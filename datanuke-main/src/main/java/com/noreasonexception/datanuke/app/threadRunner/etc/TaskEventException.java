package com.noreasonexception.datanuke.app.threadRunner.etc;

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
