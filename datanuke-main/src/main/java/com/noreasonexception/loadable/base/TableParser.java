package com.noreasonexception.loadable.base;

import com.noreasonexception.datanuke.app.ValueFilter.AbstractValueFilter;
import com.noreasonexception.datanuke.app.threadRunner.ThreadRunnerTaskEventsDispacher;
import com.noreasonexception.loadable.base.error.InvalidSourceArchitectureException;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

abstract public class TableParser extends HtmlParser {
    public TableParser(ThreadRunnerTaskEventsDispacher disp, AbstractValueFilter<Double> valueFilter) {
        super(disp, valueFilter);
    }

    @Override
    protected Pattern onPatternLoad() {
        return Pattern.compile(onTableSignatureStartLoad()+"(.*\\s)"+onTableSignatureEndLoad(),Pattern.MULTILINE|Pattern.DOTALL);
    }

    abstract protected String onTableSignatureStartLoad();
    abstract protected String onTableSignatureEndLoad();
    protected Pattern getRowPattern(){
        return Pattern.compile("<tr.*>(.*)(</td>)");
    }
    protected Pattern getCellPattern(){
        return Pattern.compile("<td(.(?!<))*><((.(?!</td>))*)></td>",Pattern.DOTALL);
    }
    ///override
    abstract protected Pattern getValuePattern();
    //override
    abstract protected int onRowIndexLoad();
    abstract protected int onCellIndexLoad();


    protected String getTableElement(String context){

        Matcher m = getPattern().matcher((String) context);
        AbstractParser.Utills.triggerMacherMethodFindNTimes(m, 1);
        return m.group(1);
    }
    protected String getRawRow(String tableElement,int rowIndex){
        Matcher rowMatcher = getRowPattern().matcher(tableElement);
        AbstractParser.Utills.triggerMacherMethodFindNTimes(rowMatcher, rowIndex);
        return rowMatcher.group(0);
    }
    protected String getCell(String rawRow,int cellIndex){
        Matcher cellMacher = getCellPattern().matcher(rawRow);
        AbstractParser.Utills.triggerMacherMethodFindNTimes(cellMacher, cellIndex);
        return cellMacher.group(2);
    }
    //override
    abstract protected String cellToValue(String cell);
    @Override
    protected Double onValueExtract(Object context) throws InvalidSourceArchitectureException {


        String actualTable = getTableElement((String)context); //getTable
        String row = getRawRow(actualTable,onRowIndexLoad());
        return Double.valueOf(cellToValue(getCell(row,onCellIndexLoad())));


    }
}
