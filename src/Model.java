import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Model{
    HashMap<String, value> modelValues = new HashMap<>();
    enum value {unknown, T, F}

    //Constructor creates model where no value of any symbol is known
    Model(List<String> symbols){
        for (String symbol: symbols) {
            this.modelValues.put(symbol,value.unknown);
        }
    }

    Model(){

    }

    String getFirstUnknownSymbol(){
        for (Map.Entry<String, value> entry :
                this.modelValues.entrySet()) {
            if (entry.getValue() == value.unknown){
                return entry.getKey();
            }
        }
        return null;
    }

    // Returns a clone of old model
    Model getClone(){
        Model newModel = new Model();
        for (Map.Entry<String, value> entry :
                this.modelValues.entrySet()) {
            newModel.modelValues.put(entry.getKey(),entry.getValue());
        }
        return newModel;
    }
}
