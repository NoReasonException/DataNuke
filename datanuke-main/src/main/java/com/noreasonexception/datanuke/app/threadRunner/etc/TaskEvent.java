package com.noreasonexception.datanuke.app.threadRunner.etc;

/****
 * This object is submitted in Task Notifier dispacher as event . the dispacher looks the methodname and calls the right
 * handler on every listener
 */
public class TaskEvent {
    private String methodName;
    private String classname;

    public String getMethodName() {
        return methodName;
    }

    public void setMethodName(String methodName) {
        this.methodName = methodName;
    }

    public String getClassname() {
        return classname;
    }

    public void setClassname(String classname) {
        this.classname = classname;
    }

    public TaskEvent(String methodName, String classname) {
        this.methodName = methodName;
        this.classname = classname;
    }
}
