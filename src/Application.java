import java.util.Scanner;


public class Application {
    public static void main(String[] args) {
        KnowledgeBase kb = new KnowledgeBase();
        kb.addProp();
        kb.addProp();
        kb.addProp();
        System.out.println(kb.toString());


    }
}
