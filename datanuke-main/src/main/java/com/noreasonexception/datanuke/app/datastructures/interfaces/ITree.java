package com.noreasonexception.datanuke.app.datastructures.interfaces;

/***
 * Interface implemented from any data structure that might be used in AbstractThreadRunner
 * @param <Key> the key type of variable
 * @param <Value> the value type of variable
 */
public interface ITree<Key,Value> {
    /***
     * Inserts a new <Key,Value> entry into data structure
     * @param key        the key to insert
     * @param value      the value to insert
     * @throws java.security.InvalidParameterException in case of 1) already exist
     */
    public void     insert  (Key key,Value value);

    /**
     * Deletes the entry described by @param key
     * @param key the key describing the entry
     * @throws java.security.InvalidParameterException in case of not found!
     */
    public void     delete  (Key key);

    /****
     * Search the entry described by key @param key
     * @param key the key describing the entry
     * @return the value of the entry
     * @throws java.security.InvalidParameterException in case of not found!
     */
    public Value    search  (Key key);

    /***
     * Removes and returns the entry with the smallest key
     * @return the value of the smallest entry in data structure
     */
    public Value    pollMin ();
}
