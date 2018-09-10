package com.noreasonexception.loadable.base.error;

public class InvalidSourceArchitectureException extends Exception {
    public InvalidSourceArchitectureException(Class sourceThrown) {
        super("Class "+sourceThrown.getName()+" Had detected that theirs website has changed!\n" +
                "Please contact with your developer :) ");
    }
}
