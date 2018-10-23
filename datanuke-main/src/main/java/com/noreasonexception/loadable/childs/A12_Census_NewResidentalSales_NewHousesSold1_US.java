package com.noreasonexception.loadable.childs;

import com.noreasonexception.datanuke.app.ValueFilter.AbstractValueFilter;
import com.noreasonexception.datanuke.app.threadRunner.ThreadRunnerTaskEventsDispacher;
import com.noreasonexception.loadable.base.AbstractParser;
import com.noreasonexception.loadable.base.PdfParser;
import com.noreasonexception.loadable.base.error.InvalidSourceArchitectureException;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class A12_Census_NewResidentalSales_NewHousesSold1_US extends PdfParser {

    public A12_Census_NewResidentalSales_NewHousesSold1_US(ThreadRunnerTaskEventsDispacher disp,
                                                           AbstractValueFilter<Double> valueFilter) {
        super(disp, valueFilter);
    }
    protected Pattern onPatternLoad(){
        return Pattern.compile("(New Houses Sold1:)\\s*(\\d*[.,]\\d*)");

    }
    protected String    onUrlLoad(){
        return"https://www.census.gov/construction/nrs/pdf/newressales.pdf";

    }
    protected Double    onValueExtract(Object context) throws InvalidSourceArchitectureException {
        Matcher matcher=getPattern().matcher((String)context);
        AbstractParser.Utills.triggerMacherMethodFindNTimes(matcher,1);
        return Double.valueOf(matcher.group(2).replace(",","."));
    }

    @Override
    protected String onPdfFileNameGet() {
        return null;
    }
}

