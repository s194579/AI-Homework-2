import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class SAT {
    static List<Proposition> clauses;
    static List<String> symbols;

    public static boolean isSatisfiableDPLL(Proposition prop){
        // Get symbols
        symbols = getSymbols(prop);
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

        applyPureSymbolsSimplification(model);
        applyUnitClauseSimplification(model);

        // Assume values of first symbol and recurse
        String unknownSymbol = model.getFirstUnknownSymbol();
        Model model1 = model.getClone();
        model1.modelValues.put(unknownSymbol, Model.value.F);
        Model model2 = model.getClone();
        model2.modelValues.put(unknownSymbol, Model.value.T);

        return DPLL(model1) || DPLL(model2);
    }

    private static void applyPureSymbolsSimplification(Model model){
        // Find eventual pure symbols and simplify
        // If symbol only has positive sign in unresolved clauses then it is pure
        HashMap<String,Boolean> pureSymbolMap = new HashMap<String, Boolean>();
        for (Proposition clause: clauses) {
            boolean isResolved = Model.value.T==clause.truthValue(model);
            if (isResolved){ //Ignore clauses that are already resolved
                continue;
            }

            List<Proposition> literals = clauseToPropList(clause);
            for (Proposition literal: literals) {
                if (literal instanceof Literal){ //If it is not a Not(Literal)
                    // Check if already found in negated form
                    String symbol = ((Literal) literal).var;
                    Boolean val = pureSymbolMap.get(symbol);
                    if (val == null){
                        pureSymbolMap.put(symbol,true);
                    }
                } else if (literal instanceof Not && literal.A instanceof Literal){
                    String symbol = ((Literal) literal.A).var;
                    Boolean val = pureSymbolMap.get(symbol);
                    if (val == null || val == true){
                        pureSymbolMap.put(symbol,false);
                    }
                }
            }
        }

        // Find the pure symbols and assign value to true
        for (String symbol: pureSymbolMap.keySet()) {
            Boolean val = pureSymbolMap.get(symbol);
            if (val == true){
                model.modelValues.put(symbol, Model.value.T);
            }
        }
    }

    private static void applyUnitClauseSimplification(Model model){
        // Find eventual unit clauses and simplify
        // If there is only one symbol in clause with value "unknown" then it is unit clause
        for (Proposition clause: clauses) {
            boolean isResolved = Model.value.T==clause.truthValue(model);
            if (isResolved){ //Ignore clauses that are already resolved
                continue;
            }

            List<Proposition> literals = clauseToPropList(clause);
            for (Proposition literal: literals) {
                int numOfUnknownLiterals = 0;
                String latestUnkownSymbol = null;
                Model.value latestUnkownSymbolValue = Model.value.unknown;
                if (literal instanceof Literal){ //If it is not a Not(Literal)
                    // Check if already found in negated form
                    String symbol = ((Literal) literal).var;
                    if (model.modelValues.get(symbol)== Model.value.unknown){
                        numOfUnknownLiterals++;
                        latestUnkownSymbol = symbol;
                        latestUnkownSymbolValue = Model.value.T;
                    }
                } else if (literal instanceof Not && literal.A instanceof Literal){
                    String symbol = ((Literal) literal.A).var;
                    if (model.modelValues.get(symbol)== Model.value.unknown){
                        numOfUnknownLiterals++;
                        latestUnkownSymbol = symbol;
                        latestUnkownSymbolValue = Model.value.F;
                    }
                }

                // If unit clause then assign approriate value
                if (numOfUnknownLiterals == 1){
                    model.modelValues.put(latestUnkownSymbol,latestUnkownSymbolValue);
                }
            }
        }
    }

    private static List<String> getSymbols(Proposition prop){
        List<String> list = new ArrayList<>();
        if (prop instanceof Literal){
            String symbol = ((Literal) prop).var;
            list.add(symbol);
        } else if (prop instanceof Not && prop.A instanceof Literal){
            String symbol = ((Literal) (prop.A)).var;
            list.add(symbol);
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

    private static List<Proposition> clauseToPropList(Proposition prop){
        List<Proposition> props = new ArrayList<>();
        if (prop instanceof Literal || prop instanceof Not && prop.A instanceof Literal){
            props.add(prop);
            return props;
        }

        props.addAll(clauseToPropList(prop.A));
        if (prop instanceof Or){
            props.addAll(clauseToPropList(((Or)prop).B));
        }
        return props;
    }
}
