public class Literal extends Proposition{
    String var;

    public Literal(Proposition parent, Proposition A) {
        super(parent, A);
    }

    String key() { return "";}
}
