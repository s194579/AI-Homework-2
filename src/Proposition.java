public abstract class Proposition {
    Proposition A;
    Proposition parent;

    public Proposition(Proposition parent, Proposition A){
        this.parent = parent;
        this.A = A;
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

    public abstract String toString();

    private void setParent(Proposition p) {
        parent = p;
    }


    void toCNF(){
        Proposition currentProp = this.containsConnective(Dict.BIIMP);
        while (currentProp != null){
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

    void setParents(Proposition parent){
        this.parent = parent;
        if (this instanceof Literal){
            return;
        }

        this.A.setParents(this);

        if (this instanceof BinaryConnectiveProp){
            ((BinaryConnectiveProp)this).B.setParents(this);
        }
    }

    Proposition transformBiimp(){
        if (!(this instanceof BiImplication)){
            System.out.println("ERROR: tried to transform BiImp, but was not BiImp");
        }
        BiImplication thisProp = ((BiImplication)this);
        Proposition A = thisProp.A;
        Proposition B = thisProp.B;
        Proposition newA = new Implication(null,A,B);
        Proposition newB = new Implication(null,B,A);
        Proposition parent = new And(this.parent,A,B);
        newA.parent = parent;
        newB.parent = parent;
        return parent;
    }

    Proposition transformImp(){
        if (!(this instanceof Implication)){
            System.out.println("ERROR: tried to transform Imp, but was not Imp");
        }
        Implication thisProp = ((Implication)this);
        Proposition A = thisProp.A;
        Proposition B = thisProp.B;
        Proposition newA = new Not(null,A);
        Proposition parent = new Or(this.parent,newA,B);
        A.parent = newA;
        newA.parent = parent;
        B.parent = parent;
        return parent;
    }


}
