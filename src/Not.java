public class Not extends Proposition{

    public Not(Proposition parent, Proposition A) {
        super(parent, A);
    }

    @Override
    public String toString() {
        return "(" + key() + this.A.toString() + ")";
    }

    String key() { return Dict.NOT;}

    @Override
    Model.value truthValue(Model model) {
        Model.value val = A.truthValue(model);
        switch (val){
            case T:
                return Model.value.F;
            case F:
                return Model.value.T;
            case unknown:
                return Model.value.unknown;
        }
        return null;
    }
}
