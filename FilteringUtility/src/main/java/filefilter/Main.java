package filefilter;

public class Main {
    public static void main(String[] args) {
        CommandLineParser parser = new CommandLineParser(args);
        if (!parser.isValid()) {
            System.out.println("Error: Incorrect startup parameters.");
            return;
        }

        FileProcessor processor = new FileProcessor(parser);
        processor.processFiles();
        processor.printStatistics();
    }
}