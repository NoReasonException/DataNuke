package com.noreasonexception.loadable.base;

import com.noreasonexception.datanuke.app.classloader.AtlasLoader;
import com.noreasonexception.datanuke.app.threadRunner.ThreadRunnerTaskEventsDispacher;

public class AbstractParser implements Runnable{

    private ThreadRunnerTaskEventsDispacher dispacher;
    public AbstractParser(ThreadRunnerTaskEventsDispacher disp){
        this.dispacher=disp;
    }

    protected ThreadRunnerTaskEventsDispacher getDispacher() {
        return dispacher;
    }
    @Override
    public void run() {
        System.out.println("RUN COMPLETED");

    }
}
