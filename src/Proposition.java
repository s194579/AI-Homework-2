public abstract class Proposition {
    Proposition A;
    Proposition parent;

    public Proposition(Proposition parent, Proposition A){
        this.parent = parent;
        this.A = A;
    }

    public abstract String toString();

    void setParent(Proposition p) {
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
