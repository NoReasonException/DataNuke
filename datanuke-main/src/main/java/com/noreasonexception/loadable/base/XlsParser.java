package com.noreasonexception.loadable.base;

import com.noreasonexception.datanuke.app.ValueFilter.AbstractValueFilter;
import com.noreasonexception.datanuke.app.threadRunner.ThreadRunnerTaskEventsDispacher;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Row;

import java.io.File;
import java.io.FileInputStream;
import java.util.regex.Pattern;

abstract public class XlsParser extends RequestParser {
    public XlsParser(ThreadRunnerTaskEventsDispacher disp, AbstractValueFilter<Double> valueFilter) {
        super(disp,valueFilter);
    }

    @Override
    protected boolean loop() {
        return true;
    }

    @Override
    protected Double onValueExtract(String tmpString) {
        return null;
    }

    @Override
    protected String convertSourceToText() {
        return null;
    }

    @Override
    protected String onUrlLoad() {
        return null;
    }
}
