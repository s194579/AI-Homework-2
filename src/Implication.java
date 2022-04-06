public class Implication extends BinaryConnectiveProp{


    public Implication(Proposition parent, Proposition A, Proposition B) {
        super(parent, A, B);
    }

    String key() { return Dict.IMP;}
}
