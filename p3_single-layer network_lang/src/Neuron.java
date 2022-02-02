import java.util.Arrays;
import java.util.Random;

public class Neuron {
    private final double[][]weights;
    private final double[] bias;
    private final double learningRate;

    public Neuron(double lr){
        weights = createWeights();
        bias = createBias();
        learningRate = lr;
    }

    private double[][] createWeights(){
        double[][] result = new double[Main.NUMBER_OF_NEURONS][Main.NUMBER_OF_INPUTS];
        for (int i = 0; i < result.length; i++){
            for(int j = 0; j < result[i].length; j++){
                result[i][j] = new Random().nextDouble();
            }
        }
        return result;
    }

    private double[] createBias(){
        double[] result = new double[Main.NUMBER_OF_NEURONS];
        for(int i = 0; i < result.length; i++){
            result[i] = new Random().nextDouble();
        }
        return result;
    }

    public int learning(Sample sample){
        double[] output = output(calculateNet(sample));
        int d = maxIndex(output);
        int i = 0;
        if(!Main.LANGUAGES[d].equals(sample.getLanguage())){
            changeW(output, getD(sample.getLanguage()), sample);
            changeBias(output, getD(sample.getLanguage()));
            i++;
        }
        return i;
    }

    public int testing(Sample sample){
        double[] output = output(calculateNet(sample));
        int d = maxIndex(output);
        System.out.println(sample);
        System.out.println("Result: " + Main.LANGUAGES[d]);
        return Main.LANGUAGES[d].equals(sample.getLanguage()) ? 0 : 1;
    }

    private void changeBias(double[] y, double[] d){
        for (int i = 0; i < bias.length; i++){
            double q = learningRate * (d[i] - y[i]) * y[i] * (1 - y[i]);
            bias[i] = bias[i] - q;
        }
    }

    private void changeW(double[] y, double[] d, Sample sample){
        for(int i = 0; i < weights.length; i++){
            double q = learningRate * (d[i] - y[i]) * y[i] * (1 - y[i]);
            for(int j = 0; j < weights[i].length; j++){
                weights[i][j] = weights[i][j] + q * sample.getValues()[j];
            }
        }
    }

    private double[] getD(String lang){
        double[] result = new double[Main.LANGUAGES.length];
        for(int i = 0; i < result.length; i++){
            if(Main.LANGUAGES[i].equals(lang)){
                result[i] = 1;
            }
            else{
                result[i] = 0;
            }
        }
        return result;
    }

    private int maxIndex(double[] y){
        double max = y[0]; int index = 0;
        for(int i = 0; i < y.length; i++){
            if(y[i] > max){
                max = y[i];
                index = i;
            }
        }
        return index;
    }

    private double[] output(double[] net){
        double[] output = new double[net.length];
        for (int i = 0; i < net.length; i++){
            output[i] = 1/(1+Math.exp(-net[i]));
        }
        return output;
    }

    private double[] calculateNet(Sample sample){
        double[] net = new double[weights.length];
        for(int i = 0; i < weights.length; i++){
            int n = 0;
            for (int j = 0; j < weights[i].length; j++){
                n += weights[i][j] * sample.getValues()[j];
            }
            net[i] = n - bias[i];
        }
        return net;
    }
}
