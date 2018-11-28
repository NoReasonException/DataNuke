package com.noreasonexception.datanuke.app.ValueFilter;

import com.noreasonexception.datanuke.app.ValueFilter.error.CsvValueFilterException;
import com.noreasonexception.datanuke.app.ValueFilter.error.CsvValueFilterInconsistentStateException;

/****
 * This class will be extended by Save subsystem .
 * How it works?
 * This class , has only one method . the .submitValue()
 * This method updates the value associated with the classname given
 *
 * If the value is different , then automatically will saved in file , in the expected position .
 *
 * @param <T> the type of value to save
 */
abstract public class AbstractValueFilter<T extends Comparable>  {
    /****
     * submits a new value to Filter
     * @param klassName the class submitted the value
     * @param value the actual value
     * @return true if submit is completed(old!=new) else false
     */
    abstract public boolean submitValue(String klassName,T value) throws CsvValueFilterException;
    /****
     * Submit a class by his name before use
     *
     * @param klassName the class name
     * @return  true for success
     * @throws CsvValueFilterInconsistentStateException
     */
    abstract public boolean submitClass(String klassName) throws CsvValueFilterInconsistentStateException;
    /****
     * Call always before any operation , otherwise , a CsvValueFilterInconsistentStateException will be thrown.
     * @return this object
     * @throws CsvValueFilterException in case of any error(IOE or corrupted file)
     */
    abstract public AbstractValueFilter<Double> buildFromFile() throws CsvValueFilterException;

    /***
     * this method covers the new event - same value exception . create a new file with a new timestamp , identical to previous one!
     * @return true on success
     * @throws CsvValueFilterException in case of any error(IOE probably)
     */
    abstract public boolean sameValueSituation(String klassName) throws CsvValueFilterException;
}
