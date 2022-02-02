public class Sample implements Comparable<Sample>{
    private double v1, v2, v3, v4, result;
    private String name;
    public Sample(double i1, double i2, double i3, double i4, String s){
        v1 = i1; v2 = i2; v3 = i3; v4 = i4; name = s;
    }

    public void calculate(Sample s){
        result = Math.sqrt(Math.pow((v1 - s.v1), 2) + Math.pow((v2 - s.v2), 2) + Math.pow((v3 - s.v3), 2) + Math.pow((v4 - s.v4), 2));
    }

    public String getName() {
        return name.isEmpty() ? "unknown" : name;
    }

    @Override
    public int compareTo(Sample sample) {
        return  Double.compare(result, sample.result);
    }

    @Override
    public String toString() {
        return "(" + v1 + "; " + v2 + "; " + v3 + "; " + v4 + ") - " + getName() ;
    }

}
