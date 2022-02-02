import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;

public class Reader {
    public static String[][] readRecordsFromFile(File file){
        String[][] result = new String[numberOfInputs(file)][numberOfAttributes(file)];
        try {
            BufferedReader reader = new BufferedReader(new FileReader(file));
            String line; int k = 0;
            while ((line = reader.readLine()) != null){
                String[] data = line.split(",");
                for (int i = 0; i < data.length; i++) {
                    result[k][i] = data[i];
                }
                k++;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    public static List<String>[] readPossibleInputs(String[][] list){
        List<String>[] result = new List[list[0].length];
        for (int i = 0; i < list[0].length; i++){
            List<String> strings = new ArrayList<>();
            for(int j = 0; j < list.length; j++){
                String attribute = list[j][i];
                if(!strings.contains(attribute)) strings.add(attribute);
            }
            result[i] = strings;
        }
        return result;
    }

    private static int numberOfInputs(File file){
        int k = 0;
        try{
            BufferedReader reader = new BufferedReader(new FileReader(file));
            while (reader.readLine() != null){
                k++;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return k;
    }
    private static int numberOfAttributes(File file){
        try{
            BufferedReader reader = new BufferedReader(new FileReader(file));
            String line;
            while ((line = reader.readLine()) != null){
                return line.split(",").length;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }
}
