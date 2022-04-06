public class And extends BinaryConnectiveProp{
    public And(Proposition A, Proposition B) {
        super(A, B);
    }

    String key() { return Dict.AND;}
}
