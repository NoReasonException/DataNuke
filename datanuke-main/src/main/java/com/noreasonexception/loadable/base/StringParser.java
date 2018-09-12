package com.noreasonexception.loadable.base;

import com.noreasonexception.datanuke.app.ValueFilter.AbstractValueFilter;
import com.noreasonexception.datanuke.app.threadRunner.ThreadRunnerTaskEventsDispacher;
import com.noreasonexception.loadable.base.error.ConvertionSourceToTextException;

abstract public class StringParser extends RequestParser {
    public StringParser(ThreadRunnerTaskEventsDispacher disp, AbstractValueFilter<Double> valueFilter) {
        super(disp, valueFilter);
    }

    /****
     * convertSourceToText
     * this routine has the responsibility to transform the source to plain String
     * for example ->    the HtmlParser will just return the text in text/html
     *                   the PdfParser will convert and return the .pdf file into simple text and vise versa
     * @return           The contexts of Source in java.lang.String object
     * */
    abstract protected String         convertSourceToText() throws ConvertionSourceToTextException;


}
