import java.util.Scanner;

public class RevisionAgent {
    BeliefBase kb = new BeliefBase();
    Scanner scanner = new Scanner(System.in);

    public void start(){
        System.out.println("Welcome. Write \"exit\" to stop at any point.");
        System.out.println("Write \"cnf\" to show the CNF representation of the belief base.");
        String input;
        boolean exit = false, showCNF = false;
        while (!exit) {
            System.out.println("Input your proposition or command:");
            input = scanner.nextLine();
            exit = input.equalsIgnoreCase("exit");
            if (exit) break;

            showCNF = input.equalsIgnoreCase("cnf");
            if (showCNF) {
                System.out.println("Belief base on CNF form:");
                System.out.println(kb.toCNFString());
                continue;
            }

            Proposition proposition;
            try {
                proposition = StringConverter.toProposition(input);
                if (proposition == null) {
                    System.out.println("Input " + input + " contains empty literal.");
                } else {
                    System.out.println("Recieved proposition:");
                    System.out.println(proposition);
                }
            } catch (Exception e) {
                System.out.println("Could not convert given input to a proposition. Input was:");
                System.out.println(input);
                continue;
            }
            if (proposition != null) {
                try {
                    System.out.println("Revising belief base with recieved proposition");
                    kb.revise(proposition);
                } catch (Exception e) {
                    System.out.println("Could not revise belief base with given proposition.");
                    System.out.println(input);
                    e.printStackTrace();
                    continue;
                }

                System.out.println("Updated contents of the belief base:");
                System.out.println(kb.toString());

            }
        }
    }
}
