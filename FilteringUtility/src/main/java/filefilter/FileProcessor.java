package filefilter;

import java.io.*;
import java.nio.file.*;

public class FileProcessor {
    private final CommandLineParser parser;
    private final FilesWriter fileWriter;
    private final StatisticsCollector statistics;

    public FileProcessor(CommandLineParser parser) {
        this.parser = parser;
        this.fileWriter = new FilesWriter(parser);
        this.statistics = new StatisticsCollector();
    }

    public void processFiles() {
        for (String fileName : parser.getInputFiles()) {
            try (BufferedReader reader = Files.newBufferedReader(Paths.get(fileName))) {
                String line;
                while ((line = reader.readLine()) != null) {
                    processLine(line);
                }
            } catch (IOException e) {
                System.err.println("Error file reading: " + fileName);
            }
        }
        fileWriter.closeAll();
    }
    private void processLine(String line) {
        if (line.matches("-?\\d+")) {
            try {
                long value = Long.parseLong(line);
                fileWriter.write("integers", value);
                statistics.addIntegerNumber(value);
            } catch (NumberFormatException e) {
                System.err.println("A number outside the range of acceptable values for long: " + line);
            } catch (IOException e) {
                System.err.println("Error writing to a file: integers");
            }

        } else if (line.matches("-?\\d*\\.\\d+([eE][-+]?\\d+)?")) {
            try {
                double value = Double.parseDouble(line);
                fileWriter.write("floats", value);
                statistics.addDoubleNumber(value);
            } catch (IOException e) {
                System.err.println("Error writing to a file: floats");
            }
        } else {
            try {
                fileWriter.write("strings", line);
                statistics.addString(line);
            } catch (IOException e) {
                System.err.println("Error writing to a file: strings");
            }
        }
    }

    public void printStatistics() {
        if ("true".equals(parser.getOption("full_statistics", "false"))){
            System.out.println(statistics.getFullStatistics());
        } else if ("true".equals(parser.getOption("short_statistics", "false"))) {
            System.out.println(statistics.getShortStatistics());
        } else System.out.println();
    }
}
