import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class KnowledgeBase {

    private List<Proposition> KBlist = new ArrayList<Proposition>();

    public void revise(Proposition phi){
        // This method takes a new proposition and does revision on the existing knowledge base
        contraction(new Not(null,phi));
        addProposition(phi);

    }

    public void contraction(Proposition phi){
        // This method takes a new proposition and does contraction on the existing knowledge base

    }

    public Proposition toCNF(){
        // Return a version of the knowledge base which is in CNF
        List<Proposition> KBclone = new ArrayList<Proposition>(KBlist);

        Proposition sentence = toProposition(KBclone);
        //sentence.toCNF(); //The call is likely different

        return sentence;
    }

    public Proposition toProposition(List<Proposition> list){
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

    public List<Proposition> getPropositions(){
        return KBlist;
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

}