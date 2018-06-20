package com.noreasonexception.datanuke.app.threadRunner.etc;

/****
 * This is an extension of classic TaskEvent . which carries an extra parameter . an Throwable .
 * this class used to describe failure events
 */
public class TaskEventException extends TaskEvent {
    private Throwable error;
    public TaskEventException(String methodName, String classname,Throwable e) {
        super(methodName, classname);
        this.error=e;
    }

    public Throwable getError() {
        return error;
    }
}
