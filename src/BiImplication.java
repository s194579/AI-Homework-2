public class BiImplication extends BinaryConnectiveProp{


    public BiImplication(Proposition parent, Proposition A, Proposition B) {
        super(parent, A, B);
    }

    String key() { return Dict.BIIMP;}

    @Override
    Model.value truthValue(Model model) {
        Model.value valA = A.truthValue(model);
        Model.value valB = B.truthValue(model);
        if (valA == Model.value.unknown || valB == Model.value.unknown){
            return Model.value.unknown;
        }
        //Return T if they are the same - F otherwise
        if (valA == valB){
            return Model.value.T;
        }
        return Model.value.F;
    }

}
