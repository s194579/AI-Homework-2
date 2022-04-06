public abstract class BinaryConnectiveProp extends Proposition{
    Proposition B;

    public BinaryConnectiveProp(Proposition parent, Proposition A, Proposition B){
        super(parent, A);
        this.B = B;
    }
}
