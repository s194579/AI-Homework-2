import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SAT {
    static List<Proposition> clauses;
    static HashMap<String,Unit> symbols;

    public static boolean isSatisfiableDPLL(Proposition prop){
        // Get symbols
        symbols = getSymbolsHashmap(prop);
        Proposition cnfProp = prop.toCNF();

        // Get clauses
        clauses = getClausesFromCNF(cnfProp);

        //Create empty model (no values of any symbol is known)
        Model model = new Model(symbols);

        // Determine satisfiability recursively using DPLL
        boolean satisfiable = DPLL(model);
        return satisfiable;
    }

    public static boolean DPLL(Model model){
        //Terminate if we can
        boolean allTrueSoFar = true;
        for (Proposition clause:clauses) {
            Model.value value = clause.truthValue(model);
            // If any clause is false - return false
            if (value == Model.value.F){
                return false;
            } else if (value == Model.value.unknown){
                allTrueSoFar = false;
            }
        }
        // If every clause is true - return true
        if (allTrueSoFar){
            return true;
        }

        //TODO add these optimizations
        // Find eventual pure symbols and simplify
        // Find eventual unit clauses and simplify

        // Assume values of first symbol and recurse
        String unknownSymbol = model.getFirstUnknownSymbol();
        Model model1 = model.getClone();
        model1.modelValues.put(unknownSymbol, Model.value.F);
        Model model2 = model.getClone();
        model2.modelValues.put(unknownSymbol, Model.value.T);

        return DPLL(model1) || DPLL(model2);
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
        if (prop instanceof And){
            list.addAll(getClausesFromCNF(((And) prop).B));
            list.addAll(getClausesFromCNF(prop.A));
        } else {
            list.add(prop);
        }

        return list;
    }

    static class Unit{
        String symbol;
        boolean isNegated;

        public Unit(String symbol, boolean isNegated) {
            this.symbol = symbol;
            this.isNegated = isNegated;
        }

        public String toString(){
            return isNegated ? Dict.NOT+symbol : symbol;
        }
    }
}
