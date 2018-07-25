package com.noreasonexception.datanuke.app.threadRunner;

public abstract class ThreadRunnerTaskListener  {
    /***
     * Called when a class info read from config file
     */
    public void onClassReadInfo(String classname,Object[]args){}

    /***
     * Called when the AbstractThreadRunner waits until the deadline of class
     */
    public void onClassWaitUntillDeadline(String classname,Object[]args){}

    /***
     * Called when the classloader is loading the class in memory
     */
    public void onClassLoading(String classname,Object[]args){}

    /****
     * Called when something went wrong in class loading stuff..
     */
    public void onClassLoadingFailed(String classname,Throwable e,Object[]args){}

    /***
     * Called when the class instance is created
     */
    public void onClassInstanceCreated(String classname,Object[]args){}

    /****
     * Called when something gone wrong about creating instance
     *
     */
    public void onClassInstanceCreatedFailed(String classname,Throwable e,Object[]args){}

    /***
     * Called when the thread is started
     */
    public void onTaskThreadStarted(String classname,Object[]args){}

    /***
     * Called when the new value is retrieved
     */
    public void onTaskThreadValueRetrieved(String classname,Object[]args){}

    /****
     * Called when something gone wrong about retrieving some value
     */
    public void onTaskThreadValueRetrievedFailed(String classname,Throwable e,Object[]args){}

    /***
     * Called when the task is terminated
     */
    public void onTaskThreadTerminated(String classname,Object[]args){}

    /***
     * Called when the garbage collector releases the thread object
     */
    public void onTaskThreadReleased(String classname,Object[]args){}

    /***
     * Called when the gerbage collector releases the class object
     */
    public void onClassReleased(String classname,Object[]args){}
}
