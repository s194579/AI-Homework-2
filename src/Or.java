public class Or extends BinaryConnectiveProp{


    public Or(Proposition parent, Proposition A, Proposition B) {
        super(parent, A, B);
    }

    String key() { return Dict.OR;}

    @Override
    Model.value truthValue(Model model) {
        Model.value valA = A.truthValue(model);
        Model.value valB = B.truthValue(model);
        if (valA == Model.value.T || valB == Model.value.T){
        return Model.value.T;
        }else if (valA == Model.value.unknown || valB == Model.value.unknown){
            return Model.value.unknown;
        }
        return Model.value.F;
    }
}
