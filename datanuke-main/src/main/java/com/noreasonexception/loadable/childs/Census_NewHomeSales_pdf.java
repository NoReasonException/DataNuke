package com.noreasonexception.loadable.childs;

import com.noreasonexception.datanuke.app.SaveRequestFilterHandler.CsvValueFilter;
import com.noreasonexception.datanuke.app.threadRunner.ThreadRunnerTaskEventsDispacher;
import com.noreasonexception.loadable.base.PdfParser;
import com.noreasonexception.loadable.base.error.InvalidSourceArchitectureException;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public final class Census_NewHomeSales_pdf extends PdfParser {
    public Census_NewHomeSales_pdf(ThreadRunnerTaskEventsDispacher disp, CsvValueFilter valueFilter) {
        super(disp,valueFilter);
    }

    protected Pattern        onPatternLoad(){
        return Pattern.compile("(.*)(New Houses Sold1:)\\s*(\\d*,\\d*)(.*)");

    }
    protected String         onUrlLoad(){
        return "\"https://www.census.gov/construction/nrs/pdf/newressales.pdf\"";
    }
    protected Double         onValueExtract(Object context) throws InvalidSourceArchitectureException {
        Pattern p = getPattern();
        Matcher m = p.matcher((String) context);
        if(!m.find()){
            System.out.println("not found"); return null;/* throw exception that changhed pattern*/ }
        return Double.valueOf(m.group(3).replace(",","."));
    }

    @Override
    protected String onPdfFileNameGet() {
        return "newressales.pdf";
    }

}
