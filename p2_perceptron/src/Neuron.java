import java.util.Arrays;
import java.util.Random;

public class Neuron {
    private final double[] values;
    private double learningRate, bias;
    private final String [] outputs;

    public Neuron(double[] v){
        values = v;
        bias = new Random().nextDouble();
        learningRate = 1;
        outputs = new String[2];
    }

    public double learning(Sample sample){
        int output = output(calculateNet(sample));
        if(output != sample.getOutput()){
            changeW(output, sample.getOutput(), sample);
            changeBias(output, sample.getOutput());
        }
        return Math.pow((output-sample.getOutput()), 2);
    }

    public double testing(Sample sample){
        int output = output(calculateNet(sample));
        System.out.println(sample);
        System.out.println("Result: " + output + "; " + outputs[output]);
        return Math.pow((output-sample.getOutput()), 2);
    }

    private int output(double net){
        return net < 0 ? 0 : 1;
    }

    private double calculateNet(Sample sample){
        double result = 0;
        for(int i = 0; i < values.length; i++){
            result += values[i] * sample.getValues()[i];
        }
        result = result - bias;
        return result;
    }

    private void changeW(int y, int d, Sample sample){
        double mul = learningRate * (d - y);
        for(int i = 0; i < values.length; i++){
            values[i] = values[i] + (sample.getValues()[i] * mul);
        }
    }

    private void changeBias(int y, int d){
        double mul = learningRate * (d - y);
        bias = bias - mul;
    }

    public int size(){return values.length;}

    public void setLearningRate(double learningRate) {
        this.learningRate = learningRate;
    }

    public void setOutputs(String n0, String n1) {
        outputs[0] = n0;
        outputs[1] = n1;
    }

    @Override
    public String toString() {
        return "Weight{" +
                "values=" + Arrays.toString(values) +
                ", bias=" + bias +
                '}';
    }
}
