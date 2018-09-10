package com.noreasonexception.loadable.childs;

import com.noreasonexception.datanuke.app.ValueFilter.AbstractValueFilter;
import com.noreasonexception.datanuke.app.threadRunner.ThreadRunnerTaskEventsDispacher;
import com.noreasonexception.loadable.base.HtmlParser;

import java.util.regex.Pattern;

public class A38_FederalReserveGov_IndustrialProduction_US extends HtmlParser {
    public A38_FederalReserveGov_IndustrialProduction_US(ThreadRunnerTaskEventsDispacher disp,
                                                         AbstractValueFilter<Double> valueFilter) {
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
    protected Double onValueExtract(Object tmpString) {
        return null;
    }
}
