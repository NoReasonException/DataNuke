package com.noreasonexception.datanuke.app.threadRunner;

public enum ThreadRunnerState {
    INITIALIZATION  ("ThreadRunner initialized successfully",0),
    LOAD_CONFIG     ("ThreadRunner initializes configurations...",1),
    LOAD_CONF_ERR   ("ThreadRunner initialization failed",-1),
    LOAD_CONF_SUCC  ("ThreadRunner initialization of configurations , okay",2),
    LOAD_SOURCES    ("ThreadRunner initializes the site's classes...",3),
    LOAD_SOURCES_ERR("ThreadRunner initialization of sources failed",-3),
    LOAD_CLASS_ERR  ("ThreadRunner : Class Not found event",4);

    private java.lang.String message=null;
    private int id=0;
    ThreadRunnerState(java.lang.String message,int id) {
        this.message=message;
        this.id=id;
    }

    public String getMessage() {
        return message;
    }

    public int getId() {
        return id;
    }
}
