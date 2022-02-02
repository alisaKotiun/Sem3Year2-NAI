import java.io.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;

public class Main {
    private  static List<Sample> trainingSet;
    private  static List<Sample> test;

    public Main(){
        File file = new File("iris.data");
        trainingSet = read(file);
        File file2 = new File("iris.test.data");
        test  = read(file2);
    }

    public static void main(String[] args) throws Exception {
        Main main = new Main();
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter positive natural number k");
        int k = scanner.nextInt();
        if(k < 1 || k > trainingSet.size()) throw new Exception("k-value is incorrect");
        System.out.println("Testing results: ");
        main.calculate(k, test, false);

        main.additionalInterface();
    }

    private void additionalInterface() {
        while (true){
            Scanner scanner = new Scanner(System.in);
            String[] data = new String[5];
            System.out.println("\n\t *** We can help you to classify the iris ***");
            System.out.println("Enter 4 parameters to predict the iris's type");
            for(int i = 0 ; i < 4; i++){
                data[i] = scanner.next();
            }
            data[4] = "";
            System.out.println("Enter positive natural number k");
            int k = scanner.nextInt();
            List<Sample> list = List.of(create(data));
            this.calculate(k, list, true);
        }
    }

    //read data from file and put it into list
    private List<Sample> read(File file){
        List<Sample> list = new ArrayList<>();
        try {
            BufferedReader reader = new BufferedReader(new FileReader(file.getPath()));
            String line;
            while ((line = reader.readLine()) != null){
                String[] data = line.split(",");
                list.add(create(data));
            }
            reader.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    //creating a "vector"
    private Sample create(String[] data){
        Sample sample = new Sample(
                Double.parseDouble(data[0]),
                Double.parseDouble(data[1]),
                Double.parseDouble(data[2]),
                Double.parseDouble(data[3]),
                data[4]);
        return sample;
    }

    //calculating and printing the result for each element of testSet
    public void calculate(int k, List<Sample> testSet, boolean isYourOwnVector){
        int correct = 0;
        for (Sample testing : testSet) {
            for (Sample sample : trainingSet) {
                sample.calculate(testing);
            }
            Collections.sort(trainingSet);
            String result = result(k, trainingSet);
            System.out.println(testing.toString() + "\t Result: " + result);
            if(testing.getName().equalsIgnoreCase(result)) correct++;
        }
        if(!isYourOwnVector) {
            int accuracy = correct * 100 / testSet.size();
            System.out.println("\t\t*** The accuracy: " + accuracy + "% ***");
        }
    }

    //counting the number of results among k-first elements
    private String result(int k, List<Sample> set){
        int s = 0, ve = 0, vi = 0;
        for(int i = 0; i < k; i++){
            String name = set.get(i).getName();
            switch (name){
                case "Iris-setosa" : s++; break;
                case "Iris-versicolor" : ve++; break;
                case "Iris-virginica" : vi++; break;
            }
        }
        int max = max(s, ve, vi);
        if (max == s) return "Iris-setosa";
        if(max == ve) return "Iris-versicolor";
        return "Iris-virginica";
    }

    private int max(int s, int ve, int vi){
        return Integer.max(s, Integer.max(ve, vi));
    }
}
