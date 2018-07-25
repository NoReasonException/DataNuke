package com.noreasonexception.loadable.base;

import com.noreasonexception.datanuke.app.ValueFilter.AbstractValueFilter;
import com.noreasonexception.datanuke.app.threadRunner.ThreadRunnerTaskEventsDispacher;
import com.snowtide.PDF;
import com.snowtide.pdf.OutputTarget;

import java.io.FileInputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.util.ArrayList;

abstract public class CsvParser extends AbstractParser {
    public CsvParser(ThreadRunnerTaskEventsDispacher disp, AbstractValueFilter<Double> valueFilter) {
        super(disp, valueFilter);
    }

    @Override
    protected String convertSourceToText() {
        HttpURLConnection c;
        InputStream s;
        StringBuilder text = new StringBuilder();
        int j;

        try {
            c = onConnection();
            s = c.getInputStream();

            while ((j = s.read()) != -1) {
                text.append((char) j);
            }
            return text.toString();

        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }

    }
    protected ArrayList convertSourceToArrayList(){
        return null;
    }
}