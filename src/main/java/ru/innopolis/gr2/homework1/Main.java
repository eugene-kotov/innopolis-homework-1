package ru.innopolis.gr2.homework1;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by eugene on 05.11.16.
 * Вариант 1

 * Необходимо разработать программу, которая получает на вход список ресурсов, содержащих текст,
 * и считает общее количество вхождений (для всех ресурсов) каждого слова. Каждый ресурс должен
 * быть обработан в отдельном потоке, текст не должен содержать инностранных символов, только
 * кириллица, знаки препинания и цифры. Отчет должен строиться в режиме реального времени,
 * знаки препинания и цифры в отчет не входят. Все ошибки должны быть корректно обработаны,
 * все API покрыто модульными тестами
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
            Thread thread = new SourceParser(args[i],fd, monitor);
            threads.add(thread);
            thread.start();
        }



        //Waiting for threads closing
        for (Thread thread : threads) {
            try {
                thread.join();
            } catch (InterruptedException e) {
                log.error("Unexpected error", e);
            }

        }


    }

}
