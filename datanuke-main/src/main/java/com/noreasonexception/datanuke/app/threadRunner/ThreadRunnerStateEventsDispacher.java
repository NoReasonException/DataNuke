package com.noreasonexception.datanuke.app.threadRunner;

import java.util.LinkedList;
import java.util.concurrent.LinkedBlockingQueue;

public class ThreadRunnerStateEventsDispacher extends Thread {
    private final LinkedList<ThreadRunnerStateListener> listeners;
    private boolean onSchedule=false;
    LinkedBlockingQueue<ThreadRunnerState> states;

    public ThreadRunnerStateEventsDispacher submitEvent(ThreadRunnerState state){
        while(!states.offer(state)){
            System.out.println("FAILED");
        }
        return this;
    }
    //TODO :ConcurrentModificationException but not deterministic , fix

    /****
     * Entry point of ThreadRunnerStateEventDispacher
     * This method basically waits for incoming events (via LinkedBlockingQueue )
     * And transmits the details in subscribers
     */
    @Override
    public void run() {
        while (true){
            synchronized (this){
                ThreadRunnerState state=null;
                try{
                    state= states.take();
                }catch (InterruptedException e){
                    System.out.println("ThreadRunnerStateEventsDispacher interrupted by user..");
                }

                synchronized (listeners){
                    for (ThreadRunnerStateListener l:listeners) {
                        l.setState(state).run();
                    }
                }
            }
        }
    }
    public ThreadRunnerStateEventsDispacher(LinkedList<ThreadRunnerStateListener> listeners) {
        this.listeners=listeners;
        this.states=new LinkedBlockingQueue<>();
    }
}
