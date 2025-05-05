package osupr.project;

import com.github.rcaller.rstuff.RCaller;
import com.github.rcaller.rstuff.RCode;

import java.io.*;
import java.util.*;

public class DataManipulation {

    public static void main(String[] args) {
        cleanData();
        double[][] x = readCSV();
        splitData(x,.8);
    }

    // Method to clean the data using R
    public static void cleanData() {
        RCaller caller = RCaller.create();
        RCode code = RCode.create();

        code.R_source("scripts/DataManipulation.R");
        
        caller.setRCode(code);
        caller.runOnly();

        caller.stopStreamConsumers();
    }

    public static double[][] readCSV(){
        System.out.println("Analysing data");
        String csvFilePath = "data/filtered_data.csv";

        List<List<String>> data = new ArrayList<>();

        try (BufferedReader br = new BufferedReader(new FileReader(csvFilePath))) {
            String line;

            while ((line = br.readLine()) != null) {
            String[] values = line.split(",");
            data.add(Arrays.asList(values));
            }

            System.out.println("\nData: ");
            for (int i = 0; i < data.size(); i++) {
            List<String> row = data.get(i);
            System.out.println("Row " + i + ": " + String.join(", ", row));
            }
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }

        double[][] x = new double[data.size()][data.getFirst().size()];
        for (int i = 0; i < data.size(); i++) {
            for (int j = 0; j < data.get(i).size(); j++) {
                try {
                    x[i][j] = Double.parseDouble(data.get(i).get(j));
                } catch (NumberFormatException e) {
                    System.out.println("Error parsing value at row " + i + ", column " + j + ": " + data.get(i).get(j));
                    x[i][j] = Double.NaN; // Assign NaN for invalid values
                }
            }
        }

        return x;

    }

    public static SplitData splitData(double[][] data, double trainRatio) {
        int numRows = data.length;
        int numCols = data[0].length;

        // Shuffle the data
        List<double[]> dataList = new ArrayList<>(Arrays.asList(data));
        Collections.shuffle(dataList, new Random());

        // Split the data
        int trainSize = (int) (numRows * trainRatio);
        double[][] trainData = new double[trainSize][numCols];
        double[][] testData = new double[numRows - trainSize][numCols];

        for (int i = 0; i < trainSize; i++) {
            trainData[i] = dataList.get(i);
        }
        for (int i = trainSize; i < numRows; i++) {
            testData[i - trainSize] = dataList.get(i);
        }

        return new SplitData(trainData, testData);
    }

}
