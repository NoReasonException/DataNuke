package com.noreasonexception.loadable.base;

import com.noreasonexception.datanuke.app.saverequestfilterhandler.SaveRequestFilterHandler;
import com.noreasonexception.datanuke.app.threadRunner.ThreadRunnerTaskEventsDispacher;
import com.noreasonexception.loadable.base.error.ConvertionSourceToTextException;
import com.noreasonexception.loadable.base.error.InvalidSourceArchitectureException;

import java.util.regex.Matcher;
import java.util.regex.Pattern;


/****
 * TODO : Instead of these terrible adapters , split the logic and the repetition process of each parser into
 * two dinstct modules , if we need to create a complex parser , we will just create a second logic module instead
 * of this mess!
 */
public class ComplexPdfParserAdapter {
    protected AbstractParser parent;
    protected PdfParser      child;
    protected ThreadRunnerTaskEventsDispacher dispacher;
    protected SaveRequestFilterHandler<Double> valueFilter;

    public ComplexPdfParserAdapter(AbstractParser parent,PdfParser simpleParser, ThreadRunnerTaskEventsDispacher dispacher, SaveRequestFilterHandler<Double> valueFilter) {
        this.dispacher=dispacher;
        this.valueFilter=valueFilter;
        this.parent=parent;
        this.child=simpleParser;
    }
    public Double onValueExtract() throws InvalidSourceArchitectureException {
        try {

            return child.onValueExtract(child.convertSourceToText());
        }catch (ConvertionSourceToTextException e){
            throw new InvalidSourceArchitectureException(parent.getClass());
        }
    }

}
