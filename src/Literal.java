public class Literal extends Proposition{
    String var;

    public Literal(Proposition parent, Proposition A) {
        super(parent, A);
    }

    @Override
    public String toString() {
        return var;
    }

    String key() { return "";}
}
