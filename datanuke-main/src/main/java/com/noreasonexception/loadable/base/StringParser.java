package com.noreasonexception.loadable.base;

import com.noreasonexception.datanuke.app.saverequestfilterhandler.SaveRequestFilterHandler;
import com.noreasonexception.datanuke.app.threadRunner.ThreadRunnerTaskEventsDispacher;
import com.noreasonexception.loadable.base.error.ConvertionSourceToTextException;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;

abstract public class StringParser extends RequestParser {
    public StringParser(ThreadRunnerTaskEventsDispacher disp, SaveRequestFilterHandler<Double> valueFilter) {
        super(disp, valueFilter);
    }

    /****
     * convertSourceToText
     * this routine has the responsibility to transform the source to plain String
     * for example ->    the HtmlParser will just return the text in text/html
     *                   the PdfParser will convert and return the .pdf file into simple text
     *                   the CsvParser will convert and return the .csv file into simple text
     * @return           The contexts of Source in java.lang.String object
     * */


    protected String convertSourceToText() throws ConvertionSourceToTextException {
        StringBuilder stringBuilder=new StringBuilder();
        int temp;
        try {
            InputStream stream = new BufferedInputStream(onConnection().getInputStream());
            while((temp=stream.read())!=-1){
                stringBuilder.append((char)temp);
            }
            return stringBuilder.toString();
        }catch (IOException e){
            throw new ConvertionSourceToTextException(getClass(),e);
        }


    }


}
