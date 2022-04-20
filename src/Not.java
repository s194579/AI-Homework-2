public class Not extends Proposition{

    public Not(Proposition parent, Proposition A) {
        super(parent, A);
    }

    @Override
    public String toString() {
        return "(" + key() + this.A.toString() + ")";
    }

    String key() { return Dict.NOT;}
}
