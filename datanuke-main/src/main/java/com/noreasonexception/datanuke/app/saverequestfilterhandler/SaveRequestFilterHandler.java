package com.noreasonexception.datanuke.app.saverequestfilterhandler;

import com.noreasonexception.datanuke.app.saverequestfilterhandler.error.GenericSaveRequestFilterException;
import com.noreasonexception.datanuke.app.saverequestfilterhandler.error.InconsistentStateException;

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
    abstract public boolean submitValue(String klassName,T value) throws GenericSaveRequestFilterException;
    /****
     * Submit a class by his name before use
     *
     * @param klassName the class name
     * @return  true for success
     * @throws InconsistentStateException
     */
    abstract public boolean submitClass(String klassName) throws InconsistentStateException;
    /****
     * Call always before any operation , otherwise , a InconsistentStateException will be thrown.
     * TODO remove it , consider creating a builder instead
     * @return this object
     * @throws GenericSaveRequestFilterException in case of any error(IOE or corrupted file)
     */
    abstract public SaveRequestFilterHandler<Double> buildFromFile() throws GenericSaveRequestFilterException;

    /***
     * this method covers the new event - same value exception . create a new file with a new timestamp , identical to previous one!
     * @return true on success
     * @throws GenericSaveRequestFilterException in case of any error(IOE probably)
     */
    abstract public boolean sameValueSituation(String klassName) throws GenericSaveRequestFilterException;
}
