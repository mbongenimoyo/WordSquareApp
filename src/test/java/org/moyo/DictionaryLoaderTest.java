package org.moyo;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class DictionaryLoaderTest {


    @Test
    public void givenFilePath_whenLoadFromTextFile_then() {

        DictionaryLoader loader = new DictionaryLoader();
        loader.loadFromTextFile("src/test/resources/TestWord.txt");

    }

    @Test
    public void givenEmptyFilePath_whenLoadFromTextFile_thenThrowException() {
        DictionaryLoader loader = new DictionaryLoader();
        assertThrows(RuntimeException.class, () -> loader.loadFromTextFile(""));
        assertThrows(RuntimeException.class, () -> loader.loadFromTextFile(null));
    }

    @Test
    public void givenValidFilePath_whenGettingDictionarySize_thenReturnCorrectResult() {
        DictionaryLoader loader = new DictionaryLoader();
        loader.loadFromTextFile("src/test/resources/TestWord.txt");
        assertEquals(3, loader.getWordSizeToDictionaryMap().size());
        assertEquals(5, loader.getDictionaryForSize(4).size());
        assertEquals(7, loader.getDictionaryForSize(7).size());
    }
}