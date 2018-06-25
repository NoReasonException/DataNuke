package com.noreasonexception.loadable.childs;

import com.noreasonexception.datanuke.app.ValueFilter.CsvValueFilter;
import com.noreasonexception.datanuke.app.threadRunner.ThreadRunnerTaskEventsDispacher;
import com.noreasonexception.loadable.base.PdfParser;
import com.snowtide.PDF;
import com.snowtide.pdf.Document;
import com.snowtide.pdf.OutputTarget;

import javax.naming.OperationNotSupportedException;
import javax.net.ssl.HttpsURLConnection;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.security.spec.InvalidParameterSpecException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class TestClass extends PdfParser {
    public TestClass(ThreadRunnerTaskEventsDispacher disp, CsvValueFilter valueFilter) {
        super(disp,valueFilter);
    }

    @Override
    protected void finalize() {
        getDispacher().submitTaskThreadTerminatedEvent(getClass().getName());
    }



    protected Pattern        onPatternLoad(){
        return Pattern.compile("(.*)(New Houses Sold1:)\\s*(\\d*,\\d*)(.*)");
    }
    protected String         onUrlLoad(){
        return "\"https://www.census.gov/construction/nrs/pdf/newressales.pdf\"";
    }
    protected Double         onValueExtract(String tmpString){
        System.out.println("onValueExtract");
        Pattern p = getPattern();
        Matcher m = p.matcher(tmpString);
        if(!m.find()){
            System.out.println("not found"); return null;/* throw exception that changhed pattern*/ }
        return Double.valueOf(m.group(3).replace(",","."));
    }

    @Override
    protected String onPdfFileNameGet() {
        return "newressales.pdf";
    }

    @Override
    public void run() {
       loop();

    }
}
