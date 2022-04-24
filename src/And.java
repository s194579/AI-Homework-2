public class And extends BinaryConnectiveProp{
    public And(Proposition parent, Proposition A, Proposition B) {
        super(parent, A, B);
    }

    String key() { return Dict.AND;}

    @Override
    Model.value truthValue(Model model) {
        Model.value valA = A.truthValue(model);
        Model.value valB = B.truthValue(model);
        if (valA == Model.value.F || valB == Model.value.F){
            return Model.value.F;
        } else if (valA == Model.value.unknown || valB == Model.value.unknown){
            return Model.value.unknown;
        }
        return Model.value.T;
    }
}
