package com.noreasonexception.loadable.childs;

import com.noreasonexception.datanuke.app.ValueFilter.AbstractValueFilter;
import com.noreasonexception.datanuke.app.threadRunner.ThreadRunnerTaskEventsDispacher;
import com.noreasonexception.loadable.base.AbstractParser;
import com.noreasonexception.loadable.base.CsvParser;
import com.noreasonexception.loadable.base.HtmlParser;
import com.noreasonexception.loadable.base.TableParser;
import com.noreasonexception.loadable.base.error.InvalidSourceArchitectureException;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class A17_AbsGov_SeasonallyAjusted_Coll3_AU extends TableParser {
    public A17_AbsGov_SeasonallyAjusted_Coll3_AU(ThreadRunnerTaskEventsDispacher disp,
                                                 AbstractValueFilter<Double> valueFilter) {
        super(disp, valueFilter);
    }

    @Override
    protected String onTableSignatureStartLoad() {
        return "<p><a name=\"272415151023994954992724151510239950\"></a>\n" +
                "<table width=\"100%\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\">";
    }

    @Override
    protected String onTableSignatureEndLoad() {
        return "</table>\n" +
                " \n" +
                "<p><font size=\"4\"> </font>\n" +
                "<p>";
    }

    @Override
    protected Pattern getValuePattern() {
        return Pattern.compile("div align=\"right\"><font size=\"2\">(.*)</font></div");
    }

    @Override
    protected int onRowIndexLoad() {
        return 6;
    }

    @Override
    protected int onCellIndexLoad() {
        return 5;
    }

    @Override
    protected String onUrlLoad() {
        return "http://www.abs.gov.au/ausstats/abs@.nsf/0/A5FB33BD2E3CC68FCA257496001547A1?Opendocument";
    }

    @Override
    protected Double onValueExtract(Object context) throws InvalidSourceArchitectureException {
        Double t;
        String actualTable = getTableElement((String) context); //getTable
        String row = getRawRow(actualTable, onRowIndexLoad());
        System.out.println(t=Double.valueOf(cellToValue(getCell(row, onCellIndexLoad()))));
        return t;


    }

    protected String cellToValue(String cell){
        Matcher matcher=getValuePattern().matcher(cell);
        AbstractParser.Utills.triggerMacherMethodFindNTimes(matcher,1);
        String retval;
        if(!(retval=matcher.group(1).replaceFirst(" ",".").
                replaceAll("(\\d)\\.$","$1")).contains(".")){
            retval="0."+retval;

        }
        return retval;
    }
}
