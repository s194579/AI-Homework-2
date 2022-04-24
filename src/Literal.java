public class Literal extends Proposition{
    String var;

    public Literal(Proposition parent, String var) {
        super(parent, null);
        if (var.contains(" ")){
            System.out.println("Cannot create a literal with identifier containing whitespace.");
            var = var.split(" ")[0];
            System.out.println("Using only first part of identifier");
        }
        this.var = var;
    }

    @Override
    public String toString() {
        return var;
    }

    String key() { return "";}

    @Override
    Model.value truthValue(Model model) {
        return model.modelValues.get(var);
    }


}
