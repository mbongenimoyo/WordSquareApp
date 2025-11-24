package org.moyo;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

class WordSquareBuilderTest {

    private Set<String> validWords;
    private WordSquareBuilder builder;

    @BeforeEach
    void setUp() {
        validWords = new HashSet<>();
    }

    @Test
    @DisplayName("Given empty word set and letters, when building square, then return empty result")
    void givenEmptyWordSetAndLetters_whenBuildingSquare_thenReturnEmptyResult() {
        // Given
        validWords = Set.of();
        builder = new WordSquareBuilder(3, validWords);
        Map<Character, Integer> letters = Map.of('a', 1, 'b', 1, 'c', 1);

        // When
        Optional<List<String>> result = builder.build(letters);

        // Then
        assertFalse(result.isPresent());
    }

    @Test
    @DisplayName("Given valid words and exact letters for 2x2 square, when building square, then return valid solution")
    void givenValidWordsAndExactLettersFor2x2Square_whenBuildingSquare_thenReturnValidSolution() {
        // Given
        validWords = Set.of("to", "on");
        builder = new WordSquareBuilder(2, validWords);
        Map<Character, Integer> letters = new HashMap<>();
        letters.put('t', 1);
        letters.put('o', 2);
        letters.put('n', 1);

        // When
        Optional<List<String>> result = builder.build(letters);

        // Then
        assertTrue(result.isPresent());
        List<String> square = result.get();
        assertEquals(2, square.size());
        assertEquals("to", square.get(0));
        assertEquals("on", square.get(1));

        // Verify word square property
        assertEquals(square.get(0).charAt(0), square.get(0).charAt(0)); // t = t
        assertEquals(square.get(0).charAt(1), square.get(1).charAt(0)); // o = o
        assertEquals(square.get(1).charAt(0), square.get(0).charAt(1)); // o = o
        assertEquals(square.get(1).charAt(1), square.get(1).charAt(1)); // n = n
    }

    @Test
    @DisplayName("Given valid words and insufficient letters, when building square, then return empty result")
    void givenValidWordsAndInsufficientLetters_whenBuildingSquare_thenReturnEmptyResult() {
        // Given
        validWords = Set.of("test", "word", "square");
        builder = new WordSquareBuilder(2, validWords);
        Map<Character, Integer> letters = Map.of('a', 1, 'b', 1); // Not enough letters

        // When
        Optional<List<String>> result = builder.build(letters);

        // Then
        assertFalse(result.isPresent());
    }

    @Test
    @DisplayName("Given word matches prefix, when checking matchesPrefix, then return true")
    void givenWordMatchesPrefix_whenCheckingMatchesPrefix_thenReturnTrue() {
        // Given
        builder = new WordSquareBuilder(3, validWords);
        String word = "hello";
        String prefix = "hel";

        // When
        boolean result = builder.matchesPrefix(word, prefix);

        // Then
        assertTrue(result);
    }

    @Test
    @DisplayName("Given word does not match prefix, when checking matchesPrefix, then return false")
    void givenWordDoesNotMatchPrefix_whenCheckingMatchesPrefix_thenReturnFalse() {
        // Given
        builder = new WordSquareBuilder(3, validWords);
        String word = "hello";
        String prefix = "hex";

        // When
        boolean result = builder.matchesPrefix(word, prefix);

        // Then
        assertFalse(result);
    }

    @Test
    @DisplayName("Given empty prefix, when checking matchesPrefix, then return true")
    void givenEmptyPrefix_whenCheckingMatchesPrefix_thenReturnTrue() {
        // Given
        builder = new WordSquareBuilder(3, validWords);
        String word = "hello";
        String prefix = "";

        // When
        boolean result = builder.matchesPrefix(word, prefix);

        // Then
        assertTrue(result);
    }

    @Test
    @DisplayName("Given word can be formed from available letters, when checking hasEnoughLetters, then return true")
    void givenWordCanBeFormedFromAvailableLetters_whenCheckingHasEnoughLetters_thenReturnTrue() {
        // Given
        builder = new WordSquareBuilder(3, validWords);
        String word = "test";
        Map<Character, Integer> availableLetters = Map.of(
                't', 2,
                'e', 1,
                's', 1
        );

        // When
        boolean result = builder.hasEnoughLetters(word, availableLetters);

        // Then
        assertTrue(result);
    }

    @Test
    @DisplayName("Given word cannot be formed due to missing letters, when checking hasEnoughLetters, then return false")
    void givenWordCannotBeFormedDueToMissingLetters_whenCheckingHasEnoughLetters_thenReturnFalse() {
        // Given
        builder = new WordSquareBuilder(3, validWords);
        String word = "test";
        Map<Character, Integer> availableLetters = Map.of(
                't', 1,  // Need 2 t's but only have 1
                'e', 1,
                's', 1
        );

        // When
        boolean result = builder.hasEnoughLetters(word, availableLetters);

        // Then
        assertFalse(result);
    }

