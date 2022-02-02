import java.io.File;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Main {
    public static List<Sample> samples;
    public static List<Sample> clusters;

    public Main(int k) {
        samples = Parser.read(new File("iris.data"));
        clusters = Parser.createClusters(k);
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter number of clusters");
        int k = scanner.nextInt();
        Main main = new Main(k);
        main.clustering();
        main.print();
    }

    public void print(){
        for(Sample cluster: clusters){
            List<Sample> list = new ArrayList<>();
            System.out.println("Cluster " + (cluster.getClusterResult() + 1) + Arrays.toString(cluster.getValues()));
            for(Sample sample : samples){
                if(cluster.getClusterResult() == sample.getClusterResult()){
                    System.out.println(" - " + sample.toString());
                    list.add(sample);
                }
            }
            Map<String, List<Sample>> map = list.stream()
                    .collect(Collectors.groupingBy(Sample :: getCorrect));
            for(String st : map.keySet()){
                double num = map.get(st).size();
                num = num / list.size() * 100;
                System.out.println(st + ": " + num + "%; ");
            }
        }
    }

    public void clustering(){
        int num = 1;
        while(true){
            int count = 0;
            double dis = 0;
            for(Sample sample : samples){
                double[] distances = new double[clusters.size()];
                for(Sample cluster : clusters){
                    distances[cluster.getClusterResult()] = calculateDistances(sample.getValues(), cluster.getValues());
                }
                int oldCluster = sample.getClusterResult();
                sample.setClusterResult((int) calculateMin(distances, true));
                dis += calculateMin(distances, false);
                if(sample.getClusterResult() != oldCluster){
                    count++;
                }
            }
            System.out.println("Iteration " + (num++) + "; " + dis);
            recalculateCentroid();
            if(count == 0) break;
        }
    }

    public static void recalculateCentroid(){
        for(Sample cluster: clusters){
            double[] values = Parser.createArr(cluster.getValues().length);
            int num = 0;
            for(Sample sample : samples){
                if(sample.getClusterResult() == cluster.getClusterResult()){
                    for(int i = 0; i < sample.getValues().length; i++){
                        values[i] += sample.getValues()[i];
                    }
                    num++;
                }
            }

            if(num != 0){
                for(int i = 0 ; i < values.length; i++){
                    values[i] = values[i]/num;
                }
                cluster.setValues(values);
            }
        }
    }

    public static double calculateMin(double[] dis, boolean t){
        int in = 0; double min = dis[0];
        for(int i = 0; i < dis.length; i++){
            if(dis[i] < min){
                in = i;
                min = dis[i];
            }
        }
        return (t ? in : min);
    }

    public static double calculateDistances(double[] values, double[] clusters){
        double result = 0;
        for(int i = 0; i < values.length; i++){
            result += Math.pow((values[i] - clusters[i]), 2);
        }
        return Math.sqrt(result);
    }
}