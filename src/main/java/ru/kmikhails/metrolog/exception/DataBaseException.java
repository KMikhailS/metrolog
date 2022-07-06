package ru.kmikhails.metrolog.exception;

public class DataBaseException extends RuntimeException {

    public DataBaseException() {
        super();
    }

    public DataBaseException(String message) {
        super(message);
    }

    public DataBaseException(Exception cause) {
        super(cause);
    }

    public DataBaseException(String message, Exception cause) {
        super(message, cause);
    }
}
