package ru.innopolis.gr2.homework1;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Scanner;

/**
 * Created by eugene on 10.11.16.
 */
public class SourceParser extends Thread {

    private static Logger log = LoggerFactory.getLogger(SourceParser.class);
    String path;
    FrequencyDictionary fDictionary;
    Object monitor;


    public SourceParser(String path, FrequencyDictionary fDictionary) {
        this.path = path;
        this.fDictionary = fDictionary;
    }



    @Override
    public void run() {
        Scanner scanner = null;
        try {
            scanner = new Scanner(getSource(path));
        } catch (IOException e) {
            log.error("Unexpected error", e);
            //e.printStackTrace();
        }

        parse(scanner);
    }

    private void parse(Scanner scanner) {
        scanner.useDelimiter(Constants.DELIMS);
        while(scanner.hasNext()) {
            String token = scanner.next();
            if (isCorrect(token, Constants.CORRECT_SYMBOLS)) {
                fDictionary.add(token, monitor);
            } else {
                log.info("Incorrect symbol in {}", path);
                return;
            }
        }
    }

    /**
     *
     * @param token
     * @param pattern
     * @return
     */

    private boolean isCorrect(String token, String pattern) {
        if (token.matches(pattern)) {
            return true;
        } else return false;
    }

    private InputStream getSource(String source) throws IOException {

        URL url = null;
        try {
            url = new URL(source);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }

        return url.openStream();
    }
}
