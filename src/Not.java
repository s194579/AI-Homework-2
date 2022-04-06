public class Not extends Proposition{
    public Not(Proposition A) {
        super(A);
    }
    String key() { return Dict.NOT;}
}
