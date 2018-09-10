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
 * PattermParser
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
abstract public class PattermParser extends AbstractParser{


    private final int REQUESTS_MAX=5000;
    private HttpURLConnection               connection;
    private java.util.regex.Pattern         pattern;
    private String                          nameofSource;
    public static class Utills{
        public static void triggerMacherMethodFindNTimes(Matcher m,int n){
            for (int i = 0; i < n; i++) {
                m.find();
            }
        }
    }
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



    /***
     * Returns the HttpURLConnection used by convertSourceToText to extract the source information as string
     * @return The HttpURLConnection object //TODO Consider making the PattermParser Handlers only Connection Objects(to be flexible in case of http/https)
     * @throws MalformedURLException in case of bad url form
     * @throws IOException           in case of any IOE (not internet found for example)
     */
    protected HttpURLConnection       onConnection() throws MalformedURLException,IOException{
        return (HttpURLConnection)(new URL(onUrlLoad())).openConnection();
    }

    /***
     * Returns the value filter , to inform the .csv file with the new values!
     * @return the CsvValueFilter
     */
    public PattermParser(ThreadRunnerTaskEventsDispacher disp, AbstractValueFilter<Double> valueFilter)
    {
        super(disp,valueFilter);
    }
    protected Pattern getPattern() {
        return pattern;
    }

    protected void setPattern(Pattern pattern) {
        this.pattern = pattern;
    }

    /****
     * The main loop of PattermParser
     * the .run() method calls it
     * It is basically an infinite loop , stopping only if the ValueFilter detects the new value
     * //TODO in case of changed date in source , this will fail in infinite loop , so a maximum inteval is needed!
     * @return true in success
     */
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
