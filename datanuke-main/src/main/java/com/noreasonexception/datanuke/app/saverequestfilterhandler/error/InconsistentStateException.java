package com.noreasonexception.datanuke.app.saverequestfilterhandler.error;

public class InconsistentStateException extends GenericSaveRequestFilterException {
    public InconsistentStateException() {
        super("Please call .buildFromFile() first");
    }
    public InconsistentStateException(String extramsg) {
        super("Please call .buildFromFile() first("+extramsg+")");
    }
}
