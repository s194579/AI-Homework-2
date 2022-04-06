public class Implication extends BinaryConnectiveProp{
    public Implication(Proposition A, Proposition B) {
        super(A, B);
    }

    String key() { return Dict.IMP;}
}
