package com.noreasonexception.datanuke.app.factory;

import com.noreasonexception.datanuke.app.dataProvider.DataProvider;
import com.noreasonexception.datanuke.app.threadRunner.AbstractThreadRunner;

import java.io.IOException;

abstract public class DataNukeAbstractFactory {
    abstract public AbstractThreadRunner    getThreadRunner();
    abstract public DataProvider            getThreadRunnersConfigProvider() throws IOException;
    abstract public DataProvider            getThreadRunnersSourceProvider() throws IOException;
    abstract public DataProvider            getDataNukeCustomClassLoaderDataProvider() throws IOException;

    abstract public ClassLoader             getDataNukeCustomClassLoader();


}
