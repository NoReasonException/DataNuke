package com.noreasonexception.datanuke.app.factory;

import com.noreasonexception.datanuke.app.dataProvider.DataProvider;
import com.noreasonexception.datanuke.app.dataProvider.FileDataProvider;
import com.noreasonexception.datanuke.app.threadRunner.AbstractThreadRunner;

import java.io.IOException;
import java.nio.file.Paths;

public class DataNukeDefaultFactoryDataNuke extends DataNukeAbstractFactory {
    private java.lang.String ThreadRunnerConfigFile;
    private java.lang.String ThreadRunnerSourcesFile;
    private java.lang.String ThreadRunnerCustomClassLoaderDataProvider;


    @Override
    public AbstractThreadRunner getThreadRunner() {
        return null;
    }

    @Override
    public DataProvider getThreadRunnersConfigProvider() throws IOException {
        return new FileDataProvider(Paths.get("conf/threadrunnerconf.json"),300);
    }

    @Override
    public DataProvider getThreadRunnersSourceProvider() throws IOException {
        return new FileDataProvider(Paths.get("conf/threadrunnersource.json"),300);
    }

    @Override
    public ClassLoader getDataNukeCustomClassLoader() {
        return null;
    }

    @Override
    public DataProvider getDataNukeCustomClassLoaderDataProvider() {
        return null;
    }

    public DataNukeDefaultFactoryDataNuke() {
    }
}
