import java.util.Scanner;

public class RevisionAgent {
    KnowledgeBase kb = new KnowledgeBase();
    Scanner scanner = new Scanner(System.in);

    public void start(){
        System.out.println("Welcome. Write \"exit\" to stop at any point.");
        String input;
        boolean exit = false;
        while (!exit){
            System.out.println("Input your proposition:");
            input = scanner.nextLine();
            exit = input.equalsIgnoreCase("exit");
            if (exit) break;

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
                System.out.println("Could not convert revise knowledge base with given proposition.");
                System.out.println(input);
                continue;
            }

            System.out.println("Updated contents of the knowledge base:");
            System.out.println(kb.toString());
        }
    }



}
