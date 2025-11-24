package org.moyo;

import java.util.*;


public class WordSquareSolver {

    private final DictionaryLoader loader;


    public WordSquareSolver(DictionaryLoader loader) {
        this.loader = loader;

    }

    public List<String> solveWordSquare(int size, String sequence) {
        validateInput(sequence);

        if (size <= 0) {
            return List.of();
        }

        Map<Character, Integer> availableLetters = parseLetterFrequencies(sequence);
        Set<String> validWords = filterValidWords(size, availableLetters);

        if (validWords.isEmpty()) {
            return List.of();
        }

        return buildSquare(size,validWords,availableLetters);
    }

    /**
     * Validates the input parameters
     */
    private void validateInput(String sequence) {

        if (sequence == null || sequence.isEmpty() ) {
            throw new IllegalArgumentException("Sequence must not be null or empty");
        }
        sequence =sequence.toLowerCase();
        for (char c : sequence.toCharArray()) {
            if (!Character.isLetter(c)) {
                throw new IllegalArgumentException("Sequence must only contain letters");
            }
        }
    }

    private Map<Character, Integer> parseLetterFrequencies(String sequence) {
        Map<Character, Integer> frequencies = new HashMap<>();

        for (char c : sequence.trim().toCharArray()) {
            if (c != ' ') {
                frequencies.put(c, frequencies.getOrDefault(c, 0) + 1);
            }
        }

        return frequencies;
    }


    private Set<String> filterValidWords(int size, Map<Character, Integer> availableLetters) {
        Set<String> dictionaryWords = loader.getDictionaryForSize(size);
        Set<String> validWords = new HashSet<>();

        for (String word : dictionaryWords) {
            if (canFormWord(word, availableLetters)) {
                validWords.add(word);
            }
        }

        return validWords;
    }


    private boolean canFormWord(String word, Map<Character, Integer> availableLetters) {
        Map<Character, Integer> wordFrequency = getLetterFrequency(word);

        for (Map.Entry<Character, Integer> entry : wordFrequency.entrySet()) {
            char letter = entry.getKey();
            int needed = entry.getValue();

            if (!availableLetters.containsKey(letter) || availableLetters.get(letter) < needed) {
                return false;
            }
        }
        return true;
    }


    private List<String> buildSquare(int size, Set<String> validWords, Map<Character, Integer> availableLetters) {
        WordSquareBuilder builder = new WordSquareBuilder(size, validWords);
        Optional<List<String>> result = builder.build(availableLetters);

        return result.orElse(List.of());
    }


    private Map<Character, Integer> getLetterFrequency(String word) {
        Map<Character, Integer> frequency = new HashMap<>();
        for (char c : word.toCharArray()) {
            frequency.put(c, frequency.getOrDefault(c, 0) + 1);
        }
        return frequency;
    }
}