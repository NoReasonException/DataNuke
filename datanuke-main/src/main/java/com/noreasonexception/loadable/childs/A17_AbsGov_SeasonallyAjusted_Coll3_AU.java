package com.noreasonexception.loadable.childs;

import com.noreasonexception.datanuke.app.ValueFilter.AbstractValueFilter;
import com.noreasonexception.datanuke.app.threadRunner.ThreadRunnerTaskEventsDispacher;
import com.noreasonexception.loadable.base.HtmlParser;
import com.noreasonexception.loadable.base.error.InvalidSourceArchitectureException;

import java.util.regex.Pattern;

public class A17_AbsGov_SeasonallyAjusted_Coll3_AU extends HtmlParser {
    public A17_AbsGov_SeasonallyAjusted_Coll3_AU(ThreadRunnerTaskEventsDispacher disp,
                                                 AbstractValueFilter<Double> valueFilter) {
        super(disp, valueFilter);
    }
    protected Pattern onPatternLoad(){
        return null;

    }
    protected String    onUrlLoad(){
        return null;
    }
    protected Double    onValueExtract(Object context) throws InvalidSourceArchitectureException {
        return null;
    }
}
