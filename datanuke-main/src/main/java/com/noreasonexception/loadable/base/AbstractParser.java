package com.noreasonexception.loadable.base;

import com.noreasonexception.datanuke.app.ValueFilter.CsvValueFilter;
import com.noreasonexception.datanuke.app.ValueFilter.error.CsvValueFilterException;
import com.noreasonexception.datanuke.app.classloader.AtlasLoader;
import com.noreasonexception.datanuke.app.threadRunner.ThreadRunnerTaskEventsDispacher;
import com.snowtide.pdf.V;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.regex.Pattern;

/***
 * AbstractParser
 * this class represents an abstract parser
 * And what is an parser?
 * A parser is a subsystem who its mission is..
 *
 * 1) Take arbitrary data via a DataProvider in a known interval
 * 2) Retrieves specific information inside these data
 * 3) Informs the ValueFilter
 * 4) When a new value is discovered , the ValueFilter.submitValue() returns true .
 * 5) When the @4 happens , the Parser must kill himself
 * @implNote In some steps , the ThreadRunnerTaskEventsDispacher must informed by corresponding methods
 */
abstract public class AbstractParser implements Runnable{



    private ThreadRunnerTaskEventsDispacher dispacher;
    private HttpURLConnection               connection;
    private CsvValueFilter                  valueFilter;


    private java.util.regex.Pattern                         pattern;
    private String                          nameofSource;
    abstract protected String         convertSourceToText();
    abstract protected java.util.regex.Pattern        onPatternLoad();
    abstract protected String         onUrlLoad();
    abstract protected Double         onValueExtract(String tmpString);
    protected HttpURLConnection       onConnection() throws MalformedURLException,IOException{
        return (HttpURLConnection)(new URL("https://www.census.gov/construction/nrs/pdf/newressales.pdf")).openConnection();
    }



    protected ThreadRunnerTaskEventsDispacher getDispacher() {
        return this.dispacher;
    }
    protected CsvValueFilter getValueFilter() {
        return this.valueFilter;
    }
    public AbstractParser(ThreadRunnerTaskEventsDispacher disp, CsvValueFilter valueFilter)
    {
        this.dispacher=disp;
        this.valueFilter=valueFilter;
    }
    protected Pattern getPattern() {
        return pattern;
    }

    protected void setPattern(Pattern pattern) {
        this.pattern = pattern;
    }

    protected boolean loop() {
        setPattern(onPatternLoad());
        String temp;
        Double tempValue;

        while(true){
            temp=convertSourceToText();
            tempValue=onValueExtract(temp);
            if(informValueFilter(tempValue)){
                //inform that value finded!
                //exit
            }
            System.out.println(tempValue);
            return true;
        }
    }
    protected boolean informValueFilter(Double value) {
        try{
            getValueFilter().submitValue(getClass().getName(),value);
            return true;
        }catch (CsvValueFilterException e){
            return false;
        }
    }
}
