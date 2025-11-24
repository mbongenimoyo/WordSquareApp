package org.moyo;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class WordSquareApp {
    private static final String DEFAULT_DICTIONARY_PATH = "src/main/resources/words.txt";

    public static void main(String[] args) {
        Map<String, String> arguments = parseArguments(args);

        validateRequiredArguments(arguments);

        int size = getSize(arguments);
        String sequence = arguments.get("sequence");
        String dictionaryPath = arguments.getOrDefault("dictionaryFilePath", DEFAULT_DICTIONARY_PATH);

        DictionaryLoader loader = new DictionaryLoader();
        loader.loadFromTextFile(dictionaryPath);

        WordSquareSolver resolver = new WordSquareSolver(loader);

        List<String> results = resolver.solveWordSquare(size, sequence);

        if (results.isEmpty()) {
            System.out.println("No solutions found.");
        } else {
            results.forEach(System.out::println);
        }
    }

    static Map<String, String> parseArguments(String[] args) {
        Map<String, String> arguments = new HashMap<>();

        for (int i = 0; i < args.length; i++) {
            if (args[i].startsWith("-")) {
                String key = args[i].substring(1); // Remove the '-' prefix

                if (i + 1 < args.length && !args[i + 1].startsWith("-")) {
                    arguments.put(key, args[i + 1]);
                    i++; // Skip the next argument since we've used it as a value
                } else {
                    System.err.println("Error: Argument " + args[i] + " requires a value");
                    System.exit(1);
                }
            }
        }

        return arguments;
    }

    static void validateRequiredArguments(Map<String, String> arguments) {
        if (!arguments.containsKey("size") || !arguments.containsKey("sequence")) {
            System.err.println("Usage: java Main -size <size> -sequence <letters> [-dictionaryFilePath <path>]");
            System.err.println("Example: java Main -size 5 -sequence aabbeeeeeeeehmosrrrruttvv");
            System.err.println("Example: java Main -size 5 -sequence aabbeeeeeeeehmosrrrruttvv -dictionaryFilePath ./custom-dictionary.txt");
            System.exit(1);
        }
    }

    static int getSize(Map<String, String> arguments) {
        int size = 0;
        try {
            size = Integer.parseInt(arguments.get("size"));
            if (size < 2) {
                System.err.println("Error: Size must be at least 2");
                System.exit(1);
            }
        } catch (NumberFormatException e) {
            System.err.println("Error: Size must be a valid integer");
            System.exit(1);
        }
        return size;
    }
}