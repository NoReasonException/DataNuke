package com.noreasonexception.loadable.base;

import com.noreasonexception.datanuke.app.SaveRequestFilterHandler.SaveRequestFilterHandler;
import com.noreasonexception.datanuke.app.threadRunner.ThreadRunnerTaskEventsDispacher;

abstract public class HtmlParser extends PatternParser {
    public HtmlParser(ThreadRunnerTaskEventsDispacher disp, SaveRequestFilterHandler<Double> valueFilter) {
        super(disp,valueFilter);
    }
}
