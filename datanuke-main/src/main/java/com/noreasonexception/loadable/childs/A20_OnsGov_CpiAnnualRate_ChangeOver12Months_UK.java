package com.noreasonexception.loadable.childs;

import com.noreasonexception.datanuke.app.ValueFilter.AbstractValueFilter;
import com.noreasonexception.datanuke.app.threadRunner.ThreadRunnerTaskEventsDispacher;
import com.noreasonexception.loadable.base.CsvParser;
import com.noreasonexception.loadable.base.error.InvalidSourceArchitectureException;

import java.util.regex.Pattern;

public class A20_OnsGov_CpiAnnualRate_ChangeOver12Months_UK extends CsvParser {
    public A20_OnsGov_CpiAnnualRate_ChangeOver12Months_UK(ThreadRunnerTaskEventsDispacher disp,
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
