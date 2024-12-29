package filefilter;

import java.io.*;
import java.nio.file.*;
import java.util.*;

public class FilesWriter {
    private final CommandLineParser parser;
    private final Map<String, BufferedWriter> writers = new HashMap<>();

    public FilesWriter(CommandLineParser parser) {
        this.parser = parser;
    }

    public void write(String type, Object value) throws IOException {
        BufferedWriter writer = getWriter(type);
        writer.write(value.toString());
        writer.newLine();
    }

    private BufferedWriter getWriter(String type) throws IOException {
        if (writers.containsKey(type)) return writers.get(type);

        String prefix = parser.getOption("prefix", "");
        String outputDir = parser.getOption("outputDir", ".");
        boolean append = "true".equals(parser.getOption("append", "false"));

        Path path = Paths.get(outputDir, prefix + type + ".txt");
        Files.createDirectories(path.getParent());

        BufferedWriter writer = Files.newBufferedWriter(
                path, append ? StandardOpenOption.APPEND : StandardOpenOption.CREATE
        );
        writers.put(type, writer);
        return writer;
    }

    public void closeAll() {
        for (BufferedWriter writer : writers.values()) {
            try {
                writer.close();
            } catch (IOException e) {
                System.err.println("Error closing writer");
            }
        }
    }
}
