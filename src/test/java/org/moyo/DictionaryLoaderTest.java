package org.moyo;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Map;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class DictionaryLoaderTest {


    private DictionaryLoader loader;

    @BeforeEach
    void setUp() {
        loader = new DictionaryLoader();
    }

    @Test
    public void givenFilePath_whenLoadFromTextFile_then() {

        DictionaryLoader loader = new DictionaryLoader();
        loader.loadFromTextFile("src/test/resources/TestWord.txt");

    }

    @Test
    @DisplayName("Given empty file path, when loading dictionary, then throw RuntimeException")
    void givenEmptyFilePath_whenLoadFromTextFile_thenThrowRuntimeException() {
        // When & Then
        assertThrows(RuntimeException.class, () -> loader.loadFromTextFile(""));
    }

    @Test
    @DisplayName("Given null file path, when loading dictionary, then throw RuntimeException")
    void givenNullFilePath_whenLoadFromTextFile_thenThrowRuntimeException() {
        // When & Then
        assertThrows(RuntimeException.class, () -> loader.loadFromTextFile(null));
    }



    @Test
    @DisplayName("Given valid file path, when loading dictionary, then load words grouped by length")
    void givenValidFilePath_whenLoadFromTextFile_thenLoadWordsGroupedByLength() {
        // When
        loader.loadFromTextFile("src/test/resources/TestWord.txt");

        // Then
        Map<Integer, Set<String>> dictionaryMap = loader.getWordSizeToDictionaryMap();
        assertNotNull(dictionaryMap);
        assertFalse(dictionaryMap.isEmpty());

        // Verify specific word lengths are present
        assertTrue(dictionaryMap.containsKey(4));
        assertTrue(dictionaryMap.containsKey(7));

        // Verify word counts
        assertEquals(5, dictionaryMap.get(4).size());
        assertEquals(7, dictionaryMap.get(7).size());
    }


    @Test
    @DisplayName("Given non-existent file path, when loading dictionary, then throw RuntimeException")
    void givenNonExistentFilePath_whenLoadFromTextFile_thenThrowRuntimeException() {
        // When & Then
        assertThrows(RuntimeException.class, () -> loader.loadFromTextFile("/non/existent/file.txt"));
    }


    @Test
    @DisplayName("Given valid file path, when getting dictionary for size, then return correct word set")
    void givenValidFilePath_whenGettingDictionaryForSize_thenReturnCorrectWordSet() {
        // Given
        loader.loadFromTextFile("src/test/resources/TestWord.txt");

        // When
        Set<String> size4Words = loader.getDictionaryForSize(4);
        Set<String> size7Words = loader.getDictionaryForSize(7);

        // Then
        assertNotNull(size4Words);
        assertEquals(5, size4Words.size());
        assertTrue(size4Words.contains("moan"));
        assertTrue(size4Words.contains("once"));
        assertTrue(size4Words.contains("acme"));
        assertTrue(size4Words.contains("need"));
        assertTrue(size4Words.contains("boat"));

        assertNotNull(size7Words);
        assertEquals(7, size7Words.size());
        assertTrue(size7Words.contains("bravado"));
        assertTrue(size7Words.contains("renamed"));
    }

    @Test
    @DisplayName("Given non-existent word size, when getting dictionary, then throw RuntimeException")
    void givenNonExistentWordSize_whenGettingDictionary_thenThrowRuntimeException() {
        // Given
        loader.loadFromTextFile("src/test/resources/TestWord.txt");

        // When & Then
        assertThrows(RuntimeException.class, () -> loader.getDictionaryForSize(99));
    }


    @Test
    @DisplayName("Given empty dictionary, when getting dictionary for size, then throw RuntimeException")
    void givenEmptyDictionary_whenGettingDictionaryForSize_thenThrowRuntimeException() {
        // Given - loader is empty (not loaded)

        // When & Then
        assertThrows(RuntimeException.class, () -> loader.getDictionaryForSize(4));
    }

}