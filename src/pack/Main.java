package pack;

/**
 * Created by evgen_001 on 03.10.2016.
 */
public class Main {
    public static void main(String[] args) {
        MachineGraph fr = new MachineGraph();
        fr.readFile("C:\\Users\\Evgenia\\IdeaProjects\\TAFYA2\\src\\files\\var1");
        fr.printResult();
//        fr.readWord("/a,;\"e");
        fr.readWord("a,;,7");
    }
}
