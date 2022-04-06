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
}
