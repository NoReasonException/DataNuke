package com.noreasonexception.datanuke.app.ValueFilter;

/****
 * This interface will be implemented by the save subsystem . only a value per class
 *
 * @param <T> the type of value to save
 */
@FunctionalInterface
public interface ValueFilterable<T extends Comparable>  {
    /****
     * submits a new value to Filter
     * @param classObj the class submitted the value
     * @param value the actual value
     * @return true if submit is completed(old!=new) else false
     */
    boolean submitValue(Class<?> classObj,T value) throws Exception;
}
