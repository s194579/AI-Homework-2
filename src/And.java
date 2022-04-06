public class And extends BinaryConnectiveProp{
    Proposition p1;
    Proposition p2;

    public And (Proposition p1, Proposition p2){
        this.p1 = p1;
        this.p2 = p2;
    }

    String key() { return Dict.AND;}
}
