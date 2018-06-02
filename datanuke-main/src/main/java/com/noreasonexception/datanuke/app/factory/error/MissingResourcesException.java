package com.noreasonexception.datanuke.app.factory.error;

public class MissingResourcesException extends IllegalStateException {
    public MissingResourcesException(Throwable throwable) {
        super(throwable);
    }
}
