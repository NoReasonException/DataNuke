package com.noreasonexception.loadable.base;

import com.noreasonexception.datanuke.app.ValueFilter.CsvValueFilter;
import com.noreasonexception.datanuke.app.classloader.AtlasLoader;
import com.noreasonexception.datanuke.app.threadRunner.ThreadRunnerTaskEventsDispacher;

public class AbstractParser implements Runnable{

    private ThreadRunnerTaskEventsDispacher dispacher;
    private CsvValueFilter valueFilter;
    public AbstractParser(ThreadRunnerTaskEventsDispacher disp, CsvValueFilter valueFilter)
    {
        this.dispacher=disp;
        this.valueFilter=valueFilter;
    }

    protected ThreadRunnerTaskEventsDispacher getDispacher() {
        return this.dispacher;
    }
    protected CsvValueFilter getValueFilter() {
        return this.valueFilter;
    }
    @Override
    public void run() {
        System.out.println("RUN COMPLETED");

    }
}
