package com.noreasonexception.datanuke.app.factory;

import com.noreasonexception.datanuke.app.classloader.DataNukeCustomClassLoader;
import com.noreasonexception.datanuke.app.dataProvider.DataProvider;
import com.noreasonexception.datanuke.app.dataProvider.FileDataProvider;
import com.noreasonexception.datanuke.app.threadRunner.AbstractThreadRunner;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.NoSuchElementException;

public class DataNukeDefaultFactory extends DataNukeAbstractFactory {
    ///Configuration Section
    private final java.lang.String THREAD_RUNNER_CONFIG_FILE_DEFAULT                ="conf/threadRunnerConf.json";
    private final java.lang.String THREAD_RUNNER_SOURCES_FILE_DEFAULT               ="conf/threadRunnerSources.json";
    private final java.lang.String DATA_NUKE_CLASS_LOADER_DEFAULT_PATH              ="conf/dataNukeClassLoaderDir/";
    private final java.lang.String DATA_NUKE_DEFAULT_FACTORY_CONF_FILE_DEFAULT      ="conf/dataNukeDefaultFactoryConf.json/";

    private java.lang.String threadRunnerConfigFile                     =null;
    private java.lang.String threadRunnerSourcesFile                    =null;
    private java.lang.String customClassLoaderPATH                      =null;
    private ClassLoader      customClassLoader                          =null;


    public DataNukeDefaultFactory loadConfiguration(DataProvider confProvider){
        StringBuilder stringBuilder = new StringBuilder();
        try{
            ByteBuffer buff=(ByteBuffer)confProvider.provide().get();
            System.out.println(buff.toString());

        }catch (NoSuchElementException e){
            threadRunnerConfigFile="conf/threadRunnerConf.json";
            threadRunnerSourcesFile="conf/threadRunnerSources.json";
            customClassLoaderPATH="conf/dataNukeClassLoaderDir/";

        }
        return this;

    }
    public DataNukeDefaultFactory loadDefaultConfiguration()throws IOException{
        Path p;
        return loadConfiguration(
                new FileDataProvider(p=Paths.get(DATA_NUKE_DEFAULT_FACTORY_CONF_FILE_DEFAULT),
                Files.size(p)));

    }
    @Override
    public AbstractThreadRunner getThreadRunner() {
        return null;
    }

    @Override
    public DataProvider getThreadRunnersConfigProvider() throws IOException {
        Path p;
        return new FileDataProvider((p=Paths.get(threadRunnerConfigFile)), Files.size(p));
    }

    @Override
    public DataProvider getThreadRunnersSourceProvider() throws IOException {
        return new FileDataProvider(Paths.get(threadRunnerSourcesFile),300);
    }

    @Override
    public ClassLoader getDataNukeCustomClassLoader() {
        return customClassLoader!=null?
                customClassLoader:(customClassLoader=new DataNukeCustomClassLoader());
    }

    @Override
    public DataProvider getDataNukeCustomClassLoaderDataProvider() throws IOException{
        return new FileDataProvider(Paths.get(customClassLoaderPATH),300);
    }
}
