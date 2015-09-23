package Week1Java;

public class FindAndReplaceDriver {

    public static void main(String[] arguments) {

        FindAndReplace processing = new FindAndReplace("input.txt", "replacements.txt", "output.txt");
        processing.run();
    }
}
