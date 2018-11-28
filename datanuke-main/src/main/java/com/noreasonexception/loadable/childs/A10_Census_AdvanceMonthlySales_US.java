package com.noreasonexception.loadable.childs;

import com.noreasonexception.datanuke.app.saverequestfilterhandler.SaveRequestFilterHandler;
import com.noreasonexception.datanuke.app.threadRunner.ThreadRunnerTaskEventsDispacher;
import com.noreasonexception.loadable.base.AbstractParser;
import com.noreasonexception.loadable.base.PdfParser;
import com.noreasonexception.loadable.base.error.InvalidSourceArchitectureException;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class A10_Census_AdvanceMonthlySales_US extends PdfParser {
    public A10_Census_AdvanceMonthlySales_US(ThreadRunnerTaskEventsDispacher disp,
                                             SaveRequestFilterHandler<Double> valueFilter) {
        super(disp, valueFilter);
    }

    protected Pattern onPatternLoad(){
        return Pattern.compile("(ADVANCE MONTHLY SALES\\s*)^(.*)(\\d\\.\\d)%",Pattern.MULTILINE|Pattern.CANON_EQ);

    }
    protected String         onUrlLoad(){
        return "https://www.census.gov/retail/marts/www/marts_current.pdf";
    }
    protected Double         onValueExtract(Object context) throws InvalidSourceArchitectureException {

        Matcher matcher=getPattern().matcher((String)context);
        AbstractParser.Utills.triggerMacherMethodFindNTimes(matcher,1);
        try {
            return Double.valueOf(matcher.group(3).toString());
        }catch (NumberFormatException e){
            throw new InvalidSourceArchitectureException(getClass());
        }
    }

    @Override
    protected String onPdfFileNameGet() {
        return "marts_current.pdf";
    }

}
