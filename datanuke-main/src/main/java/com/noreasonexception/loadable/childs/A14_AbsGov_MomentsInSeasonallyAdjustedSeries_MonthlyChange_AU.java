package com.noreasonexception.loadable.childs;

import com.noreasonexception.datanuke.app.ValueFilter.AbstractValueFilter;
import com.noreasonexception.datanuke.app.threadRunner.ThreadRunnerTaskEventsDispacher;
import com.noreasonexception.loadable.base.HtmlParser;

import java.util.regex.Pattern;

public class A14_AbsGov_MomentsInSeasonallyAdjustedSeries_MonthlyChange_AU extends HtmlParser {
    public A14_AbsGov_MomentsInSeasonallyAdjustedSeries_MonthlyChange_AU(ThreadRunnerTaskEventsDispacher disp,
                                                                         AbstractValueFilter<Double> valueFilter) {
        super(disp, valueFilter);
    }
    protected Pattern onPatternLoad(){
        return null;

    }
    protected String    onUrlLoad(){
        return null;
    }
    protected Double    onValueExtract(String tmpString){
        return null;
    }

}