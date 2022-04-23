public class CNF {


    public Proposition toCNF(Proposition proposition){
        Proposition proposition1 = eliminate(proposition);
        Proposition proposition2 = demorgan(proposition1);
        Proposition proposition3 = distribute(proposition2);

        return proposition3;
    }

    private Proposition eliminate(Proposition proposition){
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

    private Proposition demorgan(Proposition proposition){
        if (proposition instanceof Not && proposition.A instanceof And) {
            Proposition proposition1 = new Not(null, proposition.A.A);
            Proposition proposition2 = new Not(null, ((And) proposition.A).B);
            Proposition proposition3 = demorgan(proposition1);
            Proposition proposition4 = demorgan(proposition2);
            Proposition proposition5 = new Or(null, proposition3, proposition4);
            proposition3.parent = proposition5;
            proposition4.parent = proposition5;
            return proposition5;
        }else if (proposition instanceof Not && proposition.A instanceof Or){
            Proposition proposition1 = new Not(null, proposition.A.A);
            Proposition proposition2 = new Not(null, ((Or) proposition.A).B);
            Proposition proposition3 = demorgan(proposition1);
            Proposition proposition4 = demorgan(proposition2);
            Proposition proposition5 = new And(null, proposition3, proposition4);
            proposition3.parent = proposition5;
            proposition4.parent = proposition5;
            return proposition5;
        }else if (proposition instanceof And){
            Proposition proposition1 = demorgan(proposition.A);
            Proposition proposition2 = demorgan(((And) proposition).B);
            Proposition proposition3 = new And(null,proposition1,proposition2);
            proposition1.parent = proposition3;
            proposition2.parent = proposition3;
            return proposition3;
        }else if (proposition instanceof Or){
            Proposition proposition1 = demorgan(proposition.A);
            Proposition proposition2 = demorgan(((Or) proposition).B);
            Proposition proposition3 = new Or(null,proposition1,proposition2);
            proposition1.parent = proposition3;
            proposition2.parent = proposition3;
            return proposition3;
        }else if (proposition instanceof Not && proposition.A instanceof Not){
            Proposition proposition1 = demorgan(proposition.A.A);
            return  proposition1;
        }else  if (proposition instanceof Not && proposition.A instanceof Literal){
            return proposition;
        } else  if (proposition instanceof Literal){
            return proposition;
        }
        return null;
    }

    private Proposition distribute(Proposition proposition){
        if(proposition instanceof And){
            Proposition proposition1 = distribute(proposition.A);
            Proposition proposition2 = distribute(((And) proposition).B);
            Proposition proposition3 = new And(null,proposition1,proposition2);
            proposition1.parent = proposition3;
            proposition2.parent = proposition2;
            return proposition3;
        } else if (proposition instanceof Or){
            Proposition proposition1 = distribute(proposition.A);
            Proposition proposition2 = distribute(((Or) proposition).B);
            Proposition proposition3 = distribute(proposition1,proposition2);
            return  proposition3;
        } else if (proposition instanceof Not && proposition.A instanceof Literal){
            return proposition;
        } else if (proposition instanceof Literal){
            return proposition;
        }
        return null;
    }

    private  Proposition distribute(Proposition proposition1,Proposition proposition2){
        if (proposition1 instanceof And){
            Proposition proposition3 = distribute(proposition1.A,proposition2);
            Proposition proposition4 = distribute(((And) proposition1).B,proposition2);
            Proposition proposition = new And(null,proposition3,proposition4);
            proposition3.parent = proposition;
            proposition4.parent = proposition;
            return proposition;
        } else  if (proposition2 instanceof And) {
            Proposition proposition3 = distribute(proposition1, proposition2.A);
            Proposition proposition4 = distribute( proposition1, ((And) proposition2).B);
            Proposition proposition = new And(null, proposition3, proposition4);
            proposition3.parent = proposition;
            proposition4.parent = proposition;
            return proposition;
        }else{
            Proposition proposition = new Or(null,proposition1,proposition2);
            proposition1.parent = proposition;
            proposition2.parent = proposition;
            return  proposition;
        }
    }

}