    @Test
    @DisplayName("Given word cannot be formed due to missing character, when checking hasEnoughLetters, then return false")
    void givenWordCannotBeFormedDueToMissingCharacter_whenCheckingHasEnoughLetters_thenReturnFalse() {
        // Given
        builder = new WordSquareBuilder(3, validWords);
        String word = "test";
        Map<Character, Integer> availableLetters = Map.of(
                't', 2,
                'e', 1
                // Missing 's'
        );

        // When
        boolean result = builder.hasEnoughLetters(word, availableLetters);

        // Then
        assertFalse(result);
    }

    @Test
    @DisplayName("Given partial column and candidate word, when building partial column, then return correct string")
    void givenPartialColumnAndCandidateWord_whenBuildingPartialColumn_thenReturnCorrectString() {
        // Given
        validWords = Set.of("one", "two", "three");
        builder = new WordSquareBuilder(3, validWords);
        List<String> currentSquare = Arrays.asList("one", "two");
        String candidateWord = "tea";
        int row = 2; // Third row
        int col = 1; // Second column

        // When
        String partialColumn = builder.buildPartialColumn(currentSquare, candidateWord, row, col);

        // Then
        // Column 1: from row0: 'n' (from "one"), from row1: 'w' (from "two"), from current: 'e' (from "tea")
        assertEquals("nwe", partialColumn);
    }

    @Test
    @DisplayName("Given letters are consumed, when calling consumeLetters, then return updated frequency map")
    void givenLettersAreConsumed_whenCallingConsumeLetters_thenReturnUpdatedFrequencyMap() {
        // Given
        builder = new WordSquareBuilder(3, validWords);
        String word = "aabbc";
        Map<Character, Integer> availableLetters = new HashMap<>();
        availableLetters.put('a', 3);
        availableLetters.put('b', 2);
        availableLetters.put('c', 1);
        availableLetters.put('d', 1);

        // When
        Map<Character, Integer> result = builder.consumeLetters(word, availableLetters);

        // Then
        assertEquals(1, result.get('a')); // 3 - 2 = 1
        assertFalse(result.containsKey('b'));
        assertFalse(result.containsKey('c')); // 1 - 1 = 0 (removed)
        assertEquals(1, result.get('d')); // unchanged
    }

    @Test
    @DisplayName("Given valid 3x3 word square scenario, when building square, then return correct solution")
    void givenValid3x3WordSquareScenario_whenBuildingSquare_thenReturnCorrectSolution() {
        // Given
        validWords = Set.of("cat", "are", "tea", "art", "ear", "rat", "tar");
        builder = new WordSquareBuilder(3, validWords);
        Map<Character, Integer> letters = new HashMap<>();
        letters.put('c', 2);
        letters.put('a', 3);
        letters.put('t', 2);
        letters.put('r', 2);
        letters.put('e', 3);

        // When
        Optional<List<String>> result = builder.build(letters);

        // Then
        assertTrue(result.isPresent());
        List<String> square = result.get();
        assertEquals(3, square.size());

        // Verify word square property
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                assertEquals(square.get(i).charAt(j), square.get(j).charAt(i),
                        String.format("Position [%d][%d] should match [%d][%d]", i, j, j, i));
            }
        }
    }

    @Test
    @DisplayName("Given square maintains property, when checking maintainsSquareProperty, then return true")
    void givenSquareMaintainsProperty_whenCheckingMaintainsSquareProperty_thenReturnTrue() {
        // Given
        validWords = Set.of("to", "on", "no", "so", "at", "he");
        builder = new WordSquareBuilder(2, validWords);
        List<String> currentSquare = Collections.singletonList("to");
        String candidateWord = "on";
        int row = 1;

        // When
        boolean result = builder.maintainsSquareProperty(2, currentSquare, candidateWord, row);

        // Then
        assertTrue(result);
    }



    @Test
    @DisplayName("Given multiple possible solutions, when building square, then return first valid solution")
    void givenMultiplePossibleSolutions_whenBuildingSquare_thenReturnFirstValidSolution() {
        // Given
        validWords = Set.of("ab", "ba", "aa", "bb");
        builder = new WordSquareBuilder(2, validWords);
        Map<Character, Integer> letters = Map.of('a', 2, 'b', 2);

        // When
        Optional<List<String>> result = builder.build(letters);

        // Then
        assertTrue(result.isPresent());
        List<String> square = result.get();
        assertEquals(2, square.size());

        // Verify it's a valid word square
        assertEquals(square.get(0).charAt(0), square.get(0).charAt(0));
        assertEquals(square.get(0).charAt(1), square.get(1).charAt(0));
        assertEquals(square.get(1).charAt(0), square.get(0).charAt(1));
        assertEquals(square.get(1).charAt(1), square.get(1).charAt(1));
    }

    @Test
    @DisplayName("Given no solution exists, when building square, then return empty optional")
    void givenNoSolutionExists_whenBuildingSquare_thenReturnEmptyOptional() {
        // Given
        validWords = Set.of("abc", "def", "ghi"); // No possible word square
        builder = new WordSquareBuilder(2, validWords);
        Map<Character, Integer> letters = Map.of('x', 4, 'y', 4, 'z', 4);

        // When
        Optional<List<String>> result = builder.build(letters);

        // Then
        assertFalse(result.isPresent());
    }
}