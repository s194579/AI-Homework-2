import java.util.ArrayList;
import java.util.List;

public class KnowledgeBase {

    private List<Proposition> KBlist;

    public void revise(Proposition phi){
        // This method takes a new proposition and does revision on the existing knowledge base
        contraction(phi);
        addProposition(phi);

    }

    public void contraction(Proposition phi){
        // This method takes a new proposition and does contraction on the existing knowledge base

    }

    public Proposition toCNF(){
        // Return a version of the knowledge base which is in CNF
        List<Proposition> KBclone = new ArrayList<Proposition>(KBlist);

        Proposition sentence = toSentence(KBclone);
        //sentence.toCNF(); //The call is likely different

        return sentence;
    }

    public Proposition toSentence(List<Proposition> list){
        // Convert a knowledge base to a single sentence connected by AND
        return null;
    }

    public void addProposition(Proposition phi){
        if(!KBlist.contains(phi)){
            KBlist.add(phi);
        }
    }

    public List<Proposition> getPropositions(){
        return KBlist;
    }

    public String toString(){
        String result = "";
        for (int i = 0; i < KBlist.size(); i++) {
            KBlist.get(i).toString();
        }
        return result;
    }

}
