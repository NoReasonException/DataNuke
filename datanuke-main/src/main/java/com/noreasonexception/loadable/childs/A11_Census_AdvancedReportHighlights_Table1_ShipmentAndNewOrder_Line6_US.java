package com.noreasonexception.loadable.childs;

import com.noreasonexception.datanuke.app.ValueFilter.AbstractValueFilter;
import com.noreasonexception.datanuke.app.threadRunner.ThreadRunnerTaskEventsDispacher;
import com.noreasonexception.loadable.base.PdfParser;

import java.util.regex.Pattern;

public class A11_Census_AdvancedReportHighlights_Table1_ShipmentAndNewOrder_Line6_US extends PdfParser {
    public A11_Census_AdvancedReportHighlights_Table1_ShipmentAndNewOrder_Line6_US(ThreadRunnerTaskEventsDispacher disp,
                                                                                   AbstractValueFilter<Double> valueFilter) {
        super(disp, valueFilter);
    }
    protected Pattern onPatternLoad(){
        return null;

    }
    protected String    onUrlLoad(){
        return null;
    }
    protected Double    onValueExtract(Object context){
        return null;
    }

    @Override
    protected String onPdfFileNameGet() {
        return null;
    }
}
