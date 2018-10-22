package com.noreasonexception.loadable.childs;

import com.noreasonexception.datanuke.app.ValueFilter.AbstractValueFilter;
import com.noreasonexception.datanuke.app.threadRunner.ThreadRunnerTaskEventsDispacher;
import com.noreasonexception.loadable.base.XlsParser;
import com.noreasonexception.loadable.base.error.InvalidSourceArchitectureException;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Workbook;

public class A9_Census_ExclusiveMotorVehicleAndParts_US extends XlsParser {
    public A9_Census_ExclusiveMotorVehicleAndParts_US(ThreadRunnerTaskEventsDispacher disp,
                                                      AbstractValueFilter<Double> valueFilter) {
        super(disp, valueFilter);

    }

    @Override
    protected HSSFSheet getSheet(Workbook workbook) {
        return (HSSFSheet) workbook.getSheetAt(1);
    }
    @Override
    protected String onUrlLoad() {
        return "https://www.census.gov/retail/marts/www/marts_current.xls";
    }

    @Override
    protected Double onValueExtract(Object context) throws InvalidSourceArchitectureException {
        Workbook workbook;
        HSSFSheet sheet=getSheet(workbook=(Workbook)context);
        String v;

        for (Row row:sheet){
            try {

                HSSFCell cell=(HSSFCell) row.getCell(1);
                if(cell==null)continue; //the initial headlines does not have always second cells
                System.out.println(row.getCell(2));
                if(cell.toString().contains("Total (excl. motor vehicle & parts) ")) {
                    System.out.println(v=row.getCell(2).toString());
                    return Double.valueOf(v);

                }
            }catch (Exception e){e.printStackTrace();}
        }
        throw new InvalidSourceArchitectureException(getClass());
    }
}
