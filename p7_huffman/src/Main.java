import java.util.*;
import java.util.stream.Collectors;

public class Main {

    public static PriorityQueue<Node> pq = new PriorityQueue<>();

    public static void main(String[] args) {
        System.out.println("Enter a string");
        String string = new Scanner(System.in).next();

        countChars(string);

        toTree();

        prefixCode(pq.peek(), "");


    }

    public static void countChars(String STRING){

        Map<String, Long> map = Arrays.stream(STRING.split(""))
                .collect(Collectors.groupingBy(c -> c, Collectors.counting()));

        for(String key : map.keySet()){
            pq.add(new Node(key, Math.toIntExact(map.get(key))));
        }
    }

    public static void toTree(){
        while(pq.size() > 1){
            Node nl = pq.poll();
            Node nr = pq.poll();
            Node nNew = new Node(nl, nr);
            pq.add(nNew);
        }
    }

    public static void prefixCode(Node n, String s){
        Node l = n.getLEFT();
        Node r = n.getRIGHT();
        if(l == null && r == null){
            System.out.println(n + "\n\t" + s);
            return;
        }
        prefixCode(l, s + "0");
        prefixCode(r, s + "1");
    }
}
