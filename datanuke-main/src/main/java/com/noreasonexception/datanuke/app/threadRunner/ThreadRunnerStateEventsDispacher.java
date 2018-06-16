package com.noreasonexception.datanuke.app.threadRunner;

import java.util.LinkedList;
import java.util.concurrent.LinkedBlockingQueue;

public class ThreadRunnerStateEventsDispacher extends Thread {
    private final LinkedList<ThreadRunnerStateListener> listeners;
    private final AbstractThreadRunner runner;
    private boolean onSchedule=false;
    LinkedBlockingQueue<ThreadRunnerState> states;

    public ThreadRunnerStateEventsDispacher submitEvent(ThreadRunnerState state){
        while(!states.offer(state)){
            System.out.println("FAILED");
        }
        return this;
    }
    @Override
    public void run() {
        while (true){
            synchronized (this){
                synchronized (listeners){
                    ThreadRunnerState state=null;
                    try{
                        state= states.take();
                    }catch (InterruptedException e){e.printStackTrace();Thread.currentThread().interrupt();}
                    for (ThreadRunnerStateListener l:listeners) {
                        l.setState(state).run();
                    }
                }
            }
        }
    }
    public ThreadRunnerStateEventsDispacher(AbstractThreadRunner runner, LinkedList<ThreadRunnerStateListener> listeners) {
        this.runner=runner;
        this.listeners=listeners;
        this.states=new LinkedBlockingQueue<>();
    }
}
