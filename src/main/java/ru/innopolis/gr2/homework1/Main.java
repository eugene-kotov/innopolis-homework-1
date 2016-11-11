package ru.innopolis.gr2.homework1;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by eugene on 09.11.16.
 */
public class Main {

    private static Logger log = LoggerFactory.getLogger(Main.class);

    public static void main(String[] args) {

        log.info("Program started");

        FrequencyDictionary fd = new FrequencyDictionary();
        Object monitor = new Object();

        ReportBuilder report = new ReportBuilder(fd, monitor);
        report.start();

        //Create and run list of threads
        List<Thread> threads = new ArrayList<Thread>();
        for (int i = 0; i < args.length; i++) {
            Thread thread = new SourceParser(args[i],fd);
            threads.add(thread);
            thread.start();
        }

        //Waiting for threads closing
        for (Thread thread : threads) {
            try {
                thread.join();
            } catch (InterruptedException e) {
                log.error("Unexpected error", e);
                //e.printStackTrace();
            }

        }




    }

}
