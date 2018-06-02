package com.noreasonexception.datanuke.app.threadRunner;

public enum ThreadRunnerState {
    INITIALIZATION  ("ThreadRunner initialized successfully"),
    LOAD_CONFIG     ("ThreadRunner initializes configurations..."),
    LOAD_CONF_ERR   ("ThreadRunner initialization failed"),
    LOAD_SOURCES    ("ThreadRunner initializes the site's classes..."),
    LOAD_SOURCES_ERR("ThreadRunner initialization of sources failed"),
    LOAD_CLASS_ERR  ("ThreadRunner : Class Not found event");

    private java.lang.String message=null;
    ThreadRunnerState(java.lang.String message) {
        this.message=message;
    }

    public String getMessage() {
        return message;
    }
}
