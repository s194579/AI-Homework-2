public class CNF {


    public Proposition toCNF(Proposition proposition){

        return null;
    }

    public Proposition eliminate(Proposition proposition){
        if (proposition instanceof Not ){
            Proposition proposition1 = eliminate(proposition.A);
            Proposition proposition2 = new Not( null,proposition1);
            proposition1.parent = proposition2;
            return  proposition2;
        } else if (proposition instanceof Or) {
            Proposition proposition1 = eliminate(proposition.A);
            Proposition proposition2 = eliminate(((Or) proposition).B);
            Proposition proposition3 = new Or(null, proposition1, proposition2);
            proposition1.parent = proposition3;
            proposition2.parent = proposition3;
            return proposition3;
        } else if (proposition instanceof And){
            Proposition proposition1 = eliminate( proposition.A);
            Proposition proposition2 = eliminate(((And) proposition).B);
            Proposition proposition3 = new And(null,proposition1,proposition2);
            proposition1.parent = proposition3;
            proposition2.parent = proposition3;
            return proposition3;
        } else if (proposition instanceof Implication){
            Proposition proposition1 = eliminate( proposition.A);
            Proposition proposition2 = eliminate(((Implication) proposition).B);
            Proposition proposition3 = new Not(null,proposition1);
            proposition1.parent =proposition3;
            Proposition proposition4 = new Or(null,proposition3,proposition2);
            proposition2.parent = proposition4;
            proposition3.parent = proposition4;
            return  proposition4;
        } else if (proposition instanceof BiImplication){
            Proposition proposition1 = eliminate(proposition.A);
            Proposition proposition2 = eliminate(((BiImplication) proposition).B);
            Proposition proposition3 = eliminate(proposition.A);
            Proposition proposition4 = eliminate(((BiImplication) proposition).B);
            Proposition proposition5 = new And(null,proposition1,proposition2);
            proposition1.parent = proposition5;
            proposition2.parent = proposition5;
            Proposition proposition6 = new Not(null, proposition3);
            proposition3.parent = proposition6;
            Proposition proposition7 = new Not(null, proposition4);
            proposition4.parent = proposition7;

            Proposition proposition8 = new And(null, proposition6,proposition7);
            proposition6.parent = proposition8;
            proposition7.parent = proposition8;

            Proposition proposition9 = new Or(null,proposition5,proposition8);
            proposition5.parent = proposition9;
            proposition8.parent = proposition9;
            return proposition9;
        }else if (proposition instanceof  Literal){
            return proposition;
        }
        return null;
    }


}
