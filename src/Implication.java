public class Implication extends BinaryConnectiveProp{
    private Proposition p1;
    private Proposition p2;

    public Implication(Proposition p1, Proposition p2) {
        this.p1 = p1;
        this.p2 = p2;
    }

    String key() { return Dict.IMP;}
}
