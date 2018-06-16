package com.noreasonexception.datanuke.app.threadRunner;

import com.noreasonexception.datanuke.app.threadRunner.etc.TaskEvent;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.LinkedList;
import java.util.concurrent.LinkedBlockingQueue;

public class ThreadRunnerTaskEventsDispacher extends Thread {
    LinkedBlockingQueue<TaskEvent> events;
    private final LinkedList<ThreadRunnerTaskListener> listeners;

    public ThreadRunnerTaskEventsDispacher(LinkedList<ThreadRunnerTaskListener> tasks){
        this.listeners=tasks;
        events=new LinkedBlockingQueue<TaskEvent> ();
    }
    public void submitClassReadInfoEvent(String classname){
        while(!events.offer(new TaskEvent("onClassReadInfo",classname)));
    }

    /***
     * Called when the AbstractThreadRunner waits until the deadline of class
     */
    public void submitClassWaitUntillDeadlineEvent(String classname){
        while(!events.offer(new TaskEvent("onClassWaitUntillDeadline",classname)));

    }

    /***
     * Called when the classloader is loading the class in memory
     */
    public void submitClassLoadingEvent(String classname){
        while(!events.offer(new TaskEvent("onClassLoadingEvent",classname)));

    }

    /***
     * Called when the class instance is created
     */
    public void submitClassInstanceCreatedEvent(String classname){
        while(!events.offer(new TaskEvent("onClassInstanceCreated",classname)));

    }

    /***
     * Called when the thread is started
     */
    public void submitTaskThreadStartedEvent(String classname){
        while(!events.offer(new TaskEvent("onTaskThreadStarted",classname)));

    }

    /***
     * Called when the new value is retrieved
     */
    public void submitTaskThreadValueRetrievedEvent(String classname){
        while(!events.offer(new TaskEvent("onTaskThreadValueRetrieved",classname)));

    }

    /***
     * Called when the task is terminated
     */
    public void submitTaskThreadTerminatedEvent(String classname){
        while(!events.offer(new TaskEvent("onTaskThreadTerminate",classname)));

    }

    /***
     * Called when the garbage collector releases the thread object
     */
    public void submitTaskThreadReleasedEvent(String classname){
        while(!events.offer(new TaskEvent("onTaskThreadReleased",classname)));

    }

    /***
     * Called when the gerbage collector releases the class object
     */
    public void submitClassReleasedEvent(String classname){
        while(!events.offer(new TaskEvent("onClassReleased",classname)));

    }
    @Override
    public void run() {
        Class<ThreadRunnerTaskListener>klass=ThreadRunnerTaskListener.class;
        Method m=null;
        TaskEvent ev;
        while(true){
            synchronized (this){
                synchronized (listeners){
                    try{
                        ev=events.take();
                        java.lang.String methodname=ev.getMethodName();
                        System.out.println(methodname);
                        m=klass.getMethod(methodname,java.lang.String.class);
                        for (ThreadRunnerTaskListener subsciber:listeners){
                            m.invoke(subsciber,ev.getClassname());
                        }
                    }
                    catch (InterruptedException e){e.printStackTrace();}
                    catch (NoSuchMethodException|IllegalAccessException|InvocationTargetException e){e.printStackTrace();}

                }
            }
        }
    }

}
