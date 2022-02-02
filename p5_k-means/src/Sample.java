import java.util.Arrays;
import java.util.Random;

public class Sample {
    private String correct;
    private double[] values;
    private int clusterResult = -1;

    public Sample(String c, double[] a){
        correct = c;
        values = a;
    }

    public Sample(int i, double[] a){
        clusterResult = i;
        values = a;
    }

    public int getClusterResult() {
        return clusterResult;
    }

    public void setClusterResult(int clusterResult) {
        this.clusterResult = clusterResult;
    }

    public double[] getValues() {
        return values;
    }

    public void setValues(double[] values) {
        this.values = values;
    }

    public String getCorrect() {
        return correct;
    }

    @Override
    public String toString() {
        return " - " +
                "correct='" + correct + '\'' +
                ", values=" + Arrays.toString(values);
    }
}
