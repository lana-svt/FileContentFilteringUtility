package filefilter;

import java.util.*;

public class CommandLineParser {
    private final Map<String, String> options = new HashMap<>();
    private final List<String> inputFiles = new ArrayList<>();
    private boolean valid = true;

    public CommandLineParser(String[] args) {
        parse(args);
    }

    private void parse(String[] args) {
        for (int i = 0; i < args.length; i++) {
            if (args[i].startsWith("-")) {
                switch (args[i]) {
                    case "-o":
                        if (i + 1 < args.length) options.put("outputDir", args[++i]);
                        else valid = false;
                        break;
                    case "-p":
                        if (i + 1 < args.length) options.put("prefix", args[++i]);
                        else valid = false;
                        break;
                    case "-a":
                        options.put("append", "true");
                        break;
                    case "-s":
                        options.put("short_statistics", "true");
                        break;
                    case "-f":
                        options.put("full_statistics", "true");
                        break;
                    default:
                        valid = false;
                }
            } else {
                inputFiles.add(args[i]);
            }
        }

        if (inputFiles.isEmpty()) valid = false;
    }

    public boolean isValid() {
        return valid;
    }

    public String getOption(String key, String defaultValue) {
        return options.getOrDefault(key, defaultValue);
    }

    public List<String> getInputFiles() {
        return inputFiles;
    }
}
