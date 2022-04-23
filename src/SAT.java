import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class SAT {

    public static boolean isSatisfiableDPLL(Proposition prop){
        HashMap<String,Unit> symbols = getSymbolsHashmap(prop);
        Proposition cnfProp = prop.toCNF();
        List<Proposition> clauses = getClausesFromCNF(cnfProp);
        return false;
    }

    public static boolean DPLL(List<Proposition> clauses, List<Unit> symbols){
        return false;
    }

    private static HashMap<String,Unit> getSymbolsHashmap(Proposition prop){
        List<Unit> units = getSymbols(prop);
        HashMap<String,Unit> map = new HashMap<>();
        for (Unit unit: units) {
            map.put(unit.symbol,unit);
        }
        return map;
    }

    private static List<Unit> getSymbols(Proposition prop){
        List<Unit> list = new ArrayList<>();
        if (prop instanceof Literal){
            String symbol = ((Literal) prop).var;
            Unit unit = new Unit(symbol,false);
            list.add(unit);
        } else if (prop instanceof Not && prop.A instanceof Literal){
            String symbol = ((Literal) (prop.A)).var;
            Unit unit = new Unit(symbol,true);
            list.add(unit);
        } else {
            list.addAll(getSymbols(prop.A));
            if (prop instanceof BinaryConnectiveProp){
                list.addAll(getSymbols(((BinaryConnectiveProp) prop).B));
            }
        }
        return list;
    }

    private static List<Proposition> getClausesFromCNF(Proposition prop){
        List<Proposition> list = new ArrayList<>();
        Proposition p = prop;
        if (prop instanceof And){
            list.addAll(getClausesFromCNF(((And) prop).B));
            list.addAll(getClausesFromCNF(prop.A));
        }

        return list;
    }



    private static class Unit{
        String symbol;
        boolean isNegated;
        boolean valueIsKnown;
        boolean value;


        public Unit(String symbol, boolean isNegated) {
            this.symbol = symbol;
            this.isNegated = isNegated;
        }

        public String toString(){
            return isNegated ? Dict.NOT+symbol : symbol;
        }
    }
}
