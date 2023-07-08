import java.util.ArrayList;
import java.util.Arrays;

public class ArrayOperations {
    /**
     * Delete the value at the given position in the argument array, shifting
     * all the subsequent elements down, and storing a 0 as the last element of
     * the array.
     */
    public static void delete(int[] values, int pos) {
        if (pos < 0 || pos >= values.length) {
            return;
        }
        // TODO: YOUR CODE HERE
        else{
            for (int i = pos+1; i< values.length; i++){
                values[i-1] = values[i];
            }
            values[values.length-1] = 0;
        }
    }

    /**
     * Insert newInt at the given position in the argument array, shifting all
     * the subsequent elements up to make room for it. The last element in the
     * argument array is lost.
     */
    public static void insert(int[] values, int pos, int newInt) {
        if (pos < 0 || pos >= values.length) {
            return;
        }
        // TODO: YOUR CODE HERE
        else{
            for (int i = values.length-1; i > pos; i--){
                values[i] = values[i-1];
            }
            values[pos] = newInt;
        }

    }

    /** 
     * Returns a new array consisting of the elements of A followed by the
     *  the elements of B. 
     */
    public static int[] catenate(int[] A, int[] B) {
        // TODO: YOUR CODE HERE
        int[] values = new int[A.length+B.length];
        System.arraycopy(A,0,values,0,A.length);
        System.arraycopy(B,0,values,A.length,B.length);
        return values;
    }

    /** 
     * Returns the array of arrays formed by breaking up A into
     *  maximal ascending lists, without reordering.
     */
    public static int[][] naturalRuns(int[] A) {
        // TODO: Your CODE HERE

        ArrayList<Integer> breakIndex = new ArrayList<Integer>();

        breakIndex.add(0);

        for (int i = 0; i<A.length-1; i++){
            if (A[i]>A[i+1]){
                breakIndex.add(i);
            }
        }

        breakIndex.add(A.length-1);


        int[][] values = new int[breakIndex.size()-1][];

        for(int i = 0; i < breakIndex.size()-1; i++){
            if (i!=0) {
                values[i] = new int[breakIndex.get(i + 1) - breakIndex.get(i)];
                System.arraycopy(A, breakIndex.get(i)+1, values[i], 0, breakIndex.get(i + 1) - breakIndex.get(i));
            }
            else{
                values[i] = new int[breakIndex.get(i+1)- breakIndex.get(i)+1];
                System.arraycopy(A, breakIndex.get(i), values[i], 0, breakIndex.get(i+1)-breakIndex.get(i)+1);
            }
        }




        return values;
    }

    /*
    * Returns the subarray of A consisting of the LEN items starting
    * at index K.
    */
    public static int[] subarray(int[] A, int k, int len) {
        int[] result = new int[len];
        System.arraycopy(A, k, result, 0, len);
        return result;
    }

}