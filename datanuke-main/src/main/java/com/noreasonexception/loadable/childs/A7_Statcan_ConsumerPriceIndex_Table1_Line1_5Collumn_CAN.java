package com.noreasonexception.loadable.childs;

import com.noreasonexception.datanuke.app.ValueFilter.AbstractValueFilter;
import com.noreasonexception.datanuke.app.threadRunner.ThreadRunnerTaskEventsDispacher;
import com.noreasonexception.loadable.base.HtmlParser;

import java.util.regex.Pattern;

public class A7_Statcan_ConsumerPriceIndex_Table1_Line1_5Collumn_CAN extends HtmlParser {
    public A7_Statcan_ConsumerPriceIndex_Table1_Line1_5Collumn_CAN(ThreadRunnerTaskEventsDispacher disp,
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
