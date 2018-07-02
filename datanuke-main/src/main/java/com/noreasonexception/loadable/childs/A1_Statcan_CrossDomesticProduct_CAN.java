package com.noreasonexception.loadable.childs;

import com.noreasonexception.datanuke.app.ValueFilter.AbstractValueFilter;
import com.noreasonexception.datanuke.app.threadRunner.ThreadRunnerTaskEventsDispacher;
import com.noreasonexception.loadable.base.AbstractParser;
import com.noreasonexception.loadable.base.HtmlParser;

import java.security.InvalidParameterException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class A1_Statcan_CrossDomesticProduct_CAN extends HtmlParser {
    public A1_Statcan_CrossDomesticProduct_CAN(ThreadRunnerTaskEventsDispacher disp, AbstractValueFilter<Double> valueFilter) {
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
