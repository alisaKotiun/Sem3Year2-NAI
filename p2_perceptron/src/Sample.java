import java.util.Arrays;

public class Sample {
    private final double [] values;
    private final String name;
    private int output;

    public Sample(double[] v, String n){
        values = v;
        name = (n == null) ? "?" : n;
    }

    public double[] getValues() {
        return values;
    }

    public String getName() {
        return name;
    }

    public int getOutput() {
        return output;
    }

    public void setOutput(int output) {
        this.output = output;
    }

    @Override
    public String toString() {
        return "Sample{" +
                "values=" + Arrays.toString(values) +
                ", '" + name + '\'' + '}';
    }
}
