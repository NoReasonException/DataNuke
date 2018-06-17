package com.noreasonexception.loadable.childs;

import com.noreasonexception.datanuke.app.threadRunner.ThreadRunnerTaskEventsDispacher;
import com.noreasonexception.loadable.base.HtmlParser;

public class TestClass extends HtmlParser {
    public TestClass(ThreadRunnerTaskEventsDispacher disp) {
        super(disp);
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
