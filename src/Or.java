public class Or extends BinaryConnectiveProp{


    public Or(Proposition parent, Proposition A, Proposition B) {
        super(parent, A, B);
    }

    String key() { return Dict.OR;}
}
