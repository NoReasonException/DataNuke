package com.noreasonexception.datanuke.app.ValueFilter.error;

public class CsvValueFilterException extends Exception {
    public CsvValueFilterException(String message, Throwable cause) {
        super(message, cause);
    }

    public CsvValueFilterException(String message) {
        super(message);
    }
}
