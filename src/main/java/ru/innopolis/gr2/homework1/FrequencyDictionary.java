package ru.innopolis.gr2.homework1;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by eugene on 10.11.16.
 */

public class FrequencyDictionary {

    private Map<String, Integer> wordDictionary = new ConcurrentHashMap<String, Integer>();

    public void add(String token) {

        token = token.toLowerCase();

        if (!wordDictionary.containsKey(token)) {
            wordDictionary.put(token, 1);
        } else {
            int count = wordDictionary.get(token);
            wordDictionary.put(token, ++count);
        }

    }

    public Map<String, Integer> getWordDictionary() {
        return wordDictionary;
    }
}
