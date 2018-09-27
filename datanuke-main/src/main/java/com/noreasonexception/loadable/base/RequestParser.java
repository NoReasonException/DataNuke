package com.noreasonexception.loadable.base;

import com.noreasonexception.datanuke.app.ValueFilter.AbstractValueFilter;
import com.noreasonexception.datanuke.app.ValueFilter.error.CsvValueFilterException;
import com.noreasonexception.datanuke.app.threadRunner.ThreadRunnerTaskEventsDispacher;
import com.snowtide.pdf.V;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/***
 * RequestParser
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
abstract public class RequestParser extends AbstractParser{
    protected final int REQUESTS_MAX=5;


    /***
     * Returns the value filter , to inform the .csv file with the new values!
     * @return the CsvValueFilter
     */
    public RequestParser(ThreadRunnerTaskEventsDispacher disp, AbstractValueFilter<Double> valueFilter)
    {
        super(disp,valueFilter);
    }

    /****
     * @Overridable_By_Children
     * This method called when the parser it needs the url of the source
     * @return a plain string containing the URL
     */
    abstract protected String onUrlLoad();



    /***
     * Returns the HttpURLConnection used by convertSourceToText to extract the source information as string
     * @return The HttpURLConnection object //TODO Consider making the RequestParser Handlers only Connection Objects(to be flexible in case of http/https)
     * @throws MalformedURLException in case of bad url form
     * @throws IOException           in case of any IOE (not internet found for example)
     */
    protected HttpURLConnection onConnection() throws MalformedURLException,IOException{
        return (HttpURLConnection)
                new URL(onUrlLoad()).openConnection();
    }



}
