public class Literal extends Proposition{

    //A should always be null in Literal
    public Literal(Proposition parent, Proposition A) {
        super(parent, A);
    }

    String key() { return "";}
}
