public class Not extends Proposition{

    public Not(Proposition parent, Proposition A) {
        super(parent, A);
    }

    String key() { return Dict.NOT;}
}
