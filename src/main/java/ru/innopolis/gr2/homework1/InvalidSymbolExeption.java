package ru.innopolis.gr2.homework1;

/**
 * Created by eugene on 12.11.16.
 */
public class InvalidSymbolExeption extends Exception {

    public InvalidSymbolExeption() {
    }

    public InvalidSymbolExeption(String message) {
        super(message);
    }

    public InvalidSymbolExeption(String message, Throwable cause) {
        super(message, cause);
    }

    public InvalidSymbolExeption(Throwable cause) {
        super(cause);
    }

    public InvalidSymbolExeption(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
