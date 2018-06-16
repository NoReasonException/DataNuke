package com.noreasonexception.datanuke.app.threadRunner;

import java.util.LinkedList;
import java.util.concurrent.LinkedBlockingQueue;

public class ThreadRunnerTaskEventsDispacher extends Thread {
    LinkedBlockingQueue<java.lang.String> events;
    private final LinkedList<ThreadRunnerTaskListener> listeners;

    public ThreadRunnerTaskEventsDispacher(LinkedList<ThreadRunnerTaskListener> tasks){
        this.listeners=tasks;
        events=new LinkedBlockingQueue<java.lang.String> ();
    }
    public void submitClassReadInfoEvent(){
        while(!events.offer("onClassReadInfo"));
    }

    /***
     * Called when the AbstractThreadRunner waits until the deadline of class
     */
    public void submitClassWaitUntillDeadlineEvent(){
        while(!events.offer("onClassWaitUntillDeadline"));

    }

    /***
     * Called when the classloader is loading the class in memory
     */
    public void submitClassLoadingEvent(){
        while(!events.offer("onClassLoadingEvent"));

    }

    /***
     * Called when the class instance is created
     */
    public void submitClassInstanceCreatedEvent(){
        while(!events.offer("onClassInstanceCreated"));

    }

    /***
     * Called when the thread is started
     */
    public void submitTaskThreadStartedEvent(){
        while(!events.offer("onTaskThreadStarted"));

    }

    /***
     * Called when the new value is retrieved
     */
    public void submitTaskThreadValueRetrievedEvent(){
        while(!events.offer("onTaskThreadValueRetrieved"));

    }

    /***
     * Called when the task is terminated
     */
    public void submitTaskThreadTerminatedEvent(){
        while(!events.offer("onTaskThreadTerminate"));

    }

    /***
     * Called when the garbage collector releases the thread object
     */
    public void submitTaskThreadReleasedEvent(){
        while(!events.offer("onTaskThreadReleased"));

    }

    /***
     * Called when the gerbage collector releases the class object
     */
    public void submitClassReleasedEvent(){
        while(!events.offer("onClassReleased"));

    }
    @Override
    public void run() {

    }

}
