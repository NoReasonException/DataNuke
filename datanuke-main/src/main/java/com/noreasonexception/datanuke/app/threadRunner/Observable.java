package com.noreasonexception.datanuke.app.threadRunner;

public interface Observable {
    public boolean subscribeListener(ThreadRunnerListener listener);
}
