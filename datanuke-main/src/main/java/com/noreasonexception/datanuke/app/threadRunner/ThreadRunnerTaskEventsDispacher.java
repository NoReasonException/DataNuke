package com.noreasonexception.datanuke.app.threadRunner;

import com.noreasonexception.datanuke.app.threadRunner.etc.ClassInfo;
import com.noreasonexception.datanuke.app.threadRunner.etc.TaskEvent;
import com.noreasonexception.datanuke.app.threadRunner.etc.TaskEventException;

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
    public void submitClassReadInfoEvent(String classname, ClassInfo info){
        while(!events.offer(new TaskEvent("onClassReadInfo",classname,new Object[]{info})));
    }

    /***
     * Called when the AbstractThreadRunner waits until the deadline of class
     */
    public void submitClassWaitUntillDeadlineEvent(String classname,Long deadline){
        while(!events.offer(new TaskEvent("onClassWaitUntillDeadline",
                                                        classname,
                                                        new Object[]{deadline})));

    }

    /***
     * Called when the classloader is loading the class in memory
     */
    public void submitClassLoadingEvent(String classname){
        while(!events.offer(new TaskEvent("onClassLoading",classname)));

    }
    /***
     * Called if the classloader encounters some error in the proccess of loading some class into memory
     */
    public void submitClassLoadingEventFailed(String classname,Throwable e){
        while(!events.offer(new TaskEventException("onClassLoadingFailed",classname,e)));

    }

    /***
     * Called when the class instance is created
     */
    public void submitClassInstanceCreatedEvent(String classname){
        while(!events.offer(new TaskEvent("onClassInstanceCreated",classname)));

    }

    /***
     * Called when the class instance creation encounters some error
     */
    public void submitClassInstanceCreatedEventFailed(String classname,Throwable e){
        while(!events.offer(new TaskEventException("onClassInstanceCreatedFailed",classname,e)));

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
    public void submitTaskThreadValueRetrievedEvent(String classname,Double newVal){
        while(!events.offer(new TaskEvent("onTaskThreadValueRetrieved",
                                                        classname,
                                                        new Object[]{newVal})));

    }
    /***
     * Called when the new value is not retrieved due to some error
     */
    public void submitTaskThreadValueRetrievedEventFailed(String classname,Throwable e){
        while(!events.offer(new TaskEventException("onTaskThreadValueRetrievedFailed",classname,e)));

    }

    /***
     * Called when the task is terminated
     */
    public void submitTaskThreadTerminatedEvent(String classname){
        while(!events.offer(new TaskEvent("onTaskThreadTerminated",classname)));

    }

    /***
     * Called when the garbage collector releases the thread object
     */
    public void submitTaskThreadReleasedEvent(String classname){
        while(!events.offer(new TaskEvent("onTaskThreadReleased",classname)));

    }

    /***
     * Called when the garbage collector releases the class object
     */
    public void submitClassReleasedEvent(String classname){
        while(!events.offer(new TaskEvent("onClassReleased",classname)));

    }
    @Override
    public void run() {
        Class<ThreadRunnerTaskListener>klass=ThreadRunnerTaskListener.class;
        Method m=null;
        TaskEvent event;
        TaskEventException eventException;
        while(true){
            synchronized (this){
                synchronized (listeners){
                    try{
                        event=events.take();
                        java.lang.String methodname=event.getMethodName();
                        if(!methodname.endsWith("Failed")){
                            m=klass.getMethod(methodname,java.lang.String.class,java.lang.Object[].class);
                            for (ThreadRunnerTaskListener subsciber:listeners){
                                m.invoke(subsciber,event.getClassname(),event.getCall_args());
                            }
                        }
                        else{
                            eventException=(TaskEventException)event;
                            m=klass.getMethod(methodname,java.lang.String.class,java.lang.Throwable.class,java.lang.Object[].class);
                            for (ThreadRunnerTaskListener subsciber:listeners){
                                m.invoke(subsciber,eventException.getClassname(),eventException.getError(),event.getCall_args());
                            }
                        }
                    }
                    catch (NoSuchMethodException|
                            IllegalAccessException|
                            InvocationTargetException|
                            InterruptedException e){
                        e.printStackTrace();

                    }

                }
            }
        }
    }
}
