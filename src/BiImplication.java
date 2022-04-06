public class BiImplication extends BinaryConnectiveProp{
    public BiImplication(Proposition A, Proposition B) {
        super(A, B);
    }

    String key() { return Dict.BIIMP;}

}
