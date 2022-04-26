import java.util.ArrayList;
import java.util.List;

public class BeliefBase {

    private List<Proposition> KBlist = new ArrayList<Proposition>();

    public void revise(Proposition phi){
        // This method takes a new proposition and does revision on the existing knowledge base
        contraction(new Not(null,phi));
        KBlist.add(phi);
    }

    public void contraction(Proposition phi){
        // This method takes a new proposition and does contraction on the existing knowledge base

        //Check if current beliefs entail the given information (If they don't, we can ignore)
        if (!entails(KBlist,phi)){
            return;
        }

        int[] indices = getIndicesOfPropsToDelete(phi);

        Util.removeElementsFromList(KBlist, indices);

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

    int[] getFirstNonEntailingIndices(int numOfPropsToDelete, Proposition phi){
        int n = KBlist.size();
        List<int[]> indicesToTry = Util.generateCombinations_n_choose_r(n,numOfPropsToDelete);
        Util.sortIndicesOnPriority(indicesToTry);
        for (int[] indices: indicesToTry) {
            //Create clone of KB
            List<Proposition> testKbList = new ArrayList<>(KBlist);

            //Remove props
            Util.removeElementsFromList(testKbList,indices);

            //Check entailment
            boolean entails = entails(testKbList, phi);

            if (!entails){
                return indices;
            }
        }
        return null;
    }

    //KB entails phi   iff   "KB & !phi" is unsatisfiable (i.e. never true)
    private static boolean entails(List<Proposition> KB, Proposition phi){

        //Negate phi
        Proposition nphi = phi instanceof Not ? phi.A : new Not(null,phi);

        //Clone KBlist and add nphi
        ArrayList<Proposition> cloneKB = new ArrayList<>(KB);
        cloneKB.add(nphi);

        //Make composite proposition
        Proposition compProp = PropUtil.toSingleProposition(cloneKB);

        //Check for unsatisfiability
        boolean satisfiable = SAT.isSatisfiableDPLL(compProp);

        return !satisfiable;
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
