package com.noreasonexception.loadable.base;

import com.noreasonexception.datanuke.app.ValueFilter.AbstractValueFilter;
import com.noreasonexception.datanuke.app.threadRunner.ThreadRunnerTaskEventsDispacher;
import com.snowtide.PDF;
import com.snowtide.pdf.OutputTarget;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;

abstract public class PdfParser extends PatternParser {
    public PdfParser(ThreadRunnerTaskEventsDispacher disp, AbstractValueFilter<Double> valueFilter) {
        super(disp,valueFilter);
    }


    /***
     * @implNote TODO com.snowtide.pdf.Document class for some reason keeps a reference
     * @return
     */
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
            pdf=null;
            c.disconnect();
            c=null;
            s.close();
            s=null;
            return text.toString();

        }catch (IOException e){e.printStackTrace();return null;}


    }






    abstract protected String onPdfFileNameGet();

}
