import java.util.ArrayList;
import java.util.List;

public class PropUtil {

    public static List<Proposition> clauseToPropList(Proposition prop){
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

    public static List<Proposition> getClausesFromCNF(Proposition prop){
        List<Proposition> list = new ArrayList<>();
        if (prop instanceof And){
            list.addAll(getClausesFromCNF(((And) prop).B));
            list.addAll(getClausesFromCNF(prop.A));
        } else {
            list.add(prop);
        }

        return list;
    }

    public static List<String> getSymbols(Proposition prop){
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

    public static Proposition toSingleProposition(List<Proposition> list){
        // Convert a knowledge base to a single sentence connected by AND
        if(list.size() == 1){
            return list.get(0);
        }

        Proposition result = new And(null,list.get(0),list.get(1));

        for (int i = 2; i < list.size(); i++) {
            Proposition nextLayer = new And(null, result, list.get(i));
            result = nextLayer;
        }
        return result;
    }
}
