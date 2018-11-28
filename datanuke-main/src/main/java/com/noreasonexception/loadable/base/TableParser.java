package com.noreasonexception.loadable.base;

import com.noreasonexception.datanuke.app.SaveRequestFilterHandler.SaveRequestFilterHandler;
import com.noreasonexception.datanuke.app.threadRunner.ThreadRunnerTaskEventsDispacher;
import com.noreasonexception.loadable.base.error.InvalidSourceArchitectureException;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/***
 * This is the Table Parser
 * Used to extract values from a standard html <table> ... </table> element
 * How it works?
 * consider we parse the following code, and we want to extract the value 50
 * <html>
 *     <...></...>
 *     <...></...>
 * <...>
 *     ...
 *     ...
 *<p>This is a fine table</p>     <ST_SIG==== These two lines is
 *<table style="width:100%">      <ST_SIG==== The Start Signature of the table
 *   <tr>                         <--  This is the first Row of this table, so .onIndexRowLoad() should return 0;
 *     <td>Jill</td>
 *     <td>Smith</td>
 *     <td>                             <~~This is the third row , the .onIndexCellLoad() must return 2;
 *         Age: 50                             <***The .getValuePattern must return "Age:(\\d)*? in order to get the 50"
 *     </td>                            <~~This is the end of 3rd row
 *   </tr>                        <--  End of the first row
 *   <tr>
 *     <td>Eve</td>
 *     <td>Jackson</td>
 *     <td>Age: 94</td>
 *   </tr>
 *  </table>                    <EN_SIG====  These two lines is
 *  <p>this is the end</p>      <EN_SIG====  The End Signature of this table
 * </...>
 * ...
 * ...
 *
 *
 *
 */
abstract public class TableParser extends HtmlParser {
    public TableParser(ThreadRunnerTaskEventsDispacher disp, SaveRequestFilterHandler<Double> valueFilter) {
        super(disp, valueFilter);
    }

    /***
     * Every HTML table has a <table> start element and a </table> end element .
     * in order to extract the right table , it is essential to provide some code before the start element(with the start element included)
     * and some code after the end element of the table(with </table> included
     * this is defined as Start and end signature of the table , and the pattern load does just that
     * @implNote please remember to specify the MULTILINE and the DOTALL flags if you want to override this
     * @return the Pattern object representing the table
     */
    @Override
    protected Pattern onPatternLoad() {
        return Pattern.compile(onTableSignatureStartLoad()+"(.*\\s)"+onTableSignatureEndLoad(),Pattern.MULTILINE|Pattern.DOTALL);
    }

    /***
     * Every client should override this to provide the start signature
     * (See the .onPatternLoad() comments for further info
     * @See onPatternLoad() method
     * @return the String representing the Start Signature of the table
     */
    abstract protected String onTableSignatureStartLoad();

    /***
     * Every client should override this to provide the end signature
     * (See the .opPattern() comments for further info)
     * @See onPatternLoad() method
     * @return the String representing the End Signature of the table
     */
    abstract protected String onTableSignatureEndLoad();

    /***
     * In Every standard HTML table the row pattern should be the same , but this is overridable ,just in case
     * @return a Standard Pattern for each row
     */
    protected Pattern getRowPattern(){
        return Pattern.compile("<tr.*>(.*)(</td>)");
    }
    /***
     * In Every standard HTML table the cell pattern should be the same , but this is overridable ,just in case
     * @return a Standard Pattern for each cell
     */
    protected Pattern getCellPattern(){
        return Pattern.compile("<td(.(?!<))*><((.(?!</td>))*)></td>",Pattern.DOTALL);
    }

    /***
     * This should be overridable in every client , this will provide the final Pattern to search inside The
     * the HTML's selected cell
     * @See the start of this file for further details
     *
     * @return a Pattern Object for the final search
     */
    abstract protected Pattern getValuePattern();

    /***
     * Provide the Row Index
     * Overridable in any client
     * @implNote do not call this method of your own , it will automatically called by the TableParser
     * @return the Row Index
     */
    abstract protected int onRowIndexLoad();
    /***
     * Provide the Cell Index
     * Overridable in any client
     * @implNote do not call this method of your own , it will automatically called by the TableParser
     * @return the Cell Index
     */
    abstract protected int onCellIndexLoad();


    /***
     * Gets the html source code and returns the Code inside the <table><</table>
     * @param context the whole html file
     * @return the <table> ... </table> code
     */
    protected String getTableElement(String context) throws  java.lang.IllegalStateException {

        Matcher m = getPattern().matcher((String) context);
        AbstractParser.Utills.triggerMacherMethodFindNTimes(m, 1);
        return m.group(1);
    }

    /***
     * returns the code inside the @param rowIndex row
     * @param tableElement the code inside the <table>...</table>
     * @param rowIndex the row index that you want
     * @return the code inside the <tr>...</tr>
     */
    protected String getRawRow(String tableElement,int rowIndex)  throws java.lang.IllegalStateException{
        Matcher rowMatcher = getRowPattern().matcher(tableElement);
        AbstractParser.Utills.triggerMacherMethodFindNTimes(rowMatcher, rowIndex);
        return rowMatcher.group(0);
    }

    /***
     * Returns the code inside the @cellIndex
     * @param rawRow the code inside the <tr>...</tr>
     * @param cellIndex the cell you want
     * @return the code inside the <td>...</td>
     */
    protected String getCell(String rawRow,int cellIndex) throws  java.lang.IllegalStateException{
        Matcher cellMacher = getCellPattern().matcher(rawRow);
        AbstractParser.Utills.triggerMacherMethodFindNTimes(cellMacher, cellIndex);
        return cellMacher.group(2);
    }


    /***
     * utillity of getting the needed value from the final cell
     * @param cell the code inside the <td>...</td>
     * @return the actual value
     */
    protected String cellToValue(String cell) throws  java.lang.IllegalStateException{
        Matcher matcher=getValuePattern().matcher(cell);
        AbstractParser.Utills.triggerMacherMethodFindNTimes(matcher,1);
        return matcher.group(1);
    }

    /***
     * Entry point of TableParser
     * @param context here the context is the cell code (inside <td>...</td>)
     * @return the final value
     * @throws InvalidSourceArchitectureException in case that something changed and the regular expressions is unable to detect the value
     */
    @Override
    protected Double onValueExtract(Object context) throws InvalidSourceArchitectureException {

        Double d;
        try{

            String actualTable = getTableElement((String)context); //getTable
            String row = getRawRow(actualTable,onRowIndexLoad());//get needed row
            System.out.println(d=Double.valueOf(cellToValue(getCell(row,onCellIndexLoad())))); //get the final value
        }catch (IllegalStateException e){e.printStackTrace();throw new InvalidSourceArchitectureException(getClass());}
        return d;

    }
}
