public class Not extends Proposition{

    private Proposition p1;

    public Not(Proposition p1) {
        this.p1 = p1;
    }

    String key() { return Dict.NOT;}
}
