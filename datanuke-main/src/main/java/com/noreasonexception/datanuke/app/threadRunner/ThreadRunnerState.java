package com.noreasonexception.datanuke.app.threadRunner;

public enum ThreadRunnerState {
    NONE            ("ThreadRunner is in NONE mode(nothing happened yet)",0),
    INITIALIZATION  ("ThreadRunner initialize started...",1),
    LOAD_CONF       ("ThreadRunner initializes configurations...",2),
    LOAD_CONF_ERR   ("ThreadRunner initialization failed",-2),
    LOAD_CONF_SUCC  ("ThreadRunner initialization of configurations , okay",3),
    LOAD_SRC    ("ThreadRunner initializes the site's classes...",4),
    LOAD_SRC_ERR("ThreadRunner initialization of sources failed",-4),
    LOAD_SRC_SUCC("ThreadRunner initialization of sources okay",5),
    PREPARE_LOOP    ("ThreadRunner initializes the site's classes...",6),
    PREPARE_LOOP_SUCC("ThreadRunner initialization of sources failed",-6),
    PREPARE_LOOP_ERR("ThreadRunner initialization of sources okay",7),

    LOAD_CLASS_ERR  ("ThreadRunner : Class Not found event",8);

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
