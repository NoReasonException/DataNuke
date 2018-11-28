package com.noreasonexception.loadable.childs;

import com.noreasonexception.datanuke.app.SaveRequestFilterHandler.SaveRequestFilterHandler;
import com.noreasonexception.datanuke.app.threadRunner.ThreadRunnerTaskEventsDispacher;
import com.noreasonexception.loadable.base.XlsParser;
import com.noreasonexception.loadable.base.error.InvalidSourceArchitectureException;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Workbook;

public class A11_Census_AdvancedReportHighlights_Table1_ShipmentAndNewOrder_Line6_US extends XlsParser {
    public A11_Census_AdvancedReportHighlights_Table1_ShipmentAndNewOrder_Line6_US(ThreadRunnerTaskEventsDispacher disp,
                                                                                   SaveRequestFilterHandler<Double> valueFilter) {
        super(disp, valueFilter);
    }

    @Override
    protected HSSFSheet getSheet(Workbook workbook) {
        return (HSSFSheet) workbook.getSheetAt(0);
    }

    @Override
    protected String onUrlLoad() {
        return "https://www.census.gov/manufacturing/m3/adv/table1a.xls";
    }

    @Override
    protected Double onValueExtract(Object context) throws InvalidSourceArchitectureException {
        Workbook workbook;
        HSSFSheet sheet = getSheet(workbook = (Workbook) context);
        String v;
        for (Row row : sheet) {
            try {

                HSSFCell cell = (HSSFCell) row.getCell(0);
                if (cell == null) continue;
                if (cell.toString().contains("New Orders") && row.getRowNum() > 1) {
                    return Double.valueOf(row.getCell(5).toString());
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        throw new InvalidSourceArchitectureException(getClass());
    }
}
