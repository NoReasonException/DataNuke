package com.noreasonexception.loadable.childs;

import com.noreasonexception.datanuke.app.ValueFilter.AbstractValueFilter;
import com.noreasonexception.datanuke.app.threadRunner.ThreadRunnerTaskEventsDispacher;
import com.noreasonexception.loadable.base.HtmlParser;

import java.util.regex.Pattern;

public class A34_Destatis_ConsumerPrices_GER extends HtmlParser {
    public A34_Destatis_ConsumerPrices_GER(ThreadRunnerTaskEventsDispacher disp,
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
