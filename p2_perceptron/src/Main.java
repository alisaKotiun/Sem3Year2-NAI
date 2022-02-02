import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

public class Main {
    private final List<Sample> trainingSet;
    private static List<Sample> testingSet;
    private final Neuron weights;
    private static String class0, class1;
    static double minAccuracy = 0.95;

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter learning rate [0; 1]");
        double learningRate = scanner.nextDouble();

        Main main = new Main(learningRate);
        main.learn(minAccuracy);
        main.test(testingSet, true);
        main.enterYourOwnVector();

    }

    public Main(double l){
        File fileTraining = new File("C:\\Users\\User\\OneDrive\\Робочий стіл\\NAI\\l3\\p2\\perceptron.data");
        File fileTest = new File("C:\\Users\\User\\OneDrive\\Робочий стіл\\NAI\\l3\\p2\\perceptron.test.data");

        trainingSet = readFromFile(fileTraining); //training set
        saveOutputs(trainingSet); // saving classes name
        setOutputs(trainingSet);
        testingSet = readFromFile(fileTest); //testing set
        setOutputs(testingSet);

        weights = defaultWeights();
        weights.setLearningRate(l);
        weights.setOutputs(class0, class1);
    }

    //reading lists from file
    private List<Sample> readFromFile(File file){
        List<Sample> list = new ArrayList<>();
        try {
            BufferedReader reader = new BufferedReader(new FileReader(file));
            String line;
            while ((line = reader.readLine()) != null){
                String[] data = line.split(",");
                list.add(createSample(data));
            }
            reader.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    //creating sample
    private Sample createSample(String[]data){
        double [] values = new double[data.length - 1];
        for(int i = 0; i < values.length; i++){
            values[i] = Double.parseDouble(data[i]);
        }
        return new Sample(values, data[data.length-1]);
    }

    //creating weight
    private Neuron defaultWeights(){
        int valuesNumber = trainingSet.get(0).getValues().length;
            double[] values = new double[valuesNumber];
            for(int j = 0; j < values.length; j++){
                values[j] = new Random().nextDouble();
            }
            return new Neuron(values);
    }

    //saving outputs
    private void saveOutputs(List<Sample> list){
        class0 = list.get(0).getName();
        for (Sample sample: list) {
          if (!((sample.getName()).equalsIgnoreCase(class0))) {
              class1 = sample.getName();
              break;
          }
        }
    }

    //setting outputs - 1 or 0
    private void setOutputs(List<Sample> list){
        for (Sample sample: list) {
            if ((sample.getName()).equalsIgnoreCase(class0)) {
                sample.setOutput(0);
            }
            else{
                sample.setOutput(1);
            }
        }
    }

    private void learn(double error){
        double accuracy, errRate;
        //
        do{
            errRate = 0;
            for (Sample sample:trainingSet) {
                errRate += weights.learning(sample);
            }
            accuracy = (trainingSet.size()-errRate)/trainingSet.size();
            System.out.println("Training: " + accuracy);
        }while(accuracy < error);
        System.out.println(weights.toString());
        System.out.println("\tLearning is finished!\n");
    }

    private void test(List<Sample> list, boolean test){
        double accuracy = 0;
        for (Sample sample:list) {
            accuracy += weights.testing(sample);
        }
        if(test) {
            accuracy = (list.size() - accuracy) / list.size() * 100;
            System.out.println("\t Accuracy: " + accuracy + "%");
        }
    }

    private void enterYourOwnVector(){
        while(true){
            Scanner scanner = new Scanner(System.in);
            System.out.println("\n\t Enter your own input, size = " + weights.size());
            String[] values = new String[weights.size() + 1];
            for(int i = 0; i < values.length-1; i++){
                values[i] = scanner.next();
            }
            test(List.of(createSample(values)), false);
        }
    }
}
