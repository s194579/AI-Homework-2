public abstract class Proposition {
    Proposition A;


    public Proposition toProposition(String prop){

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
           Proposition p2 = toProposition((String) prop.subSequence(startp2+1,endp2-1));
           String m = prop.substring(endp1+1,startp2-1);
           if(m.contains(Dict.AND)){
               return new And(p1, p2);
           } else if (m.contains(Dict.BIIMP)){
               return  new BiImplication(p1,p2);
           } else if (m.contains(Dict.IMP)){
               return new Implication(p1,p2);
           } else if (m.contains(Dict.OR)){
               return new Or(p1,p2);
           } else if (!b && prop.substring(0,startp1-1).contains(Dict.NOT)){
               return new Not(p1);
           }

       }
       return null;
    }


    public Proposition(Proposition A){
        this.A = A;
    }

    void toCNF(){
        Proposition currentProp = this.containsConnective(Dict.BIIMP);
        while (currentProp != null){
            currentProp = currentProp.transformBiimp(); //Expand Biimp to two implications

            currentProp = this.containsConnective(Dict.BIIMP);
        }

        currentProp = this.containsConnective(Dict.IMP);
        while (currentProp != null){

            currentProp = this.containsConnective(Dict.IMP);
        }

        while (!this.isCNF()){
            //Perform distribution and de morgan
        }

    }

    abstract String key();

    /**
     * Returns the first proposition found, which contains the key, e.g. Dict.AND key
     * If none is found, null is returned
     * @param key
     * @return
     */
    Proposition containsConnective(String key){
        if (this.key() == key){
            return this;
        }

        if (this instanceof Literal){
            return null;
        }

        Proposition containingPropInABranch = this.A.containsConnective(key);
        if (containingPropInABranch != null){
            return containingPropInABranch;
        }
        if (this instanceof BinaryConnectiveProp){
            Proposition containingPropInBBranch = ((BinaryConnectiveProp)this).B.containsConnective(key);
            if (containingPropInBBranch != null){
                return containingPropInBBranch;
            }
        }
        return null;
    }

    boolean isCNF(){
        if (this.isLogicalLiteral()){
            return true;
        }

        if (this.isClause()){
            return true;
        }

        if (this instanceof And){
            return this.A.isCNF() && ((Or)this).B.isCNF();
        }

        return false;
    }

    boolean isClause(){
        if (this.isLogicalLiteral()){
            return true;
        }

        if (this instanceof Or){
            return this.A.isClause() && ((Or)this).B.isClause();
        }

        return false;
    }

    //Return true only if of type !A, !B, A, C so on (Literal | (Not Literal))
    boolean isLogicalLiteral(){
        if (this instanceof Literal){
            return true;
        }
        if (this instanceof Not){
            return this.A instanceof Literal;
        }
        return false;
    }


    Proposition transformBiimp(){
        if (!(this instanceof BiImplication)){
            System.out.println("ERROR: tried to transform BiImp, but was not BiImp");
        } else {
            BiImplication thisProp = ((BiImplication)this);
            Proposition A = thisProp.A;
            Proposition B = thisProp.B;
            Proposition result = new And(new Implication(A,B),new Implication(B,A));
            return result;
        }
        return null;
    }


}
