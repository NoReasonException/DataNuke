package com.noreasonexception.datanuke.app.ValueFilter.error;

public class CsvValueFilterInconsistentStateException extends CsvValueFilterException  {
    public CsvValueFilterInconsistentStateException() {
        super("Please call .buildFromFile() first");
    }
    public CsvValueFilterInconsistentStateException(String extramsg) {
        super("Please call .buildFromFile() first("+extramsg+")");
    }
}
