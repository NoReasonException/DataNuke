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

    protected String    onUrlLoad(){
        return "https://www.ons.gov.uk/generator?format=csv&uri=/economy/inflationandpriceindices/timeseries/l55o/mm23";
    }
    protected int onCsvValueIndexLoad(int numberOfValues){
        return numberOfValues-1;
    }
}
