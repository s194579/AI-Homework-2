public abstract class BinaryConnectiveProp extends Proposition{
    Proposition B;

    public BinaryConnectiveProp(Proposition A, Proposition B){
        super(A);
        this.B = B;
    }
}
