import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class Util {

    /**
     DISCLAIMER:
     This code was borrowed from
     https://www.baeldung.com/java-combinations-algorithm
     on 24/04/2022
     */
    public static List<int[]> generateCombinations_n_choose_r(int n, int r) {
        List<int[]> combinations = new ArrayList<>();
        int[] combination = new int[r];

        // initialize with lowest lexicographic combination
        for (int i = 0; i < r; i++) {
            combination[i] = i;
        }

        while (combination[r - 1] < n) {
            combinations.add(combination.clone());

            // generate next combination in lexicographic order
            int t = r - 1;
            while (t != 0 && combination[t] == n - r + t) {
                t--;
            }
            combination[t]++;
            for (int i = t + 1; i < r; i++) {
                combination[i] = combination[i - 1] + 1;
            }
        }
        return combinations;
    }

    //Sorts based on sum of indices, e.g. [1,5] has priority 6 and [0,4] has priority 4
    public static void sortIndicesOnPriority(List<int[]> indices){
        Util cu = new Util();
        indices.sort(
                Comparator.comparingInt(cu::sum));
    }

    private int sum(int[] array){
        int sum = 0;
        for (int i:array) {
            sum+=i;
        }
        return sum;
    }

    public static void removeElementsFromList(List<Proposition> list, int[] indicesToDelete){
        //Mark elements for removal
        for (int i:indicesToDelete) {
            list.set(i,null);
        }

        //Remove marked elements
        while (list.remove(null)) {
        }
    }
}
