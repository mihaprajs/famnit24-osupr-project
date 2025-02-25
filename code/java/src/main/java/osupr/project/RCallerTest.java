package osupr.project;

/*
 * POMEMBNO!
 * Pred zagonom prgrama preveri:
 * - da je R nameščen (testirano na verziji 4.4.2) - prenesi in namesti z: https://cran.r-project.org/
 * - da je pot do Rscript dodana v "PATH"
 *
 * IMPORTANT!
 * Before running the program make sure:
 * - R is installed (tested using version 4.4.2) - download and install from: https://cran.r-project.org/
 * - path to Rscript is added to "PATH"
 *
 * Koda iz dokumentacije - https://github.com/jbytecode/rcaller/blob/master/doc/rcaller3/rcaller3.pdf (07/02/2025)
 * Rahlo spremenjena za potrebe programa
 *
 */

import com.github.rcaller.rstuff.RCaller;
import com.github.rcaller.rstuff.RCode;

public class RCallerTest {
    public static void main(String[] args) {
        RCallerTest test = new RCallerTest();
        double result = test.testSetUp();

        if (result != 3.0) {
            System.out.println("Please make sure everything is installed correctly.");
        }else
            System.out.println("Everything is ok!");

    }

    public double testSetUp() {
        RCaller caller = RCaller.create();
        RCode code = RCode.create();

        double[] array = new double[]{1.0,2.0,3.0,4.0,5.0};
        code.addDoubleArray("myArray", array);

        code.addRCode("avg <- mean(myArray)");
        caller.setRCode(code);

        caller.runAndReturnResult("avg");

        double[] result = caller.getParser().getAsDoubleArray("avg");

        return result[0];
    }
}
