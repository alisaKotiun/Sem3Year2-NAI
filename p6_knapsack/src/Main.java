import java.util.Arrays;

public class Main {


    static int[] WEIGHTS = {3, 1, 6, 10, 1, 4, 9, 1, 7, 2, 6, 1, 6, 2, 2, 4, 8, 1, 7, 3, 6, 2, 9, 5, 3, 3, 4, 7, 3, 5, 30, 50};
    static int[] VALUES = {7, 4, 9, 18, 9, 15, 4, 2, 6, 13, 18, 12, 12, 16, 19, 19, 10, 16, 14, 3, 14, 4, 15, 7, 5, 10, 10, 13, 19, 9, 8, 5};
    static int SIZE = 32;
    static int MAX = 75;


/*
    static int[] WEIGHTS = {1, 2, 3, 8, 7, 4};
    static int[] VALUES = {1, 5, 10, 40, 15, 25};
    static int SIZE = 6;
    static int MAX = 10;
 */

    static long PERMUTATIONS = (long) Math.pow(2, SIZE);
    static int VALUE_RESULT = 0;
    static long SET = 0;

    public static void main(String[] args) {
        for(long i = 0; i < PERMUTATIONS; i++){
            if(MAX >= calculateSize(i)){
                int result;
                if(VALUE_RESULT < (result = calculateValue(i))){
                    VALUE_RESULT = result;
                    SET = i;
                }
            }
        }
        printResult(SET);
        System.out.println("Total: " + VALUE_RESULT);
    }

    public static void printResult(long i){
        int [] perm = extractArray(i);
        for(int c = 0; c < perm.length; c++){
            if(perm[(perm.length - c -1)] == 1){
                System.out.println("Item " + c + " {" + WEIGHTS[c] + "; " + VALUES[c] + "}");
            }
        }
    }

    public static int calculateValue(long i){
        int result = 0;
        int [] perm = extractArray(i);
        for(int c = 0; c < perm.length; c++){
            if(perm[(perm.length - c -1)] == 1){
                result += VALUES[c];
            }
        }
        return result;
    }

    public static int calculateSize(long i){
        int result = 0;
        int [] perm = extractArray(i);
        for(int c = 0; c < perm.length; c++){
            if(perm[(perm.length - c -1)] == 1){
                result += WEIGHTS[c];
            }
        }
        return result;
    }

    private static int[] extractArray(long i){
        String string = Long.toBinaryString(i);
        int [] perm = Arrays
                .stream(string.split(""))
                .mapToInt(Integer::parseInt)
                .toArray();
        return perm;
    }
}
