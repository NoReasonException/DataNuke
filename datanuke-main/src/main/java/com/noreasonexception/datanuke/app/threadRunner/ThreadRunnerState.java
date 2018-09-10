package com.noreasonexception.datanuke.app.threadRunner;

public enum ThreadRunnerState implements Cloneable {
    NONE                ("threadRunner is in NONE mode(nothing happened yet)",0),
    INITIALIZATION      ("threadRunner initialize started...",1),
    LOAD_CONF           ("threadRunner initializes configurations...",2),
    LOAD_CONF_ERR       ("threadRunner initialization failed",-2),
    LOAD_CONF_SUCC      ("threadRunner initialization of configurations , okay",3),
    LOAD_SRC            ("threadRunner initializes the site's classes...",4),
    LOAD_SRC_ERR        ("threadRunner initialization of sources failed",-4),
    LOAD_SRC_SUCC       ("threadRunner initialization of sources okay",5),
    PREPARE_LOOP        ("threadRunner initializes the main loop",6),
    PREPARE_LOOP_SUCC   ("threadRunner initialization of loop Succeded",-6),
    PREPARE_LOOP_ERR    ("threadRunner initialization of sources okay",7),
    LOAD_CLASS_ERR      ("threadRunner : Class Not found event",8);

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
