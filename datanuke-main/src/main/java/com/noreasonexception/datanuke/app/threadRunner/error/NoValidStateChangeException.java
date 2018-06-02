package com.noreasonexception.datanuke.app.threadRunner.error;

import com.noreasonexception.datanuke.app.threadRunner.ThreadRunnerState;

import java.security.InvalidParameterException;

public class NoValidStateChangeException extends InvalidParameterException {
    private ThreadRunnerState oldState;
    private ThreadRunnerState newState;
    public NoValidStateChangeException(String s, ThreadRunnerState oldState, ThreadRunnerState newState) {
        super(s);
        this.oldState = oldState;
        this.newState = newState;
    }

    public ThreadRunnerState getOldState() {
        return oldState;
    }

    public ThreadRunnerState getNewState() {
        return newState;
    }
}
