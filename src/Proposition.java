public abstract class Proposition {
    Proposition A;


    void toCNF(){
        while (this.containsConnective(Dict.BIIMP)){

        }

        while (this.containsConnective(Dict.IMP)){

        }

        while (!this.isCNF()){
            //Perform distribution and de morgan
        }

    }

    abstract String key();

    boolean containsConnective(String key){
        if (this.key() == key){
            return true;
        }

        if (this instanceof Literal){
            return false;
        }

        boolean aContains = this.A.containsConnective(key);
        if (this instanceof BinaryConnectiveProp){
            boolean bContains = ((BinaryConnectiveProp)this).B.containsConnective(key);
            return aContains || bContains;
        }
        return aContains;
    }

    boolean isCNF(){
        if (this.isLogicalLiteral()){
            return true;
        }

        if (this.isClause()){
            return true;
        }

        if (this instanceof And){
            return this.A.isCNF() && ((Or)this).B.isCNF();
        }

        return false;
    }

    boolean isClause(){
        if (this.isLogicalLiteral()){
            return true;
        }

        if (this instanceof Or){
            return this.A.isClause() && ((Or)this).B.isClause();
        }

        return false;
    }

    //Return true only if of type !A, !B, A, C so on (Literal | (Not Literal))
    boolean isLogicalLiteral(){
        if (this instanceof Literal){
            return true;
        }
        if (this instanceof Not){
            return this.A instanceof Literal;
        }
        return false;
    }

}
