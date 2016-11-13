package ru.innopolis.gr2.homework1;

import org.junit.Ignore;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.net.URL;
import java.net.URLConnection;
import java.util.Scanner;
import static org.junit.Assert.*;

/**
 * Created by eugene on 07.11.16.
 */
public class SourceParser extends Thread {

    private static Logger log = LoggerFactory.getLogger(SourceParser.class);
    String path;
    FrequencyDictionary fDictionary;
    Object monitor;


    public SourceParser(String path, FrequencyDictionary fDictionary, Object monitor) {
        this.path = path;
        this.fDictionary = fDictionary;
        this.monitor = monitor;
    }

    @Override
    public void run() {
        Scanner scanner = null;
        try {
            scanner = new Scanner(getSource(path));
        } catch (IOException e) {
            log.error("Unexpected error", e);
        }
// TODO: 11.11.16 add try with resources
        try {
            parse(scanner);
        } catch (InterruptedException e) {
            log.error("Unexpected exeption ", e);
        }
    }


    /**
     * Ищет слова в ресурсе
     * @param scanner
     */
    private void parse(Scanner scanner) throws InterruptedException {
        scanner.useDelimiter(Constants.DELIMS);
        while(scanner.hasNext()) {

            String token = scanner.next();

            if (!token.isEmpty()) {
                try {
                    if (verify(token, Constants.CORRECT_SYMBOLS)) {
                        fDictionary.add(token, monitor);
                    }
                } catch (InvalidSymbolExeption invalidSymbolExeption) {
                    invalidSymbolExeption.printStackTrace();
                    log.error("Found invalid symbol" , invalidSymbolExeption);
                    throw new InterruptedException();
                }
            }

        }
    }

    /**
     * Проверяет слово на соответствие передаваемому паттерну в случае
     * несоответствия выбрасывает InvalidSymbolExeption
     * @param token - слово
     * @param pattern - regex паттерн
     * @return
     */

    private static boolean verify(String token, String pattern) throws InvalidSymbolExeption {
        if (token.matches(pattern)) {
            return true;
        } else {
            throw new InvalidSymbolExeption("invalid symbol in string: " + token);
            //return false;
        }
    }

//    @Ignore
//    @Test(expected = InvalidSymbolExeption.class) {
//        public void testInvalidSymbolExeption() {
//            verify("qwerty", Constants.CORRECT_SYMBOLS);
//        }
//    }

//    @Ignore
//    @Test
//    public void checkVerify() throws InvalidSymbolExeption {
//        assertTrue(verify("слово", Constants.CORRECT_SYMBOLS));
//        assertFalse(verify("word", Constants.CORRECT_SYMBOLS));
//    }


    /**
     * Строит исходящий поток на основе передаваемого источника,
     * источник может быть как ссылкой на внешний ресурс (http, ftp, file),
     * так и файлом в файловой системе, либо потоком байт
     * @param source - Путь к ресурсу
     * @return
     * @throws IOException
     */

    private InputStream getSource(String source) throws IOException {
// TODO: 13.11.16 Try to use factory

        if (source.startsWith("http:") || source.startsWith("ftp:") ||
                source.startsWith("file:")) {
            URL url = new URL(source);
            URLConnection conn = url.openConnection();

            return url.openStream();
            // TODO: 11.11.16 use try with resources
        } else if (new File(source).isFile()) {

            return new FileInputStream(source);

        } else {

            return new ByteArrayInputStream(source.getBytes());

        }

    }

}
