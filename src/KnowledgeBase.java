import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class KnowledgeBase {

    private List<Proposition> KBlist;

    public void addProp(){
        System.out.println("Input your proposition:");

        Scanner scanner = new Scanner(System.in);
        //Read user input so it can be converted to a proposition
        String input = scanner.nextLine();
        try {
            Proposition proposition = Proposition.toProposition(input);
            KBlist.add(proposition);
        } catch (Exception e){
            System.out.println("Could not convert given input to a proposition. Input was:");
            System.out.println(input);
        }
    }


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
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < KBlist.size(); i++) {
            sb.append(KBlist.get(i).toString());
            sb.append("\n");
        }
        return sb.toString();
    }

}
