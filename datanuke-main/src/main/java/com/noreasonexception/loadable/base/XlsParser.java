package com.noreasonexception.loadable.base;

import com.noreasonexception.datanuke.app.ValueFilter.AbstractValueFilter;
import com.noreasonexception.datanuke.app.threadRunner.ThreadRunnerTaskEventsDispacher;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Workbook;

import java.io.IOException;

abstract public class XlsParser extends RequestParser {
    public XlsParser(ThreadRunnerTaskEventsDispacher disp, AbstractValueFilter<Double> valueFilter) {
        super(disp,valueFilter);
    }

    @Override
    protected boolean loop() {
        HSSFWorkbook workbook=(HSSFWorkbook) getXlsWorkbook();
        HSSFSheet sheet=workbook.getSheetAt(0);
        for (Row i:sheet){
            try {

                /*HSSFCellStyle style = (HSSFCellStyle) i.getCell(1).getCellStyle();
                HSSFFont font = style.getFont(workbook);
                //System.out.println(r.getCell(1)+"->" + font.getBoldweight());
                if(font.getBold() && onValueExtract(i.getCell(1)){
                    System.out.println(i.getCell(1)+"new value");
                    return true;
                }*/
            }catch (Exception e){continue;}
        }
        return false;

    }

    @Override
    protected Double onValueExtract(Object tmpString) {
        return null;
    }

    @Override
    protected String onUrlLoad() {
        return "https://www.census.gov/construction/nrc/xls/newresconst.xls";
    }

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
            ///TODO : Fox
        }
        return new HSSFWorkbook();
    }
}
