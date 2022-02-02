import java.io.File;
import java.util.List;

public class Main {
    private String PATH_TEST = "C:\\Users\\User\\OneDrive\\Робочий стіл\\NAI\\l8\\agaricus-lepiota.test.data";
    private String PATH = "C:\\Users\\User\\OneDrive\\Робочий стіл\\NAI\\l8\\agaricus-lepiota.data";

    public static int NUMBER_OF_INPUTS;
    public static String[][] inputs;
    public static String[][] test;
    public static List<String>[] attributes;

    public Main(){
        File file = new File(PATH);
        File fileTest = new File(PATH_TEST);

        inputs = Reader.readRecordsFromFile(file);
        NUMBER_OF_INPUTS = inputs.length;
        test = Reader.readRecordsFromFile(fileTest);

        attributes = Reader.readPossibleInputs(inputs);
    }

    public static void main(String[] args) {
        Main main = new Main();
        test();
    }

    public static void test(){
        int[][] matrix = {{0, 0}, {0, 0}};
        for(int i = 0; i < test.length; i++){
            double[] results = new double[attributes[0].size()]; // if y/n results.length = 2
            int k = 0;
            for(String output : attributes[0]){
                double correct = mainCorrectProbability(output);
                double[] probabilities = new double[test[i].length-1];

                for(int j = 1; j < test[i].length; j++){
                    probabilities[j-1] = probability(output, test[i][j], j);
                }
                results[k++] = calculate(correct, probabilities);
            }
            print(test[i]);
            String result = attributes[0].get(result(results));
            System.out.println("Result = " + result);

            recordResult(matrix, attributes[0], test[i][0], result);
        }
        print(matrix);
        System.out.println("Accuracy: " + calculateAccuracy(matrix));
        System.out.println("Precision: " + calculatePrecision(matrix));
        System.out.println("Recall: " + calculateRecall(matrix));
        System.out.println("F-measure: " + calculateFMeasure(matrix));
    }

    private static double calculateFMeasure(int[][] matrix){
        double r = calculateRecall(matrix);
        double p = calculatePrecision(matrix);
        return (2 * p * r)/ (p + r);
    }

    private static double calculateRecall(int[][] matrix){
        double correct = matrix[0][0];
        return correct/(correct + matrix[0][1]);
    }

    private static double calculatePrecision(int[][] matrix){
        double correct = matrix[0][0];
        return correct/(correct + matrix[1][0]);
    }

    private static double calculateAccuracy(int[][] matrix){
        double correct = (matrix[0][0] + matrix[1][1]);
        return correct / (correct + matrix[0][1] + matrix[1][0]);
    }

    private static void recordResult(int[][] matrix, List<String> list, String correct, String result){
        if(result.equals(list.get(0))){
            if(result.equals(correct)) matrix[0][0]++;
            else matrix[1][0]++;
        }
        else{
            if(result.equals(correct)) matrix[1][1]++;
            else matrix[0][1]++;
        }
    }

    private static int result(double[] results){
        int in = 0; double max = results[0];
        for(int i = 0; i < results.length; i++){
            if(results[i] > max){
                in = i; max = results[i];
            }
        }
        return in;
    }

    private static void print(String[] strings){
        for(String s: strings){
            System.out.print(s + " ");
        }
        System.out.println();
    }

    private static void print(int[][] ints){
        System.out.println("\n Matrix: ");
        for (int i = 0; i < ints.length; i++){
            for(int j = 0; j < ints[i].length; j++){
                System.out.print(ints[i][j] + "\t");
            }
            System.out.println();
        }
        System.out.println();
    }

    private static double calculate(double correct, double[]probabilities){
        double result = correct/NUMBER_OF_INPUTS;
        for(int i = 0; i < probabilities.length; i++){
            result = result * (probabilities[i]/correct);
        }
        return result;
    }

    private static double calculateWithSmoothing(double correct, double[]probabilities){
        double result = correct/NUMBER_OF_INPUTS;
        for(int i = 0; i < probabilities.length; i++){
            if(probabilities[i] == 0){
                result = result * (1./(correct+smoothing(i+1)));
            }
            else{
                result = result * (probabilities[i]/correct);
            }
        }
        return result;
    }

    private static int smoothing(int k){
        return attributes[k].size();
    }

    private static double probability(String output, String attribute, int column){
        int count = 0;
        for(int i = 0; i < inputs.length; i++){
            if(inputs[i][0].equals(output) && inputs[i][column].equals(attribute)) count++;
        }
        return count;
    }

    private static double mainCorrectProbability(String attribute){
        int count = 0;
        for(int i = 0; i < inputs.length; i++){
            if(inputs[i][0].equals(attribute)) count++;
        }
        return count;
    }
}
