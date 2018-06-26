package com.noreasonexception.loadable.childs;

import com.noreasonexception.datanuke.app.ValueFilter.CsvValueFilter;
import com.noreasonexception.datanuke.app.threadRunner.ThreadRunnerTaskEventsDispacher;
import com.noreasonexception.loadable.base.PdfParser;

import java.util.regex.Pattern;

public final class Census_CoreDurableGoodsOrders_pdf extends PdfParser {
    public Census_CoreDurableGoodsOrders_pdf(ThreadRunnerTaskEventsDispacher disp, CsvValueFilter valueFilter) {
        super(disp, valueFilter);
    }

    @Override
    protected Double onValueExtract(String tmpString) {
        return null;
    }

    @Override
    protected String onPdfFileNameGet() {
        return null;
    }

    @Override
    protected Pattern onPatternLoad() {
        return null;
    }

    @Override
    protected String onUrlLoad() {
        return null;
    }

    @Override
    public void run() {
        System.out.println("RUNN!");
    }
}
