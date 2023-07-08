import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Locale;

public class CodingChallenges {

    /**
     * Return the missing number from an array of length N containing all the
     * values from 0 to N except for one missing number.
     */
    public static int missingNumber(int[] values) {
        // TODO
        int missingNumber = 0;
        ArrayList<Integer> ls1 = new ArrayList<>();
        for (int i = 0; i< values.length; i++){
            ls1.add(values[i]);
        }

        for (int i = 0; i <= values.length; i++){
            if (ls1.contains(i)){
                continue;
            }else{
                missingNumber = i;
                break;
            }
        }
        return missingNumber;
    }

    /**
     * Returns true if and only if two integers in the array sum up to n.
     * Assume all values in the array are unique.
     */
    public static boolean sumTo(int[] values, int n) {
        // TODO
        for (int i1 = 0; i1 < values.length; i1++){
            for (int i2 = 0; i2 < values.length; i2++){
                if(values[i1] + values[i2] == n){
                    return true;
                }
            }
        }

        return false;
    }

    /**
     * Returns true if and only if s1 is a permutation of s2. s1 is a
     * permutation of s2 if it has the same number of each character as s2.
     */
    public static boolean isPermutation(String s1, String s2) {
        // TODO
        HashMap<String, Integer> s1Map = new HashMap<>();
        HashMap<String, Integer> s2Map = new HashMap<>();

        for (int i1 = 0; i1<s1.length(); i1++){
            if(!s1.substring(i1, i1 + 1).equals(" ")) {
                if (!s1Map.containsKey(s1.substring(i1, i1 + 1).toLowerCase())) {
                    s1Map.put(s1.substring(i1, i1 + 1).toLowerCase(), 1);
                } else {
                    s1Map.put(s1.substring(i1, i1 + 1).toLowerCase(), s1Map.get(s1.substring(i1, i1 + 1).toLowerCase()) + 1);
                }
            }
        }

        for (int i2 = 0; i2<s2.length(); i2++){
            if (!s2.substring(i2, i2 + 1).equals(" ")) {
                if (!s2Map.containsKey(s2.substring(i2, i2 + 1).toLowerCase())) {
                    s2Map.put(s2.substring(i2, i2 + 1).toLowerCase(), 1);
                } else {
                    s2Map.put(s2.substring(i2, i2 + 1).toLowerCase(), s2Map.get(s2.substring(i2, i2 + 1).toLowerCase()) + 1);
                }
            }
        }


        boolean thingToReturn = true;

        for (int i1 = 0; i1 < s1.length(); i1++){
            String current = s1.substring(i1, i1+1);
            if(!current.equals(" ")){
                current = current.toLowerCase();
                if (s2Map.containsKey(current)){
                    if (!s2Map.get(current).equals(s1Map.get(current))){
                       thingToReturn = false;
                    }
                }else{
                    thingToReturn = false;
                }
            }
        }


        for (int i2 = 0; i2 < s2.length(); i2++){
            String current = s2.substring(i2, i2+1);
            if(!current.equals(" ")){
                current = current.toLowerCase();
                if (s1Map.containsKey(current)){
                    if (!s1Map.get(current).equals(s2Map.get(current))){
                        thingToReturn = false;
                    }
                }else{
                    thingToReturn = false;
                }
            }
        }

        return thingToReturn;

    }
}