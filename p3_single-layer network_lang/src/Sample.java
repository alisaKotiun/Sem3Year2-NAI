import java.util.Arrays;

public class Sample {
    private double[] values;
    private String language;

    public Sample(int[]v, String l){
        values = normalize(v);
        language = l;
    }

    private double[] normalize(int[]v){
        double n = 0; double[] result = new double[v.length];
        for (int j : v) {
            n += j * j;
        }
        n = Math.sqrt(n);
        for (int i = 0; i < v.length; i++){
            result[i] = v[i]/n;
        }
        return result;
    }

    public double[] getValues() {
        return values;
    }

    public String getLanguage() {
        return language;
    }

    @Override
    public String toString() {
        return "Sample{" +
                "values=" + Arrays.toString(values) +
                ", '" + language + '\'' + '}';
    }
}
