package com.noreasonexception.loadable.base;

import com.noreasonexception.datanuke.app.ValueFilter.AbstractValueFilter;
import com.noreasonexception.datanuke.app.threadRunner.ThreadRunnerTaskEventsDispacher;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.regex.Pattern;

abstract public class PatternParser extends RequestParser {
    private java.util.regex.Pattern         pattern;
    private final int REQUESTS_MAX=5000;

    public PatternParser(ThreadRunnerTaskEventsDispacher dispacher, AbstractValueFilter<Double> valueFilter) {
        super(dispacher, valueFilter);
    }
    /****
     * @Overridable_By_Children
     * This is called when the Parser is loading the corresponding pattern Object , just return the
     * needed pattern by compile it (Pattern.compile(..))
     * @return an Java.utill.regex.Pattern object
     */
    abstract protected java.util.regex.Pattern onPatternLoad();
    protected Pattern getPattern() {
        return pattern;
    }

    protected void setPattern(Pattern pattern) {
        this.pattern = pattern;
    }



    protected boolean loop() {
        setPattern(onPatternLoad());
        System.out.println("started"+getClass().getName());
        String temp;
        Double tempValue;
        int i=0;

        while(true){
            temp=convertSourceToText();
            System.out.println("attempt on -> "+getClass().getName());
            try{
                tempValue=onValueExtract(temp);
                if(informValueFilter(tempValue)){
                    getDispacher().submitTaskThreadValueRetrievedEvent(getClass().getName(),tempValue);
                    System.out.println("temp -> "+tempValue);
                    return true;
                }
                if(i>REQUESTS_MAX)break;
                i+=1;
            }catch (NumberFormatException e){
                System.out.println(getClass().getName()+": This Event need update , the page format is unknown");
            }

        }
        return false;
    }
}
