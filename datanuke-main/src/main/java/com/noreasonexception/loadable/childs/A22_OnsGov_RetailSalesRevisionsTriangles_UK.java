package com.noreasonexception.loadable.childs;

import com.noreasonexception.datanuke.app.ValueFilter.AbstractValueFilter;
import com.noreasonexception.datanuke.app.threadRunner.ThreadRunnerTaskEventsDispacher;
import com.noreasonexception.loadable.base.XlsParser;
import com.noreasonexception.loadable.base.error.InvalidSourceArchitectureException;
import com.noreasonexception.loadable.base.error.NoUserAgentRequired;
import com.noreasonexception.loadable.base.etc.UserAgent;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Workbook;

public class A22_OnsGov_RetailSalesRevisionsTriangles_UK extends XlsParser {
    public A22_OnsGov_RetailSalesRevisionsTriangles_UK(ThreadRunnerTaskEventsDispacher disp,
                                                       AbstractValueFilter<Double> valueFilter) {
        super(disp, valueFilter);
    }

    @Override
    protected HSSFSheet getSheet(Workbook workbook) {
        return (HSSFSheet) workbook.getSheetAt(0);
    }

    @Override
    protected String onUrlLoad() {
        return "https://www.ons.gov.uk/file?uri=/businessindustryandtrade/retailindustry/datasets/retailsalesrevisionstriangles1monthgrowth/current/dataset6.xls";
    }

    @Override
    protected Double onValueExtract(Object context) throws InvalidSourceArchitectureException {
        Workbook workbook;
        HSSFSheet sheet = getSheet(workbook = (Workbook) context);
        String v;
        for (Row row : sheet) {
            try {

                HSSFCell cell = (HSSFCell) row.getCell(1);
                if (cell == null) continue;
                if (cell.toString().contains("Latest")) {
                    int i=0;
                    while(row.getCell(i+=1)!=null);
                    return Double.valueOf(row.getCell(i-1).toString());
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        throw new InvalidSourceArchitectureException(getClass());
    }

    @Override
    protected UserAgent onUserAgentFieldLoad() throws NoUserAgentRequired {
        return UserAgent.DATANUKE;
    }
}
