package ru.innopolis.gr2.homework1;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Arrays;
import java.util.Map;

/**
 * Created by eugene on 07.11.16.
 */
public class ReportBuilder extends Thread {

    private static Logger log = LoggerFactory.getLogger(ReportBuilder.class);
    private FrequencyDictionary frequencyDictionary;
    static Object monitor;


    public ReportBuilder(FrequencyDictionary frequencyDictionary, Object monitor) {
        this.frequencyDictionary = frequencyDictionary;
        this.monitor = monitor;
        setDaemon(true);
    }

    @Override
    public void run() {
        while (!isInterrupted()){
            synchronized (monitor) {
                try {
                    monitor.wait();
                    printResult();
                } catch (InterruptedException e) {
                    log.error("Unexpected error", e);
                }
            }
        }
    }

    /**
     * Выводит отчёт по найденным словам
     */
    public void printResult(){

        log.info(Arrays.asList(frequencyDictionary.getWordDictionary()).toString());

    }
}
