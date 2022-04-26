import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class BeliefBase {

    private List<Proposition> KBlist = new ArrayList<Proposition>();

    public void revise(Proposition phi){
        // This method takes a new proposition and does revision on the existing knowledge base
        contraction(new Not(null,phi));
        addProposition(phi);

    }

    public void contraction(Proposition phi){
        // This method takes a new proposition and does contraction on the existing knowledge base

        //Check if current beliefs entail the given information (If they don't, we can ignore)
        if (!entails(KBlist,phi)){
            return;
        }

        int[] indices = getIndicesOfPropsToDelete(phi);

        removeElementsFromList(KBlist, indices);

    }

    // Iterate through all combinations of 1,2,...,n propositions to delete and find first non-entailing
    int[] getIndicesOfPropsToDelete(Proposition phi){
        for (int i = 1; i < KBlist.size()+1; i++) {
            int[] indices = getFirstNonEntailingIndices(i, phi);
            if (indices != null){
                return indices;
            }
        }
        return null;
    }

    //Sorts based on sum of indices, e.g. [1,5] has priority 6 and [0,4] has priority 4
    void sortIndicesOnPriority(List<int[]> indices){
        indices.sort(
                Comparator.comparingInt(this::sum));
    }

    private int sum(int[] array){
        int sum = 0;
        for (int i:array) {
            sum+=i;
        }
        return sum;
    }

    int[] getFirstNonEntailingIndices(int numOfPropsToDelete, Proposition phi){
        int n = KBlist.size();
        List<int[]> indicesToTry = CombinatoricsUtil.generateCombinations_n_choose_r(n,numOfPropsToDelete);
        sortIndicesOnPriority(indicesToTry);
        for (int[] indices: indicesToTry) {
            //Create clone of KB
            List<Proposition> testKbList = new ArrayList<>(KBlist);

            //Remove props
            removeElementsFromList(testKbList,indices);

            //Check entailment
            boolean entails = entails(testKbList, phi);

            if (!entails){
                return indices;
            }
        }
        return null;
    }

    void removeElementsFromList(List<Proposition> list, int[] indicesToDelete){
        //Mark elements for removal
        for (int i:indicesToDelete) {
            list.set(i,null);
        }

        //Remove marked elements
        while (list.remove(null)) {
        }
    }

    //KB entails phi   iff   "KB & !phi" is unsatisfiable (i.e. never true)
    private static boolean entails(List<Proposition> KB, Proposition phi){

        //Negate phi
        Proposition nphi = phi instanceof Not ? phi.A : new Not(null,phi);

        //Clone KBlist and add nphi
        ArrayList<Proposition> cloneKB = new ArrayList<>(KB); // TODO consider deep clone
        cloneKB.add(nphi);

        //Make composite proposition
        Proposition compProp = toSingleProposition(cloneKB);

        //Check for unsatisfiability
        boolean satisfiable = SAT.isSatisfiableDPLL(compProp);

        return !satisfiable;
    }

    public static Proposition toSingleProposition(List<Proposition> list){
        // Convert a knowledge base to a single sentence connected by AND
        if(list.size() == 1){
            return list.get(0);
        }

        Proposition result = new And(null,list.get(0),list.get(1));

        for (int i = 2; i < list.size(); i++) {
            Proposition nextLayer = new And(null, result, list.get(i));
            result = nextLayer;
        }
        return result;
    }

    public void addProposition(Proposition phi){
        if(!KBlist.contains(phi)){
            KBlist.add(phi);
        }
    }

    @Override
    public String toString(){
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < KBlist.size(); i++) {
            sb.append(KBlist.get(i).toString());
            sb.append("\n");
        }
        return sb.toString();
    }

    public String toCNFString(){
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < KBlist.size(); i++) {
            sb.append(KBlist.get(i).toCNF().toString());
            sb.append("\n");
        }
        return sb.toString();
    }

}
