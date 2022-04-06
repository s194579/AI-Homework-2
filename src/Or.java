public class Or extends BinaryConnectiveProp{
    private Proposition p2;
    private Proposition p1;

    public Or(Proposition p1, Proposition p2) {
        this.p1 = p1;
        this.p2 = p2;
    }

    String key() { return Dict.OR;}
}
