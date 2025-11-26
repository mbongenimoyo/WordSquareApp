package org.moyo;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class WordSquareApp {
    private static final String DEFAULT_DICTIONARY_PATH = "src/main/resources/words.txt";
    private DictionaryLoader loader;
    private WordSquareSolver resolver;
    private boolean running = true;

    public WordSquareApp(String dictionaryPath) {
        System.out.println("Loading dictionary from: " + dictionaryPath);
        this.loader = new DictionaryLoader();
        this.loader.loadFromTextFile(dictionaryPath);
        this.resolver = new WordSquareSolver(loader);
        System.out.println("Dictionary loaded. Ready to solve word squares.");
    }

    public static void main(String[] args) {
        // Parse initial dictionary path if provided
        Map<String, String> initialArgs = parseArguments(args);
        String dictionaryPath = initialArgs.getOrDefault("dictionaryFilePath", DEFAULT_DICTIONARY_PATH);

        WordSquareApp app = new WordSquareApp(dictionaryPath);
        app.run();
    }

    private void run() {
        Scanner scanner = new Scanner(System.in);

        printHelp();

        while (running) {
            System.out.print("\n> ");
            String input = scanner.nextLine().trim();

            if (input.isEmpty()) {
                continue;
            }

            processCommand(input);
        }

        scanner.close();
        System.out.println("Exiting Word Square Solver. Goodbye!");
    }

    private void processCommand(String input) {
        String[] tokens = input.split("\\s+");
        String command = tokens[0].toLowerCase();

        switch (command) {
            case "solve":
                handleSolve(tokens);
                break;
            case "load":
                handleLoad(tokens);
                break;
            case "help":
                printHelp();
                break;
            case "exit":
            case "quit":
                running = false;
                break;
            default:
                System.err.println("Unknown command: " + command);
                System.out.println("Type 'help' for available commands.");
        }
    }

    private void handleSolve(String[] tokens) {
        try {
            // Convert tokens to args format
            String[] args = new String[tokens.length - 1];
            System.arraycopy(tokens, 1, args, 0, tokens.length - 1);

            Map<String, String> arguments = parseArguments(args);

            if (!arguments.containsKey("size") || !arguments.containsKey("sequence")) {
                System.err.println("Error: Missing required arguments.");
                System.out.println("Usage: solve -size <size> -sequence <letters>");
                System.out.println("Example: solve -size 5 -sequence aabbeeeeeeeehmosrrrruttvv");
                return;
            }

            int size = getSize(arguments);
            String sequence = arguments.get("sequence");

            System.out.println("Solving word square of size " + size + " with sequence: " + sequence);
            long startTime = System.currentTimeMillis();

            List<String> results = resolver.solveWordSquare(size, sequence);

            long endTime = System.currentTimeMillis();

            if (results.isEmpty()) {
                System.out.println("No solutions found.");
            } else {
                System.out.println("\nFound " + results.size() + " solution(s):\n");
                results.forEach(System.out::println);
            }
            System.out.println("\n...done in " + (endTime - startTime) + "ms");

        } catch (Exception e) {
            System.err.println("Error solving word square: " + e.getMessage());
        }
    }

    private void handleLoad(String[] tokens) {
        if (tokens.length < 2) {
            System.err.println("Error: Missing dictionary path.");
            System.out.println("Usage: load <path>");
            System.out.println("Example: load ./custom-dictionary.txt");
            return;
        }

        String path = tokens[1];
        try {
            System.out.println("Loading dictionary from: " + path);
            this.loader = new DictionaryLoader();
            this.loader.loadFromTextFile(path);
            this.resolver = new WordSquareSolver(loader);
            System.out.println("Dictionary loaded successfully.");
        } catch (Exception e) {
            System.err.println("Error loading dictionary: " + e.getMessage());
        }
    }

    private void printHelp() {
        System.out.print("""
            
            === Word Square Solver ===
            Available commands:
              solve -size <size> -sequence <letters>
                  Solve a word square with the given size and letter sequence
                  Example: solve -size 5 -sequence aabbeeeeeeeehmosrrrruttvv
            
              load <path>
                  Load a different dictionary file
                  Example: load ./custom-dictionary.txt
            
              help
                  Display this help message
            
              exit | quit
                  Exit the application
            
            """);
    }
    static Map<String, String> parseArguments(String[] args) {
        Map<String, String> arguments = new HashMap<>();

        for (int i = 0; i < args.length; i++) {
            if (args[i].startsWith("-")) {
                String key = args[i].substring(1);

                if (i + 1 < args.length && !args[i + 1].startsWith("-")) {
                    arguments.put(key, args[i + 1]);
                    i++;
                } else {
                    System.err.println("Error: Argument " + args[i] + " requires a value");
                }
            }
        }

        return arguments;
    }

    private static int getSize(Map<String, String> arguments) {
        int size = 0;
        try {
            size = Integer.parseInt(arguments.get("size"));
            if (size < 2) {
                throw new IllegalArgumentException("Size must be at least 2");
            }
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("Size must be a valid integer");
        }
        return size;
    }
}