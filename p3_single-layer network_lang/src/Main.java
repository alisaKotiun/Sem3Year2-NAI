import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;
import java.util.Scanner;

public class Main {
    private static String PATH = "C:\\Users\\User\\OneDrive\\Робочий стіл\\NAI\\l4\\p3\\lang";
    private static String TESTINGS = "C:\\Users\\User\\OneDrive\\Робочий стіл\\NAI\\l4\\p3\\lang.test";

    public static int NUMBER_OF_NEURONS;
    public static int NUMBER_OF_INPUTS = 26;
    public static String[] LANGUAGES;
    private static List<Sample> trainingSet;
    private static List<Sample> testingSet;
    private static Neuron neuron;
    private double ERROR = 0.99;

    public Main(String path, String pathTest){
        NUMBER_OF_NEURONS = Reader.numOfNeurons(path);
        LANGUAGES = Reader.languages(path);
        trainingSet = Reader.getSamples(path);
        testingSet = Reader.getSamples(pathTest);
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter learning rate [0; 1]");
        double learningRate = scanner.nextDouble();
        Main main = new Main(PATH, TESTINGS);

        neuron = new Neuron(learningRate);
        main.learning();
        main.testing(testingSet, true);
        main.enterYourOwnText();
    }

    public void learning(){
        double accuracy, errRate;
        do{
            errRate = 0;
            for (Sample sample:trainingSet) {
                errRate += neuron.learning(sample);
            }
            accuracy = (trainingSet.size()-errRate)/trainingSet.size();
            System.out.println("Training: " + accuracy * 100 + "%");
        }while(ERROR > accuracy);
        System.out.println("\tLearning is finished!\n");
    }

    public void testing(List<Sample> list, boolean test){
        double accuracy = 0;
        for (Sample sample : list) {
            accuracy += neuron.testing(sample);
        }
        if(test){
            accuracy = (list.size() - accuracy) / list.size() * 100;
            System.out.println("\t Accuracy: " + accuracy + "%");
        }
    }

    public void enterYourOwnText(){
        while(true){
            System.out.println("\n\t Enter your own text");
            Scanner in = new Scanner(System.in);
            String s = in.nextLine();

            testing(List.of(createSample(s)), false);
        }
    }

    private Sample createSample(String s){
        File file = new File("info.txt");
        try {
            FileWriter writer = new FileWriter(file);
            writer.write(s);
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        Sample sample = new Sample(Reader.readFile(file), "?");
        return sample;
    }
}
