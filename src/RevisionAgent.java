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
                break;
            }

            try {
                System.out.println("Revising knowledge base with recieved proposition");
                kb.revise(proposition);
            } catch (Exception e){
                System.out.println("Could not convert revise knowledge base with given proposition.");
                System.out.println(input);
                break;
            }

            System.out.println("Updated contents of the knowledge base:");
            System.out.println(kb.toString());
        }
    }


    public static Proposition toProposition(String prop){

        if (prop.contains("(")){
            boolean  findp1 = true;
            boolean b = false;
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

            Proposition p1 = toProposition((String) prop.subSequence(startp1+1,endp1-1));

            Proposition p2 = null;
            if(b) {
                p2 = toProposition((String) prop.subSequence(startp2 + 1, endp2 - 1));
            }
            String m = prop.substring(endp1+1,startp2-1);
            Proposition p;
            if(m.contains(Dict.AND)){
                p = new And(null,p1, p2);
                p1.setParent(p);
                p2.setParent(p);
                return p;
            } else if (m.contains(Dict.BIIMP)){

                p = new BiImplication(null,p1,p2);
                p1.setParent(p);
                p2.setParent(p);
                return p;

            } else if (m.contains(Dict.IMP)){
                p = new Implication(null,p1,p2);
                p1.setParent(p);
                p2.setParent(p);
                return p;
            } else if (m.contains(Dict.OR)){
                p = new Or(null,p1,p2);
                p1.setParent(p);
                p2.setParent(p);
                return p;
            } else if (!b && prop.substring(0,startp1-1).contains(Dict.NOT)){
                p =  new Not(null,p1);
                p1.setParent(p);
                return p;
            }
        }else{
            Literal l = new Literal(null,null);
            l.var = prop;
            return l;
        }
        return null;
    }
}
