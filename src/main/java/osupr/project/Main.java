package osupr.project;

public class Main {
    public static void main(String[] args) {
        DataManipulation.cleanData();
        double[][] dataMatrix = DataManipulation.readCSV();
        SplitData splitData = DataManipulation.splitData(dataMatrix,.8);
        double[][] trainData = splitData.trainData;
        double[][] testData = splitData.testData;

        Knn knn = new Knn(3, "euclidean");

        int[] predictions = knn.predictAll(trainData, testData);

        System.out.println("\nPredictions:");
        for (int i = 0; i < predictions.length; i++) {
            System.out.println("Actual: " + (int)testData[i][testData[i].length - 1] +
                    ", Predicted: " + predictions[i]);
        }

        Knn.evaluate(predictions, testData, 2);
    }
}
