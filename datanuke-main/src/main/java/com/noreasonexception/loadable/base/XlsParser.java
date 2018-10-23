package com.noreasonexception.loadable.base;

import com.noreasonexception.datanuke.app.ValueFilter.AbstractValueFilter;
import com.noreasonexception.datanuke.app.threadRunner.ThreadRunnerTaskEventsDispacher;
import com.noreasonexception.loadable.base.error.InvalidSourceArchitectureException;
import com.noreasonexception.loadable.base.etc.LoopOperationResult;
import com.noreasonexception.loadable.base.etc.LoopOperationStatus;
import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.ss.usermodel.Workbook;

import java.io.IOException;

abstract public class XlsParser extends RequestParser {
    public XlsParser(ThreadRunnerTaskEventsDispacher disp, AbstractValueFilter<Double> valueFilter) {
        super(disp,valueFilter);
    }

    @Override
    protected LoopOperationStatus loop() {
        Double tmp=0d;
        for (int i = 0; i < REQUESTS_MAX; i++)
        {

            try{
                if(informValueFilter(tmp=onValueExtract(getXlsWorkbook()))){
                    return LoopOperationStatus.buildSuccess(tmp);
                }
            }catch (InvalidSourceArchitectureException e){
                return LoopOperationStatus.buildExceptionThrown(e);
            }
        }
        return LoopOperationStatus.buildSameValueSituation(tmp);

    }

    abstract protected HSSFSheet getSheet(Workbook workbook);



    /***
     * Using the Apache Poi in order to read every .xls file!
     * WorkBook is the main object which represents every .xls file! we obtain this by
     * getting the InputStream of connection using onConnection() method (provided by RequestParser)
     * @return a brand-new HSSFWorkbook
     */
    protected Workbook getXlsWorkbook() {

        try {

            return new HSSFWorkbook(onConnection().getInputStream());

        } catch (IOException e) {

            e.printStackTrace();
        }
        return new HSSFWorkbook();
    }
}
