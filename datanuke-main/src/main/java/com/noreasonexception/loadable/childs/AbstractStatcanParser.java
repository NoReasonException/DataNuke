package com.noreasonexception.loadable.childs;

import com.noreasonexception.datanuke.app.ValueFilter.AbstractValueFilter;
import com.noreasonexception.datanuke.app.threadRunner.ThreadRunnerTaskEventsDispacher;
import com.noreasonexception.loadable.base.HtmlParser;
import com.noreasonexception.loadable.base.error.InvalidSourceArchitectureException;

import java.util.regex.Pattern;

public class AbstractStatcanParser extends HtmlParser {
    public AbstractStatcanParser(ThreadRunnerTaskEventsDispacher disp, AbstractValueFilter<Double> valueFilter) {
        super(disp, valueFilter);
    }

    @Override
    protected Pattern onPatternLoad() {
        return null;
    }

    @Override
    protected String onUrlLoad() {
        return null;
    }

    @Override
    protected Double onValueExtract(Object context) throws InvalidSourceArchitectureException {
        return null;
    }
}
