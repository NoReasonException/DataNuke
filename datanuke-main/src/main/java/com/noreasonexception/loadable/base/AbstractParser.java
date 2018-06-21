package com.noreasonexception.loadable.base;

import com.noreasonexception.datanuke.app.ValueFilter.CsvValueFilter;
import com.noreasonexception.datanuke.app.classloader.AtlasLoader;
import com.noreasonexception.datanuke.app.threadRunner.ThreadRunnerTaskEventsDispacher;

/***
 * AbstractParser
 * this class represents an abstract parser
 * And what is an parser?
 * A parser is a subsystem who its mission is..
 *
 * 1) Take arbitrary data via a DataProvider in a known interval
 * 2) Retrieves specific information inside these data
 * 3) Informs the ValueFilter
 * 4) When a new value is discovered , the ValueFilter.submitValue() returns true .
 * 5) When the @4 happens , the Parser must kill himself
 * @implNote In some steps , the ThreadRunnerTaskEventsDispacher must informed by corresponding methods
 */
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
