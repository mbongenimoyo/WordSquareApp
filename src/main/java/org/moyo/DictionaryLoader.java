package org.moyo;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class DictionaryLoader {

    private Map<Integer, Set<String>> wordSizeToDictionaryMap;

    public DictionaryLoader() {
        this.wordSizeToDictionaryMap =  new HashMap<>();
    }


    public void loadFromTextFile(String filePath) {
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String word = line.trim().toLowerCase();
                if (!word.isEmpty()) {
                    int length = word.length();
                    wordSizeToDictionaryMap
                            .computeIfAbsent(length, k -> new HashSet<>())
                            .add(word);
                }
            }
        } catch (IOException e) {
            throw new RuntimeException("Failed to load dictionary from file: " + filePath, e);
        }
    }

    public Set<String> getDictionaryForSize (int size) {
        if(wordSizeToDictionaryMap == null || wordSizeToDictionaryMap.isEmpty() || !wordSizeToDictionaryMap.containsKey(size)) throw new RuntimeException("Alphabet does not exist for size");
        return wordSizeToDictionaryMap.get(size);
    }

    public Map<Integer, Set<String>> getWordSizeToDictionaryMap() {
        return wordSizeToDictionaryMap;
    }
}
