
package org.moyo;

import java.util.*;

/**
 * Responsible for building word squares using backtracking algorithm.
 * Maintains the constraint that row[i] = column[i] for all positions.
 */
public class WordSquareBuilder {

    private final Set<String> validWords;
    final int size;

    public WordSquareBuilder(int size, Set<String> validWords) {
        this.validWords = validWords;
        this.size = size;
    }



    public Optional<List<String>> build(Map<Character, Integer> remainingLetters) {
        List<String> currentSquare = new ArrayList<>();
        if (buildSquare(currentSquare, remainingLetters)) {
            return Optional.of(currentSquare);
        }
        return Optional.empty();
    }


    private boolean buildSquare(List<String> currentSquare, Map<Character, Integer> remainingLetters) {
        // Base case: completed the square
        if (currentSquare.size() == size) {
            return true;
        }
        int row = currentSquare.size();
        String requiredPrefix = getRequiredPrefix(currentSquare, row);

        // Try each word that could fit in this row
        for (String candidateWord : validWords) {
            if (!matchesPrefix(candidateWord, requiredPrefix)) {
                continue;
            }

            if (!hasEnoughLetters(candidateWord, remainingLetters)) {
                continue;
            }

            if (!maintainsSquareProperty(size,currentSquare, candidateWord, row)) {
                continue;
            }

            currentSquare.add(candidateWord);
            Map<Character, Integer> updatedLetters = consumeLetters(candidateWord, remainingLetters);

            if (buildSquare(currentSquare, updatedLetters)) {
                System.out.println("Found solution: " + currentSquare);
                return true;
            }
            System.out.println("Backtracking...");
            currentSquare.remove(currentSquare.size() - 1);
        }

        return false;
    }


    private String getRequiredPrefix(List<String> currentSquare, int row) {
        if (row == 0) {
            return "";
        }

        StringBuilder prefix = new StringBuilder();
        for (int col = 0; col < row; col++) {
            prefix.append(currentSquare.get(col).charAt(row));
        }
        return prefix.toString();
    }


 
    
    protected boolean matchesPrefix(String word, String prefix) {
        return word.startsWith(prefix);
    }


    protected boolean hasEnoughLetters(String word, Map<Character, Integer> remainingLetters) {
        Map<Character, Integer> needed = getLetterFrequency(word);

        for (Map.Entry<Character, Integer> entry : needed.entrySet()) {
            char letter = entry.getKey();
            int count = entry.getValue();

            if (!remainingLetters.containsKey(letter) || remainingLetters.get(letter) < count) {
                return false;
            }
        }

        return true;
    }

    /**
     * Validates that placing this word maintains the word square property.
     * Ensures that each partial column can potentially complete to a valid word.
     */
    protected boolean maintainsSquareProperty(int size,List<String> currentSquare, String candidateWord, int row) {
        // Check each column that extends beyond the current row
        for (int col = row + 1; col < size; col++) {
            String partialColumn = buildPartialColumn(currentSquare, candidateWord, row, col);

            if (!canCompleteToValidWord(partialColumn)) {
                return false;
            }
        }

        return true;
    }

    /**
     * Builds the partial column word up to the current row
     */
    protected String buildPartialColumn(List<String> currentSquare, String candidateWord, int row, int col) {
        StringBuilder column = new StringBuilder();

        // Add characters from previous rows
        for (int r = 0; r < row; r++) {
            column.append(currentSquare.get(r).charAt(col));
        }

        // Add character from current candidate word
        column.append(candidateWord.charAt(col));

        return column.toString();
    }

    /**
     * Checks if any valid word starts with this prefix
     */
    private boolean canCompleteToValidWord(String prefix) {
        for (String word : validWords) {
            if (word.startsWith(prefix)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Returns a new map with the letters from the word consumed
     */
    protected Map<Character, Integer> consumeLetters(String word, Map<Character, Integer> availableLetters) {
        Map<Character, Integer> updated = new HashMap<>(availableLetters);

        for (char c : word.toCharArray()) {
            int count = updated.get(c);
            if (count == 1) {
                updated.remove(c);
            } else {
                updated.put(c, count - 1);
            }
        }

        return updated;
    }


    private Map<Character, Integer> getLetterFrequency(String word) {
        Map<Character, Integer> frequency = new HashMap<>();
        for (char c : word.toCharArray()) {
            frequency.put(c, frequency.getOrDefault(c, 0) + 1);
        }
        return frequency;
    }
}