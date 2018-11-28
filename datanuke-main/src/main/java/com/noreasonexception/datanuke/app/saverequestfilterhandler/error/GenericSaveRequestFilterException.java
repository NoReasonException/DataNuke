package com.noreasonexception.datanuke.app.saverequestfilterhandler.error;

public class GenericSaveRequestFilterException extends Exception {
    public GenericSaveRequestFilterException(String message, Throwable cause) {
        super(message, cause);
    }

    public GenericSaveRequestFilterException(String message) {
        super(message);
    }
}
