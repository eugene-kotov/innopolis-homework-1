package ru.innopolis.gr2.homework1;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import static org.junit.Assert.*;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by eugene on 07.11.16.
 */

public class FrequencyDictionary {

    private static Logger logger = LoggerFactory.getLogger(FrequencyDictionary.class);
    private Map<String, Integer> wordDictionary = new ConcurrentHashMap<String, Integer>();

    /**
     * Добавление в коллекцию найденного слова, если слово существует
     * увеличивает значение на еденицу
     * @param token - передаваемое слово
     * @param monitor - объект монитор для синхронизации потоков
     */
    public void add(String token, Object monitor) {

        token = token.toLowerCase();

        synchronized (monitor) {
            if (!wordDictionary.containsKey(token)) {
                wordDictionary.put(token, 1);
            } else {
                int count = wordDictionary.get(token);
                wordDictionary.put(token, ++count);
            }
            monitor.notifyAll();
        }

    }

    @Test
    public void checkWordAddition() {
        add("word1", new Object());
        assertEquals(wordDictionary.size(), 1);
        assertNotEquals(wordDictionary.size(), 0);
        add("WORD1", new Object());
        assertEquals(wordDictionary.size(), 1);
    }



    /**
     * @return Возвращает коллекцию слов
     */
    public Map<String, Integer> getWordDictionary() {
        return wordDictionary;
    }

}
