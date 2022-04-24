import java.util.Locale;
import java.util.Scanner;

public class RevisionAgent {
    KnowledgeBase kb = new KnowledgeBase();
    Scanner scanner = new Scanner(System.in);

    public void start(){
        System.out.println("Welcome. Write \"exit\" to stop at any point.");
        System.out.println("Write \"cnf\" to show the CNF representation of the knowledge base.");
        String input;
        boolean exit = false, showCNF = false;
        while (!exit){
            System.out.println("Input your proposition or command:");
            input = scanner.nextLine();
            exit = input.equalsIgnoreCase("exit");
            if (exit) break;

            showCNF = input.equalsIgnoreCase("cnf");
            if (showCNF){
                System.out.println("Knowledge base on CNF form:");
                System.out.println(kb.toCNFString());
                continue;
            }

            Proposition proposition;
            try {
                proposition = StringConverter.toProposition(input);
                System.out.println("Recieved proposition:");
                System.out.println(proposition);
            } catch (Exception e){
                System.out.println("Could not convert given input to a proposition. Input was:");
                System.out.println(input);
                continue;
            }

            try {
                System.out.println("Revising knowledge base with recieved proposition");
                kb.revise(proposition);
            } catch (Exception e){
                System.out.println("Could not revise knowledge base with given proposition.");
                System.out.println(input);
                continue;
            }

            System.out.println("Updated contents of the knowledge base:");
            System.out.println(kb.toString());
        }
    }
}
