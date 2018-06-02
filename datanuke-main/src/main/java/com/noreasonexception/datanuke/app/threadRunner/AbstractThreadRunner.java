package com.noreasonexception.datanuke.app.threadRunner;

import com.noreasonexception.datanuke.app.dataProvider.DataProvider;

import java.nio.Buffer;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedList;

public class AbstractThreadRunner implements Runnable , Observable {
    private ThreadRunnerState currentState = null;
    private ClassLoader classLoader = null;
    private DataProvider configBuffer = null;
    private DataProvider sourceBuffer = null;
    private HashMap<Date, String> classSources = null;
    private LinkedList<ThreadRunnerListener> listeners = null;

    private void eventHappened() {
        for (ThreadRunnerListener listener : this.listeners) {
            new Thread(listener.setState(currentState)).start();

        }
    }

    /****
     * This is the main entry point for ThreadRunner
     *
     */
    public void run() {

    }

    public boolean subscribeListener(ThreadRunnerListener listener) {
        return this.listeners.add(listener);
    }

    public AbstractThreadRunner() {
        this.listeners = new LinkedList<>();

    }
}
