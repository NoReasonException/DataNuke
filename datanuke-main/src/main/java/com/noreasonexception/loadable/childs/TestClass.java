package com.noreasonexception.loadable.childs;

import com.noreasonexception.datanuke.app.ValueFilter.CsvValueFilter;
import com.noreasonexception.datanuke.app.threadRunner.ThreadRunnerTaskEventsDispacher;
import com.noreasonexception.loadable.base.HtmlParser;

public class TestClass extends HtmlParser {
    public TestClass(ThreadRunnerTaskEventsDispacher disp, CsvValueFilter valueFilter) {
        super(disp,valueFilter);
    }

    @Override
    protected void finalize() throws Throwable {
        getDispacher().submitTaskThreadTerminatedEvent(getClass().getName());
    }

    @Override
    public void run() {
        super.run();
    }
}
