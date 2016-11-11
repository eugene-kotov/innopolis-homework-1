package ru.innopolis.gr2.homework1;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by eugene on 10.11.16.
 */

public class FrequencyDictionary {

    private static Logger logger = LoggerFactory.getLogger(FrequencyDictionary.class);
    private Map<String, Integer> wordDictionary = new ConcurrentHashMap<String, Integer>();

    public void add(String token, Object monitor) {

        token = token.toLowerCase();

        synchronized (monitor) {
            if (!wordDictionary.containsKey(token)) {
                wordDictionary.put(token, 1);
            } else {
                int count = wordDictionary.get(token);
                wordDictionary.put(token, ++count);
            }
            notifyAll();
        }

    }

    public Map<String, Integer> getWordDictionary() {
        return wordDictionary;
    }
}
