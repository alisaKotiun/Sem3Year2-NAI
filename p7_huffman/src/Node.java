public class Node implements Comparable<Node> {
    private String CHAR;
    private int FREQUENCY;
    private Node LEFT;
    private Node RIGHT;

    public Node(String c, int f){
        CHAR = c;
        FREQUENCY = f;
        LEFT = RIGHT = null;
    }

    public Node(Node l, Node r){
        LEFT = l;
        RIGHT = r;
        FREQUENCY = l.FREQUENCY + r.FREQUENCY;
        CHAR = l.CHAR + r.CHAR;
    }

    public String getCHAR() {
        return CHAR;
    }

    public int getFREQUENCY() {
        return FREQUENCY;
    }

    public Node getLEFT() {
        return LEFT;
    }

    public Node getRIGHT() {
        return RIGHT;
    }

    @Override
    public String toString() {
        return "{" + CHAR + ": " + FREQUENCY + '}';
    }

    @Override
    public int compareTo(Node o) {
        if(this.FREQUENCY - o.getFREQUENCY() != 0)
        return this.FREQUENCY - o.getFREQUENCY();
        else return this.CHAR.compareTo(o.getCHAR());
    }
}
