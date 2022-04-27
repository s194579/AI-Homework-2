public class StringConverter {
    private static String operatorString;
    private static boolean propIsEmptiy;
    public static Proposition toProposition(String prop) {
        //Strip of whitespace
        prop = prop.replaceAll("\\s", "");
        propIsEmptiy = false;
        //Perform conversion
        Proposition proposition = toPropositionInner(prop);

        //Set parents in whole tree
        proposition.setParents(null);
        if (propIsEmptiy) {
            return null;
        } else {
            return proposition;
        }
    }



    private static Proposition toPropositionInner(String prop){
        //Find top level operator
        int index = findTopLevelOperatorIndex(prop);

        if (index == -1){
            //If no top level operator was found
            if (prop.contains("(")){
                //Must be redundant parenthesis
                return toPropositionInner(prop.substring(1,prop.length()-1));
            } else {
                //Can evaluate without parenthes
                return noParenthesesToProp(prop);
            }
        }

        //If a top level operator was found
        String p1, p2;
        int p2startIndex = index + operatorString.length();
        p1 = prop.substring(0,index);
        p2 = prop.substring(p2startIndex);
        if (operatorString.equals(Dict.BIIMP)){
            return new BiImplication(null,toPropositionInner(p1),toPropositionInner(p2));
        } else if (operatorString.equals(Dict.IMP)){
            return new Implication(null,toPropositionInner(p1),toPropositionInner(p2));
        } else if (operatorString.equals(Dict.OR)){
            return new Or(null,toPropositionInner(p1),toPropositionInner(p2));
        } else if (operatorString.equals(Dict.AND)){
            return new And(null,toPropositionInner(p1),toPropositionInner(p2));
        } else if (operatorString.equals(Dict.NOT)){
            return new Not(null,toPropositionInner(p2));
        }
        System.out.println("Error in toPropositionInner()");
        return null;
    }

    /**
     * Returns start index of the top level parameter of the string.
     * E.g. for "(A => B) v (C & D)" returns index of 'v'
     * For "A v B <=> C & D" returns index of '<' in "<=>"
     * For "( A v B )" returns -1 since there is no top level operator
     *
     * Also sets value of operatorString to actual string for the operator
     * @param prop
     * @return
     */
    private static int findTopLevelOperatorIndex(String prop){
        int openParenthesisCount = 0;
        int bestIndex = -1;
        int operatorPriority = -1;
        String c;
        for (int i = 0; i < prop.length(); i++) {
            c = prop.substring(i,i+1);
            if (c.equals("(")){
                openParenthesisCount++;
            } else if (c.equals(")")){
                openParenthesisCount--;
            }

            //Only consider the other symbols, if we are not inside parenthesis
            if (openParenthesisCount == 0){

                if (operatorStartsAt(i,prop,Dict.BIIMP)){
                    if (operatorPriority < 5){
                        operatorPriority = 5;
                        bestIndex = i;
                        operatorString = Dict.BIIMP;
                    }
                } else if (operatorStartsAt(i,prop,Dict.IMP)){
                    if (operatorPriority < 4){
                        operatorPriority = 4;
                        bestIndex = i;
                        operatorString = Dict.IMP;
                    }
                } else if (operatorStartsAt(i,prop,Dict.OR)){
                    if (operatorPriority < 3){
                        operatorPriority = 3;
                        bestIndex = i;
                        operatorString = Dict.OR;
                    }
                } else if (operatorStartsAt(i,prop,Dict.AND)){
                    if (operatorPriority < 2){
                        operatorPriority = 2;
                        bestIndex = i;
                        operatorString = Dict.AND;
                    }
                } else if (operatorStartsAt(i,prop,Dict.NOT)){
                    if (operatorPriority < 1){
                        operatorPriority = 1;
                        bestIndex = i;
                        operatorString = Dict.NOT;
                    }

                }
            }
        }
        if (operatorString != null){};//redundant line, but otherwise compiler ignores variable
        return bestIndex;
    }

    private static boolean operatorStartsAt(int index, String prop, String operator){
        int len = operator.length();
        if (index+len-1 < prop.length()){
            String operatorSubString = prop.substring(index,index+len);
            return operator.equals(operatorSubString);
        }
        return false;
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
            if(prop.replace(" ","").length() <1 ){
                propIsEmptiy = true;
            }
            Literal l = new Literal(null,prop);
            return l;
        }
    }
}
