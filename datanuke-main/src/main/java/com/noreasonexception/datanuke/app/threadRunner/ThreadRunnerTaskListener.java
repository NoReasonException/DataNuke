package com.noreasonexception.datanuke.app.threadRunner;

public abstract class ThreadRunnerTaskListener  {
    /***
     * Called when a class info read from config file
     */
    public void onClassReadInfo(){}

    /***
     * Called when the AbstractThreadRunner waits until the deadline of class
     */
    public void onClassWaitUntillDeadline(){}

    /***
     * Called when the classloader is loading the class in memory
     */
    public void onClassLoading(){}

    /***
     * Called when the class instance is created
     */
    public void onClassInstanceCreated(){}

    /***
     * Called when the thread is started
     */
    public void onTaskThreadStarted(){}

    /***
     * Called when the new value is retrieved
     */
    public void onTaskThreadValueRetrieved(){}

    /***
     * Called when the task is terminated
     */
    public void onTaskThreadTerminated(){}

    /***
     * Called when the garbage collector releases the thread object
     */
    public void onTaskThreadReleased(){}

    /***
     * Called when the gerbage collector releases the class object
     */
    public void onClassReleased(){}
}
