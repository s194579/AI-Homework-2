public class BiImplication extends BinaryConnectiveProp{


    public BiImplication(Proposition parent, Proposition A, Proposition B) {
        super(parent, A, B);
    }

    String key() { return Dict.BIIMP;}

}
