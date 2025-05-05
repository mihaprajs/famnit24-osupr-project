package osupr.project;

public class SplitData {
    public double[][] trainData;
    public double[][] testData;

    public SplitData(double[][] trainData,double[][] testData) {
        this.trainData = trainData;
        this.testData = testData;
    }
}
