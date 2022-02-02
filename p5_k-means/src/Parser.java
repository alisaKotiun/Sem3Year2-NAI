import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Parser {
    public static List<Sample> read(File file){
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

    private static Sample create(String[] data){
        double [] arg = new double [data.length-1];
        for(int i = 0; i < arg.length; i++){
            arg[i] = Double.parseDouble(data[i]);
        }
        return new Sample(data[data.length-1], arg);
    }

    public static List<Sample> createClusters(int k){
        List<Sample> list = new ArrayList<>();
        for(int i = 0; i < k; i++) {
            double[] values = Main.samples.get(new Random().nextInt(Main.samples.size())).getValues();
            list.add(new Sample(i, values));
        }
        return list;
    }

    public static double[] createArr(int k){
        double[] result = new double[k];
        for(double d : result){
            d = 0;
        }
        return result;
    }
}