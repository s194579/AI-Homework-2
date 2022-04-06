public class Or extends BinaryConnectiveProp{
    public Or(Proposition A, Proposition B) {
        super(A,B);
    }

    String key() { return Dict.OR;}
}
