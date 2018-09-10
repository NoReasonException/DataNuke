package com.noreasonexception.loadable.childs;

import com.noreasonexception.datanuke.app.ValueFilter.AbstractValueFilter;
import com.noreasonexception.datanuke.app.threadRunner.ThreadRunnerTaskEventsDispacher;
import com.noreasonexception.loadable.base.PdfParser;
import com.noreasonexception.loadable.base.error.InvalidSourceArchitectureException;

import java.util.regex.Pattern;

public class A10_Census_AdvanceMonthlySales_US extends PdfParser {
    public A10_Census_AdvanceMonthlySales_US(ThreadRunnerTaskEventsDispacher disp,
                                             AbstractValueFilter<Double> valueFilter) {
        super(disp, valueFilter);
    }

    protected Pattern onPatternLoad(){
        return null;

    }
    protected String         onUrlLoad(){
        return null;
    }
    protected Double         onValueExtract(Object context) throws InvalidSourceArchitectureException {
        return null;
    }

    @Override
    protected String onPdfFileNameGet() {
        return null;
    }

}
