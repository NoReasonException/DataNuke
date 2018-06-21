package com.noreasonexception.datanuke.app.factory;

import com.noreasonexception.datanuke.app.ValueFilter.CsvValueFilter;
import com.noreasonexception.datanuke.app.ValueFilter.error.CsvValueFilterException;
import com.noreasonexception.datanuke.app.dataProvider.DataProvider;
import com.noreasonexception.datanuke.app.factory.error.MissingResourcesException;
import com.noreasonexception.datanuke.app.threadRunner.AbstractThreadRunner;

import java.io.IOException;

abstract public class DataNukeAbstractFactory {
    abstract public AbstractThreadRunner    getThreadRunner() throws MissingResourcesException;
    abstract public DataProvider            getThreadRunnersConfigProvider() throws IOException;
    abstract public DataProvider            getThreadRunnersSourceProvider() throws IOException;
    abstract public DataProvider            getDataNukeCustomClassLoaderDataProvider() throws IOException;
    abstract public ClassLoader             getDataNukeCustomClassLoader();
    abstract public CsvValueFilter          getDataNukeCSVvalueFilter() throws CsvValueFilterException;


}
