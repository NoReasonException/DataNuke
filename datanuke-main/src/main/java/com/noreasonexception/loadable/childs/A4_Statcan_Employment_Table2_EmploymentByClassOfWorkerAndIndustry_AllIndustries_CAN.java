package com.noreasonexception.loadable.childs;

import com.noreasonexception.datanuke.app.ValueFilter.AbstractValueFilter;
import com.noreasonexception.datanuke.app.threadRunner.ThreadRunnerTaskEventsDispacher;
import com.noreasonexception.loadable.base.HtmlParser;

import java.util.regex.Pattern;

public class A4_Statcan_Employment_Table2_EmploymentByClassOfWorkerAndIndustry_AllIndustries_CAN extends HtmlParser {
    public A4_Statcan_Employment_Table2_EmploymentByClassOfWorkerAndIndustry_AllIndustries_CAN
            (ThreadRunnerTaskEventsDispacher disp, AbstractValueFilter<Double> valueFilter) {
        super(disp, valueFilter);
    }

    protected Pattern onPatternLoad(){
        return null;

    }
    protected String         onUrlLoad(){
        return null;
    }
    protected Double         onValueExtract(String tmpString){
        return null;
    }
}
