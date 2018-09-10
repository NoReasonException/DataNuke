package com.noreasonexception.loadable.childs;

import com.noreasonexception.datanuke.app.ValueFilter.AbstractValueFilter;
import com.noreasonexception.datanuke.app.threadRunner.ThreadRunnerTaskEventsDispacher;
import com.noreasonexception.loadable.base.XlsParser;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.ss.usermodel.Workbook;

import java.util.regex.Pattern;

public class A22_OnsGov_RetailSalesRevisionsTriangles_UK extends XlsParser {
    public A22_OnsGov_RetailSalesRevisionsTriangles_UK(ThreadRunnerTaskEventsDispacher disp,
                                                       AbstractValueFilter<Double> valueFilter) {
        super(disp, valueFilter);
    }


    protected Pattern onPatternLoad(){
        return null;

    }
    protected String    onUrlLoad(){
        return null;
    }
    protected Double    onValueExtract(Object context){
        return null;
    }

    @Override
    protected HSSFSheet getSheet(Workbook workbook) {
        return null;
    }

    @Override
    protected Workbook getXlsWorkbook() {
        return null;
    }
}
