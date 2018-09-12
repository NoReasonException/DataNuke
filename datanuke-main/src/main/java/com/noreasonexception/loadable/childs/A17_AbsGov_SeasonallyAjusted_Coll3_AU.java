package com.noreasonexception.loadable.childs;

import com.noreasonexception.datanuke.app.ValueFilter.AbstractValueFilter;
import com.noreasonexception.datanuke.app.threadRunner.ThreadRunnerTaskEventsDispacher;
import com.noreasonexception.loadable.base.CsvParser;
import com.noreasonexception.loadable.base.HtmlParser;
import com.noreasonexception.loadable.base.error.InvalidSourceArchitectureException;

import java.util.regex.Pattern;

public class A17_AbsGov_SeasonallyAjusted_Coll3_AU extends CsvParser {
    public A17_AbsGov_SeasonallyAjusted_Coll3_AU(ThreadRunnerTaskEventsDispacher disp,
                                                 AbstractValueFilter<Double> valueFilter) {
        super(disp, valueFilter);
    }

    @Override
    protected int onCsvValueIndexLoad(int numberOfValues) {
        return 0;
    }

    @Override
    protected String onUrlLoad() {
        return null;
    }
}
