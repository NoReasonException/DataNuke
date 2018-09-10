package com.noreasonexception.datanuke.app.threadRunner;

abstract public class ThreadRunnerStateListener implements Runnable {
    private ThreadRunnerState state=null;
    abstract public void run() ;

    public synchronized ThreadRunnerState getState() {
        return state;
    }

    public synchronized ThreadRunnerStateListener setState(ThreadRunnerState state) {
        this.state = state;
        return this;
    }
}
