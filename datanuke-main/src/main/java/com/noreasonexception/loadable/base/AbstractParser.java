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


    private java.util.regex.Pattern          pattern;
    private String                          nameofSource;
    /****
     * convertSourceToText
     * this routine has the responsibility to transform the source to plain String
     * for example ->    the HtmlParser will just return the text in text/html
     *                   the PdfParser will convert and return the .pdf file into simple text and vise versa
     * @return           The contexts of Source in java.lang.String object
     * */
    abstract protected String         convertSourceToText();

    /****
     * @Overridable_By_Children
     * This is called when the Parser is loading the corresponding pattern Object , just return the
     * needed pattern by compile it (Pattern.compile(..))
     * @return an Java.utill.regex.Pattern object
     */
    abstract protected java.util.regex.Pattern onPatternLoad();

    /****
     * @Overridable_By_Children
     * This method called when the parser it needs the url of the source
     * @return a plain string containing the URL
     */
    abstract protected String onUrlLoad();

    /****
     * @Overridable_By_Children
     * This routine is combines the results of convertSourceToText( given in tmpString parameter)
     * with the given pattern (using getPattern()) to extract the value needed
     * @param tmpString the source text provided by convertSourceToText
     * @return a Double Object //TODO maybe this methods need to return a plain Object object?
     */
    abstract protected Double onValueExtract(String tmpString);

    /***
     * Returns the HttpURLConnection used by convertSourceToText to extract the source information as string
     * @return The HttpURLConnection object //TODO Consider making the AbstractParser Handlers only Connection Objects(to be flexible in case of http/https)
     * @throws MalformedURLException in case of bad url form
     * @throws IOException           in case of any IOE (not internet found for example)
     */
    protected HttpURLConnection       onConnection() throws MalformedURLException,IOException{
        return (HttpURLConnection)(new URL("https://www.census.gov/construction/nrs/pdf/newressales.pdf")).openConnection();
    }

    /****
     * Returns the dispacher object , used to inform the outside subscribers for events inside this parser via ThreadRunner
     * @return the ThreadRunnerTaskEventsDispacher
     */
    protected ThreadRunnerTaskEventsDispacher getDispacher() {
        return this.dispacher;
    }

    /***
     * Returns the value filter , to inform the .csv file with the new values!
     * @return the CsvValueFilter
     */
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

    /****
     * The main loop of AbstractParser
     * the .run() method calls it
     * It is basically an infinite loop , stopping only if the ValueFilter detects the new value
     * //TODO in case of changed date in source , this will fail in infinite loop , so a maximum inteval is needed!
     * @return true in success
     */
    protected boolean loop() {
        setPattern(onPatternLoad());
        System.out.println("started");
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

    /***
     * Informs the value filter for new values , is called by .loop()
     * @param value
     * @return
     */
    protected boolean informValueFilter(Double value) {
        try{
            getValueFilter().submitValue(getClass().getName(),value);
            return true;
        }catch (CsvValueFilterException e){
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public void run() {
        System.out.println("run \t"+getClass().getName());
        loop();
    }
    @Override
    protected void finalize() {
        getDispacher().submitTaskThreadTerminatedEvent(getClass().getName());
    }

}
