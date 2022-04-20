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
                proposition = toProposition(input);
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


    public static Proposition toProposition(String prop){
        //Strip of whitespace
        prop = prop.replaceAll("\\s", "");

        //Perform conversion
        Proposition proposition = toPropositionInner(prop);

        //Set parents in whole tree
        proposition.setParents(null);

        return proposition;
    }



    private static Proposition toPropositionInner(String prop){
        Proposition p1, p2;

        if (prop.contains("(")){
            boolean  findp1 = true;
            boolean b = false; //indicates if top connective is binary (As opposed to unary)
            int counter = 0;
            int startp1 = 0;
            int endp1 = 0;
            int startp2 = 0;
            int endp2 = 0;

            String partTwo;
            for (int i = 0; i < prop.length(); i++){
                if(prop.charAt(i) == '('){
                    if(!b){
                        b = true;
                        if(findp1) {
                            startp1 = i;
                        }else{
                            startp2 = i;
                        }
                    }
                    counter ++;
                }
                else if(prop.charAt(i) == ')'){
                    counter --;
                }
                if (b && counter == 0){
                    if(findp1) {
                        endp1 = i;
                        findp1 = false;
                        b =false;
                    }else{
                        endp2 = i;
                        break;
                    }
                }
            }

            p1 = toPropositionInner((String) prop.subSequence(startp1+1,endp1));

            p2 = null;
            if(b) {
                p2 = toPropositionInner((String) prop.subSequence(startp2 + 1, endp2 - 1));
            }

            String m = prop.substring(endp1+1,startp2-1);
            Proposition p = null;
            if(m.contains(Dict.AND)){
                p = new And(null,p1, p2);
            } else if (m.contains(Dict.BIIMP)){
                p = new BiImplication(null,p1,p2);
            } else if (m.contains(Dict.IMP)){
                p = new Implication(null,p1,p2);
            } else if (m.contains(Dict.OR)){
                p = new Or(null,p1,p2);
            } else if (!b && prop.substring(0,startp1-1).contains(Dict.NOT)){
                p =  new Not(null,p1);
            }
            return p;
        }
        //If there were not parentheses
        return noParenthesesToProp(prop);
    }

    private static Proposition noParenthesesToProp(String prop){
        //Prop has no parantheses, e.g. "A&B", "C", "A&B => C|D"
        //Split on highest level operator: hierarchy is BIIMP, IMP, OR, AND, NOT
        String[] args;
        Proposition p1, p2;
        if (prop.contains(Dict.BIIMP)){
            args = prop.split(Dict.BIIMP,2);
            p1 = noParenthesesToProp(args[0]);
            p2 = noParenthesesToProp(args[1]);
            return new BiImplication(null, p1, p2);
        } else if (prop.contains(Dict.IMP)){
            args = prop.split(Dict.IMP,2);
            p1 = noParenthesesToProp(args[0]);
            p2 = noParenthesesToProp(args[1]);
            return new Implication(null, p1, p2);
        } else if (prop.contains(Dict.OR)){
            args = prop.split(Dict.OR,2);
            p1 = noParenthesesToProp(args[0]);
            p2 = noParenthesesToProp(args[1]);
            return new Or(null, p1, p2);
        } else if (prop.contains(Dict.AND)){
            args = prop.split(Dict.AND,2);
            p1 = noParenthesesToProp(args[0]);
            p2 = noParenthesesToProp(args[1]);
            return new And(null, p1, p2);
        } else if (prop.contains(Dict.NOT)){
            //Must only contain NOT LITERAL
            String var = prop.replace(Dict.NOT,"");
            p1 = new Literal(null,var);
            return new Not(null,p1);
        } else {
            //Must be literal only
            Literal l = new Literal(null,prop);
            return l;
        }
    }
}
