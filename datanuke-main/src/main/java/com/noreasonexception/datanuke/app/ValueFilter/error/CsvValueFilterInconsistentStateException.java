package com.noreasonexception.datanuke.app.ValueFilter.error;

public class CsvValueFilterInconsistentStateException extends CsvValueFilterException  {
    public CsvValueFilterInconsistentStateException() {
        super("Please call .buildFromFile() first");
    }
}
