package com.noreasonexception.datanuke.app.ValueFilter;

/****
 * This interface will be implemented by Save subsystem .
 * How it works?
 * This Interface , has only one method . the .submitValue()
 * This method updates the value associated with the classname given
 *
 * If the value is different , then automatically will saved in file , in the expected position .
 *
 * @param <T> the type of value to save
 */
@FunctionalInterface
public interface ValueFilterable<T extends Comparable>  {
    /****
     * submits a new value to Filter
     * @param className the class submitted the value
     * @param value the actual value
     * @return true if submit is completed(old!=new) else false
     */
    boolean submitValue(String className,T value) throws Exception;
}
