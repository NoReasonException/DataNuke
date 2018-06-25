package com.noreasonexception.loadable.base;

import com.noreasonexception.datanuke.app.ValueFilter.CsvValueFilter;
import com.noreasonexception.datanuke.app.ValueFilter.error.CsvValueFilterException;
import com.noreasonexception.datanuke.app.threadRunner.ThreadRunnerTaskEventsDispacher;
import com.snowtide.PDF;
import com.snowtide.pdf.OutputTarget;

import javax.swing.text.Document;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.regex.Pattern;

abstract public class PdfParser extends AbstractParser {
    public PdfParser(ThreadRunnerTaskEventsDispacher disp, CsvValueFilter valueFilter) {
        super(disp,valueFilter);
    }

    @Override
    protected String convertSourceToText() {
        HttpURLConnection c;
        InputStream s;
        try {
            c = onConnection();
            s=c.getInputStream();
            com.snowtide.pdf.Document pdf = PDF.open(s,onPdfFileNameGet());
            StringBuilder text = new StringBuilder();
            pdf.pipe(new OutputTarget(text));

            pdf.close();
            return text.toString();

        }catch (IOException e){e.printStackTrace();return null;}


    }






    abstract protected String onPdfFileNameGet();

}
