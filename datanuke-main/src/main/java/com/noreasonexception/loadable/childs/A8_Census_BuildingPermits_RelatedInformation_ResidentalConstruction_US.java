package com.noreasonexception.loadable.childs;

import com.noreasonexception.datanuke.app.ValueFilter.AbstractValueFilter;
import com.noreasonexception.datanuke.app.threadRunner.ThreadRunnerTaskEventsDispacher;
import com.noreasonexception.loadable.base.XlsParser;
import com.noreasonexception.loadable.base.error.InvalidSourceArchitectureException;
import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Workbook;

public class A8_Census_BuildingPermits_RelatedInformation_ResidentalConstruction_US extends XlsParser {
    public A8_Census_BuildingPermits_RelatedInformation_ResidentalConstruction_US(ThreadRunnerTaskEventsDispacher disp,
                                                                                  AbstractValueFilter<Double> valueFilter) {
        super(disp, valueFilter);
    }
    @Override
    protected Double onValueExtract(Object context) throws InvalidSourceArchitectureException {
        Workbook workbook;
        HSSFSheet sheet=getSheet(workbook=(Workbook)context);
        for (Row row:sheet){
            try {

                HSSFCell cell=(HSSFCell) row.getCell(1);
                if(cell==null)continue;
                HSSFCellStyle style = cell.getCellStyle();
                HSSFFont font = style.getFont(workbook);
                if(font.getBold()) {
                    return Double.valueOf(row.getCell(1).toString());

                }
            }catch (Exception e){break;}
        }
        throw new InvalidSourceArchitectureException(getClass());
    }

    @Override
    protected HSSFSheet getSheet(Workbook workbook) {
        return (HSSFSheet) workbook.getSheetAt(0);
    }

    @Override
    protected String onUrlLoad() {
        return "https://www.census.gov/construction/nrc/xls/newresconst.xls";
    }


}
