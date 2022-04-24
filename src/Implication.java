public class Implication extends BinaryConnectiveProp{


    public Implication(Proposition parent, Proposition A, Proposition B) {
        super(parent, A, B);
    }

    String key() { return Dict.IMP;}

    @Override
    Model.value truthValue(Model model) {
        Model.value valA = A.truthValue(model);
        Model.value valB = B.truthValue(model);
        if (valA == Model.value.F){
            return Model.value.T;
        } else if (valA == Model.value.unknown){
            return Model.value.unknown;
        }
        //A is true. It all depends on B
        return valB;
    }
}
