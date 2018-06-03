package com.noreasonexception.datanuke.app.threadRunner;

public enum ThreadRunnerState {
    NONE            ("ThreadRunner is in NONE mode(nothing happened yet)",0),
    INITIALIZATION  ("ThreadRunner initialize started...",1),
    LOAD_CONF       ("ThreadRunner initializes configurations...",2),
    LOAD_CONF_ERR   ("ThreadRunner initialization failed",-2),
    LOAD_CONF_SUCC  ("ThreadRunner initialization of configurations , okay",3),
    LOAD_SOURCES    ("ThreadRunner initializes the site's classes...",4),
    LOAD_SOURCES_ERR("ThreadRunner initialization of sources failed",-4),
    LOAD_CLASS_ERR  ("ThreadRunner : Class Not found event",5);

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
