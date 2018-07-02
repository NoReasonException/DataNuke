package com.noreasonexception.loadable.base;

import com.noreasonexception.datanuke.app.ValueFilter.AbstractValueFilter;
import com.noreasonexception.datanuke.app.threadRunner.ThreadRunnerTaskEventsDispacher;
import com.snowtide.PDF;
import com.snowtide.pdf.OutputTarget;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;

abstract public class OpenDocumentParser extends AbstractParser {
    public OpenDocumentParser(ThreadRunnerTaskEventsDispacher disp, AbstractValueFilter<Double> valueFilter) {
        super(disp, valueFilter);
    }

    @Override
    protected String convertSourceToText() {
        return null;
    }
}
