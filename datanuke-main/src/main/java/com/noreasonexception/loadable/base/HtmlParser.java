package com.noreasonexception.loadable.base;

import com.noreasonexception.datanuke.app.ValueFilter.AbstractValueFilter;
import com.noreasonexception.datanuke.app.threadRunner.ThreadRunnerTaskEventsDispacher;

abstract public class HtmlParser extends PatternParser {
    public HtmlParser(ThreadRunnerTaskEventsDispacher disp, AbstractValueFilter<Double> valueFilter) {
        super(disp,valueFilter);
    }

    @Override
    protected String convertSourceToText() {
        return null;
    }
}
