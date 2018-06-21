package com.noreasonexception.loadable.childs;

import com.noreasonexception.datanuke.app.ValueFilter.CsvValueFilter;
import com.noreasonexception.datanuke.app.threadRunner.ThreadRunnerTaskEventsDispacher;
import com.noreasonexception.loadable.base.HtmlParser;

import javax.management.RuntimeErrorException;
import java.util.Random;

public class TestClass extends HtmlParser {
    public TestClass(ThreadRunnerTaskEventsDispacher disp, CsvValueFilter valueFilter) {
        super(disp,valueFilter);
    }

    @Override
    protected void finalize() throws Throwable {
        getDispacher().submitTaskThreadTerminatedEvent(getClass().getName());
    }

    @Override
    //TODO : For some reason , the .submitValue enforces this object to remain in memory FIX
    public void run() {
        try{
            Random r=new Random();
            getValueFilter().submitValue(new String(getClass().getName()),r.nextDouble());
            System.out.println("OK");

        }catch (Exception e){throw new Error();
        }
    }
}
