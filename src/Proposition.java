public abstract class Proposition {
    Proposition A;


    public Proposition toProposition(String prop){

       if (prop.contains("(")){
           boolean  findp1 = true;
           boolean b = false;
           int counter = 0;
           int startp1 = 0;
           int endp1 = 0;
           int startp2 = 0;
           int endp2 = 0;

           String partTwo;
           for (int i = 0; i < prop.length(); i++){
               if(prop.charAt(i) == '('){
                   if(!b){
                       b = true;
                       if(findp1) {
                           startp1 = i;
                       }else{
                           startp2 = i;
                       }
                   }
                   counter ++;
               }
               else if(prop.charAt(i) == ')'){
                   counter --;
               }
               if (b && counter == 0){
                   if(findp1) {
                       endp1 = i;
                   }else{
                       endp2 = i;
                       break;
                   }
                   b =false;
               }
           }

           Proposition p1 = toProposition((String) prop.subSequence(startp1+1,endp1-1));
           Proposition p2 = toProposition((String) prop.subSequence(startp2+1,endp2-1));
           String m = prop.substring(endp1+1,startp2-1);
           if(m.contains(""))

       }





    }
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
