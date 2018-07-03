package com.noreasonexception.datanuke.app.factory;

import com.noreasonexception.datanuke.app.ValueFilter.AbstractValueFilter;
import com.noreasonexception.datanuke.app.ValueFilter.error.CsvValueFilterException;
import com.noreasonexception.datanuke.app.dataProvider.DataProvider;
import com.noreasonexception.datanuke.app.factory.error.MissingResourcesException;
import com.noreasonexception.datanuke.app.threadRunner.AbstractThreadRunner;

import java.io.IOException;
import java.util.Random;

/****
 * The main factory class
 * Every major subsystem is enforced to created using this class.
 * Also dont forget to use DI -> DONT create your own depedencies
 */
abstract public class DataNukeAbstractFactory {
    /***
     * Constructs the Main Thread Runner
     * @return a AbstractThreadRunner Object
     * @throws MissingResourcesException
     */
    abstract public AbstractThreadRunner    getThreadRunner() throws MissingResourcesException;

    /***
     * Constructs and returns the main thread runner's configuration data provider
     * Basically is a thread runners depedency , you probably never call it directly
     * @return a @see DataProvider
     * @throws IOException in case of any IOException
     */
    abstract public DataProvider            getThreadRunnersConfigProvider() throws IOException;

    /***
     * Constructs and returns the main thread runner's source data provider
     * Basically , as we already saw with .getThreadRunnersConfigProvider , this is a depedency of
     * AbstractThreadRunner , so you probably never call it directly
     * @return a @see DataProvider
     * @throws IOException
     */
    abstract public DataProvider            getThreadRunnersSourceProvider() throws IOException;

    abstract public Random                  getThreadRunnersRandomGenerator() throws Exception;

    /***
     * Constructs and returns the DataNukesCustomClassLoaderDataProvider , is also an depedency
     * (of .getDataNukeCustomClassLoader())so will probably never call it directly;
     * @return a @see DataProvider
     * @throws IOException
     */
    abstract public DataProvider            getDataNukeCustomClassLoaderDataProvider() throws IOException;

    /***
     * Constructs and returns the dynamic classloader (with remove capability)
     * @return a @see ClassLoader Object
     */
    abstract public ClassLoader             getDataNukeCustomClassLoader();

    /****
     * Constructs and returns the ValueFilter subsystem , responsible for writing the values into an external source
     * @return a @CsvValueFilter
     * @throws CsvValueFilterException
     */
    abstract public AbstractValueFilter<Double> getDataNukeValueFilter() throws CsvValueFilterException;


}
