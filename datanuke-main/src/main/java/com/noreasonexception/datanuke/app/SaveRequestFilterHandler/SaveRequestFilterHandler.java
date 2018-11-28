package com.noreasonexception.datanuke.app.SaveRequestFilterHandler;

import com.noreasonexception.datanuke.app.SaveRequestFilterHandler.error.CsvValueFilterException;
import com.noreasonexception.datanuke.app.SaveRequestFilterHandler.error.CsvValueFilterInconsistentStateException;

/****
 * This interface will be implemented by Save subsystem .

 * @param <T> the type of value to save
 */
public interface SaveRequestFilterHandler<T extends Comparable>  {
    /****
     * submits a new value to the filter
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
     * TODO remove it , consider creating a builder instead
     * @return this object
     * @throws CsvValueFilterException in case of any error(IOE or corrupted file)
     */
    abstract public SaveRequestFilterHandler<Double> buildFromFile() throws CsvValueFilterException;

    /***
     * this method covers the new event - same value exception . create a new file with a new timestamp , identical to previous one!
     * @return true on success
     * @throws CsvValueFilterException in case of any error(IOE probably)
     */
    abstract public boolean sameValueSituation(String klassName) throws CsvValueFilterException;
}
