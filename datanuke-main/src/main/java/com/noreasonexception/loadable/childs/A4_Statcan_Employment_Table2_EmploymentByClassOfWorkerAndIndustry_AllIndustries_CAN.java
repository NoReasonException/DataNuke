package com.noreasonexception.loadable.childs;

import com.noreasonexception.datanuke.app.ValueFilter.AbstractValueFilter;
import com.noreasonexception.datanuke.app.threadRunner.ThreadRunnerTaskEventsDispacher;
import com.noreasonexception.loadable.base.CanStatParser;
import com.noreasonexception.loadable.base.HtmlParser;
import com.noreasonexception.loadable.base.error.InvalidSourceArchitectureException;

import java.util.regex.Pattern;

public class A4_Statcan_Employment_Table2_EmploymentByClassOfWorkerAndIndustry_AllIndustries_CAN extends CanStatParser {
    public A4_Statcan_Employment_Table2_EmploymentByClassOfWorkerAndIndustry_AllIndustries_CAN
            (ThreadRunnerTaskEventsDispacher disp, AbstractValueFilter<Double> valueFilter) {
        super(disp, valueFilter);
    }
    @Override
    protected String onEventNameLoad() {
        return "Building permits";
    }

    @Override
    protected Double onValueExtract(Object context) throws InvalidSourceArchitectureException {
        System.out.println(".onValueExtract(Object context) called");
        return 0d;
    }
}
