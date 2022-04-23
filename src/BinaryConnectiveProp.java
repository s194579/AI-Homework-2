public abstract class BinaryConnectiveProp extends Proposition{
    Proposition B;

    public BinaryConnectiveProp(Proposition parent, Proposition A, Proposition B){
        super(parent, A);
        this.B = B;
        this.B.setParent(B);
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("(");
        sb.append(this.A.toString());
        sb.append(" " + this.key() + " ");
        sb.append(this.B.toString());
        sb.append(")");
        return sb.toString();
    }
}
