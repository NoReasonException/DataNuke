package com.noreasonexception.loadable.base;

import com.noreasonexception.datanuke.app.ValueFilter.AbstractValueFilter;
import com.noreasonexception.datanuke.app.threadRunner.ThreadRunnerTaskEventsDispacher;
import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Workbook;

import java.io.IOException;

abstract public class XlsParser extends RequestParser {
    public XlsParser(ThreadRunnerTaskEventsDispacher disp, AbstractValueFilter<Double> valueFilter) {
        super(disp,valueFilter);
    }

    @Override
    protected boolean loop() {
        for (int i = 0; i < REQUESTS_MAX; i++)
        {
            Double tmp;
            if(informValueFilter(tmp=onValueExtract(getXlsWorkbook()))){
                System.out.println(tmp);
                return true;
            }

        }
        return false;

    }

    abstract protected HSSFSheet getSheet(Workbook workbook);



    /***
     * Using the Apache Poi in order to read every .xls file!
     * WorkBook is the main object which represents every .xls file! we obtain this by
     * getting the InputStream of connection using onConnection() method (provided by RequestParser)
     * @return a brand-new HSSFWorkbook
     */
    abstract protected Workbook getXlsWorkbook();
}
