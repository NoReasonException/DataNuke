package com.noreasonexception.loadable.base;

import com.noreasonexception.datanuke.app.ValueFilter.CsvValueFilter;
import com.noreasonexception.datanuke.app.threadRunner.ThreadRunnerTaskEventsDispacher;

public class HtmlParser extends AbstractParser{
    public HtmlParser(ThreadRunnerTaskEventsDispacher disp, CsvValueFilter valueFilter) {
        super(disp,valueFilter);
    }
}
