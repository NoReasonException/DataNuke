package com.noreasonexception.loadable.childs;

import com.noreasonexception.datanuke.app.ValueFilter.AbstractValueFilter;
import com.noreasonexception.datanuke.app.threadRunner.ThreadRunnerTaskEventsDispacher;
import com.noreasonexception.loadable.base.HtmlParser;

import java.util.regex.Pattern;

public class A28_MarkitEconomics_IHSMarkitCipsUkManufacturingPMI_UK extends HtmlParser {
    public A28_MarkitEconomics_IHSMarkitCipsUkManufacturingPMI_UK(ThreadRunnerTaskEventsDispacher disp,
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
    protected Double onValueExtract(Object context) {
        return null;
    }
}
