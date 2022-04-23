public abstract class Proposition {
    Proposition A;
    Proposition parent;

    public Proposition(Proposition parent, Proposition A){
        this.parent = parent;
        this.A = A;
        this.A.setParent(this);
    }

    public abstract String toString();

    void setParent(Proposition p) {
        parent = p;
    }

    Proposition toCNF(){
        CNF cnf = new CNF();
        return cnf.toCNF(this);
    }

    abstract String key();

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
}
