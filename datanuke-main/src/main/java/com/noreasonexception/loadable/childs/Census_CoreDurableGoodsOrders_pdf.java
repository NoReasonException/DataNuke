package com.noreasonexception.loadable.childs;

import com.noreasonexception.datanuke.app.ValueFilter.CsvValueFilter;
import com.noreasonexception.datanuke.app.threadRunner.ThreadRunnerTaskEventsDispacher;
import com.noreasonexception.loadable.base.RequestParser;
import com.noreasonexception.loadable.base.PdfParser;

import java.security.InvalidParameterException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public final class Census_CoreDurableGoodsOrders_pdf extends PdfParser {
    public Census_CoreDurableGoodsOrders_pdf(ThreadRunnerTaskEventsDispacher disp, CsvValueFilter valueFilter) {
        super(disp, valueFilter);
    }

    protected Pattern        onPatternLoad(){
        return Pattern.compile("(.*\\d+[.]\\d+\\s+)");

    }
    protected String         onUrlLoad(){
        return "https://www.census.gov/manufacturing/m3/adv/pdf/table1a.pdf";
    }
    protected Double         onValueExtract(String tmpString){
        Pattern p = getPattern();
        Matcher m = p.matcher(tmpString);
        RequestParser.Utills.triggerMacherMethodFindNTimes(m,41);
        if(!m.find()){
            System.out.println("not found");
            throw new InvalidParameterException("Pattern is not find anything!");/* throw exception that changhed pattern*/ }
        return Double.valueOf(m.group(0).replace(",","."));
    }

    @Override
    protected String onPdfFileNameGet() {
        return "table1a.pdf";
    }
}
//here
