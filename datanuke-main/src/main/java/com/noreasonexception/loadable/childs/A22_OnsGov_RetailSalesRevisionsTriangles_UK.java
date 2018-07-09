package com.noreasonexception.loadable.childs;

import com.noreasonexception.datanuke.app.ValueFilter.AbstractValueFilter;
import com.noreasonexception.datanuke.app.threadRunner.ThreadRunnerTaskEventsDispacher;
import com.noreasonexception.loadable.base.XlsParser;

import java.util.regex.Pattern;

public class A22_OnsGov_RetailSalesRevisionsTriangles_UK extends XlsParser {
    public A22_OnsGov_RetailSalesRevisionsTriangles_UK(ThreadRunnerTaskEventsDispacher disp,
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
