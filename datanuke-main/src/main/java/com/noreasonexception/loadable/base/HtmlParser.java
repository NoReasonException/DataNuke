package com.noreasonexception.loadable.base;

import com.noreasonexception.datanuke.app.ValueFilter.AbstractValueFilter;
import com.noreasonexception.datanuke.app.threadRunner.ThreadRunnerTaskEventsDispacher;
import com.noreasonexception.loadable.base.error.ConvertionSourceToTextException;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;

abstract public class HtmlParser extends PatternParser {
    public HtmlParser(ThreadRunnerTaskEventsDispacher disp, AbstractValueFilter<Double> valueFilter) {
        super(disp,valueFilter);
    }

    @Override
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
