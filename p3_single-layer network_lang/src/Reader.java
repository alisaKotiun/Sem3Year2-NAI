import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;
import java.util.stream.Collectors;

public class Reader {
    public static int numOfNeurons(String path){
        File directory = new File(path);
        return directory.listFiles().length;
    }

    public static String[] languages(String path){
        String[] strings = new String[numOfNeurons(path)];
        File dir = new File(path);
        File listDir[] = dir.listFiles();
        for(int i = 0; i < listDir.length; i++){
            strings[i] = listDir[i].getName();
        }
        return strings;
    }

    public static List<Sample> getSamples(String path){
        List<Sample> list = new ArrayList<>();
        File dir = new File(path);
        File listDir[] = dir.listFiles();
        for (File d: listDir) {
            String language = d.getName();
            for (File file: getFilesLanguage(d)) {
                Sample sample = new Sample(readFile(file), language);
                list.add(sample);
            }
        }
        return list;
    }

    private static List<File> getFilesLanguage(File dir){
        List<File> files = new ArrayList<>();
        try {
            files = Files.walk(dir.toPath())
                    .filter(Files::isRegularFile)
                    .map(Path::toFile)
                    .collect(Collectors.toList());

        } catch (IOException e) {
            e.printStackTrace();
        }
        return files;
    }

    public static int[] readFile(File file){
        int[] values = new int[Main.NUMBER_OF_INPUTS];
        try {
            BufferedReader reader = new BufferedReader(new FileReader(file));
            String line;
            while ((line = reader.readLine()) != null){
                line = line.toLowerCase();
                for(int i = 0; i < line.length(); i++){
                    int ch = line.charAt(i);
                    if(ch > 96 && ch < 123){
                        values[ch-97]++;
                    }
                }
            }
            reader.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return values;
    }
}
