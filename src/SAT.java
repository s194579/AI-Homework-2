import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class SAT {
    static List<Proposition> clauses;
    static List<String> symbols;

    public static boolean isSatisfiableDPLL(Proposition prop){
        // Get symbols
        symbols = PropUtil.getSymbols(prop);
        Proposition cnfProp = prop.toCNF();

        // Get clauses
        clauses = PropUtil.getClausesFromCNF(cnfProp);

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
        // If symbol has same sign in all unresolved clauses then it is pure
        HashMap<String,Boolean> positivePureSymbolMap = new HashMap<String, Boolean>();
        HashMap<String,Boolean> negativePureSymbolMap = new HashMap<String, Boolean>();
        for (Proposition clause: clauses) {
            boolean isResolved = Model.value.T==clause.truthValue(model);
            if (isResolved){ //Ignore clauses that are already resolved
                continue;
            }

            List<Proposition> literals = PropUtil.clauseToPropList(clause);
            for (Proposition literal: literals) {
                if (literal instanceof Literal){ //If it is not a Not(Literal)
                    // Check if already found in negated form
                    String symbol = ((Literal) literal).var;
                    Boolean pval = positivePureSymbolMap.get(symbol);
                    Boolean nval = negativePureSymbolMap.get(symbol);
                    if (pval == null){
                        positivePureSymbolMap.put(symbol,true);
                    }
                    if (nval == null || true){
                        negativePureSymbolMap.put(symbol,false);
                    }
                } else if (literal instanceof Not && literal.A instanceof Literal){
                    String symbol = ((Literal) literal.A).var;
                    Boolean pval = positivePureSymbolMap.get(symbol);
                    Boolean nval = negativePureSymbolMap.get(symbol);
                    if (nval == null){
                        negativePureSymbolMap.put(symbol,true);
                    }
                    if (pval == null || true){
                        positivePureSymbolMap.put(symbol,false);
                    }
                }
            }
        }

        // Find the positive pure symbols and assign value to true
        for (String symbol: positivePureSymbolMap.keySet()) {
            Boolean val = positivePureSymbolMap.get(symbol);
            if (val == true){
                model.modelValues.put(symbol, Model.value.T);
            }
        }

        // Find the negative pure symbols and assign value to true
        for (String symbol: negativePureSymbolMap.keySet()) {
            Boolean val = negativePureSymbolMap.get(symbol);
            if (val == true){
                model.modelValues.put(symbol, Model.value.F);
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

            List<Proposition> literals = PropUtil.clauseToPropList(clause);
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


}
