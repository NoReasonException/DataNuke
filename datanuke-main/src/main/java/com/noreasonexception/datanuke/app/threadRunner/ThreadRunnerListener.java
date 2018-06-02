package com.noreasonexception.datanuke.app.threadRunner;

abstract public class ThreadRunnerListener implements Runnable {
    private ThreadRunnerState state=null;
    abstract public void run() ;

    public ThreadRunnerState getState() {
        return state;
    }

    public ThreadRunnerListener setState(ThreadRunnerState state) {
        this.state = state;
        return this;
    }
}
