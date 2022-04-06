public class And extends BinaryConnectiveProp{
    public And(Proposition parent, Proposition A, Proposition B) {
        super(parent, A, B);
    }

    String key() { return Dict.AND;}
}
