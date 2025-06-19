package osupr.project;


import java.util.Comparator;

public class Knn {
    private final int k;
    private final String distanceMetric;

    public Knn(int k, String distanceMetric) {
        this.k = k;
        this.distanceMetric = distanceMetric;
    }

    public int predict(double[][] trainData, double[] testInstance) {
        int trainSize = trainData.length;
        double[][] distances = new double[trainSize][2]; // [distance, label]

        // Calculate distances from testInstance to all training data
        for (int i = 0; i < trainSize; i++) {
            double[] trainFeatures = new double[testInstance.length - 1];
            double[] testFeatures = new double[testInstance.length - 1];

            for (int j = 0; j < testInstance.length - 1; j++) {
                trainFeatures[j] = trainData[i][j];
                testFeatures[j] = testInstance[j];
            }

            double dist = 0;
            if (distanceMetric.equals("euclidean")) {
                dist = Distance.euclidean(trainFeatures, testFeatures);
            } else if (distanceMetric.equals("manhattan")) {
                dist = Distance.manhattan(trainFeatures, testFeatures);
            } else if (distanceMetric.equals("discrete")) {
                dist = Distance.discrete(trainFeatures, testFeatures);
            }

            distances[i][0] = dist;
            distances[i][1] = trainData[i][trainData[i].length - 1]; // label
        }

        // Sort distances
        java.util.Arrays.sort(distances, Comparator.comparingDouble(a -> a[0]));

        // Count class occurrences in k nearest neighbors
        int[] classCounts = new int[10]; // assuming class labels are small integers (e.g., 0-9)
        for (int i = 0; i < k; i++) {
            int label = (int) distances[i][1];
            classCounts[label]++;
        }

        // Return the class with the highest count
        int maxCount = -1;
        int predictedLabel = -1;
        for (int i = 0; i < classCounts.length; i++) {
            if (classCounts[i] > maxCount) {
                maxCount = classCounts[i];
                predictedLabel = i;
            }
        }

        return predictedLabel;
    }

    public int[] predictAll(double[][] trainData, double[][] testData) {
        int[] predictions = new int[testData.length];
        for (int i = 0; i < testData.length; i++) {
            predictions[i] = predict(trainData, testData[i]);
        }
        return predictions;
    }

    public static void evaluate(int[] predictions, double[][] testData, int numClasses) {
        int[][] confusionMatrix = new int[numClasses][numClasses];
        int correct = 0;

        for (int i = 0; i < predictions.length; i++) {
            int actual = (int) testData[i][testData[i].length - 1];
            int predicted = predictions[i];

            confusionMatrix[actual][predicted]++;
            if (actual == predicted) {
                correct++;
            }
        }

        System.out.println("\n Confusion matrix:\n");
        System.out.print("      ");
        for (int i = 0; i < numClasses; i++) {
            System.out.printf("%4d", i);
        }
        System.out.println();
        for (int i = 0; i < numClasses; i++) {
            System.out.printf("%4d", i);
            for (int j = 0; j < numClasses; j++) {
                System.out.printf("%4d", confusionMatrix[i][j]);
            }
            System.out.println();
        }

        double accuracy = (correct * 100.) / predictions.length;
        System.out.printf("\nAccuracy: %.2f\n", accuracy);
    }

}
