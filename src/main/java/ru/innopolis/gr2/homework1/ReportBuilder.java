package ru.innopolis.gr2.homework1;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;

/**
 * Created by eugene on 11.11.16.
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
                    //e.printStackTrace();
                }
            }
        }
    }

    public void printResult(){

        for (Map.Entry entry : frequencyDictionary.getWordDictionary().entrySet()) {
            System.out.format("%s : %s \n", entry.getKey(), entry.getValue().toString());
        }

    }
}
