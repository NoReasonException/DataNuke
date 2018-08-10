package com.noreasonexception.datanuke.app.factory;

import com.noreasonexception.datanuke.app.ValueFilter.AbstractValueFilter;
import com.noreasonexception.datanuke.app.ValueFilter.CsvValueFilter;
import com.noreasonexception.datanuke.app.ValueFilter.error.CsvValueFilterException;
import com.noreasonexception.datanuke.app.classloader.AtlasLoader;
import com.noreasonexception.datanuke.app.dataProvider.DataProvider;
import com.noreasonexception.datanuke.app.dataProvider.FileDataProvider;
import com.noreasonexception.datanuke.app.factory.error.MissingResourcesException;
import com.noreasonexception.datanuke.app.threadRunner.AbstractThreadRunner;

import javax.json.Json;
import javax.json.JsonObject;
import javax.json.JsonReader;
import java.io.IOException;
import java.io.StringReader;
import java.nio.ByteBuffer;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.NoSuchElementException;
import java.util.Random;

public class DataNukeDefaultFactory extends DataNukeAbstractFactory {
    ///Configuration Section
    private static final java.lang.String THREAD_RUNNER_CONFIG_FILE_DEFAULT                ="src/main/conf/threadRunnerConf.json";
    private static final java.lang.String THREAD_RUNNER_SOURCES_FILE_DEFAULT               ="src/main/conf/threadRunnerSources.json";
    private static final java.lang.String DATA_NUKE_CLASS_LOADER_DEFAULT_PATH              ="src/main/conf/dataNukeClassLoaderDir/";
    public static  final java.lang.String DATA_NUKE_DEFAULT_FACTORY_CONF_FILE_DEFAULT      ="src/main/conf/dataNukeDefaultFactoryConf.json/";
    public static  final java.lang.String CSV_VALUE_FILTER_FILE_PATH_DEFAULT               ="dat.csv";
    ///===========================Configuration================================================\\\
    private java.lang.String threadRunnerConfigFile                                =null;
    private java.lang.String threadRunnerSourcesFile                               =null;
    private java.lang.String customClassLoaderPATH                                 =null;
    private java.lang.String csvValueFilterFile                                    =null;
    //===========================Siglentons======================================================\\\
    private AbstractValueFilter<Double> valueFilter                                =null;
    private AtlasLoader                 customClassLoader                          =null;
    private AbstractThreadRunner        threadRunner                               =null;
    /****
     * loadConfiguration
     * This method loads the configurations needed to start the factory and pass in every subsystem
     * the proper configuration file and e.t.c
     * Please use the .loadDefaultConfiguration()
     * @param confProvider the data provider
     * @return this object
     */
    public DataNukeDefaultFactory loadConfiguration(DataProvider confProvider){
        StringBuilder stringBuilder = new StringBuilder();
        try{
            ByteBuffer buff=(ByteBuffer)confProvider.provide().get();
            for (int i = 0;  i<buff.limit(); i++) {
                stringBuilder.append((char)buff.get());
            }

            JsonReader reader = Json.createReader(new StringReader(stringBuilder.toString()));
            JsonObject object=reader.readObject();
            object.getString("customClassLoaderPATH");
            if((customClassLoaderPATH=object.getString("customClassLoaderPATH"))==null){
                throw new NoSuchElementException();
            }
            else if((threadRunnerConfigFile=object.getString("threadRunnerConfigFile"))==null){
                throw new NoSuchElementException();
            }
            else if((threadRunnerSourcesFile=object.getString("threadRunnerSourcesFile"))==null){
                throw new NoSuchElementException();
            }
            else if((csvValueFilterFile=object.getString("CsvValueFilterFileDestination"))==null){
                throw new NoSuchElementException();
            }
            System.out.println(csvValueFilterFile);

        }catch (NoSuchElementException e){
            threadRunnerConfigFile=THREAD_RUNNER_CONFIG_FILE_DEFAULT;
            threadRunnerSourcesFile=THREAD_RUNNER_SOURCES_FILE_DEFAULT;
            customClassLoaderPATH=DATA_NUKE_CLASS_LOADER_DEFAULT_PATH;
            csvValueFilterFile=CSV_VALUE_FILTER_FILE_PATH_DEFAULT;
        }
        return this;

    }
    public DataNukeDefaultFactory loadDefaultConfiguration()throws IOException{
        Path p;
        return loadConfiguration(
                getFactoryConfigProvider());

    }
    @Override
    public AbstractThreadRunner getThreadRunner() throws MissingResourcesException {
        try{
            return threadRunner!=null?
                    threadRunner:
                    (threadRunner=new AbstractThreadRunner(
                    getDataNukeCustomClassLoader(),
                    getThreadRunnersConfigProvider(),
                    getThreadRunnersSourceProvider(),
                    getDataNukeValueFilter(),
                    getThreadRunnersRandomGenerator()));
        }catch (IOException|CsvValueFilterException e){
            throw new MissingResourcesException(e);
        }catch (Exception e){
            throw new MissingResourcesException(e); //TODO fix
        }
    }

    @Override
    public DataProvider getThreadRunnersConfigProvider() throws IOException {
        Path p;
        return new FileDataProvider((p=Paths.get(threadRunnerConfigFile)));
    }

    @Override
    public DataProvider getFactoryConfigProvider() throws IOException {
        return new FileDataProvider(Paths.get(DATA_NUKE_DEFAULT_FACTORY_CONF_FILE_DEFAULT));
    }

    @Override
    public DataProvider getThreadRunnersSourceProvider() throws IOException {
        Path p;
        return new FileDataProvider(p=Paths.get(threadRunnerSourcesFile));
    }

    @Override
    public AtlasLoader getDataNukeCustomClassLoader() {
        return customClassLoader!=null?
                customClassLoader:(customClassLoader=AtlasLoader.getInstance());
    }

    @Override
    public DataProvider getDataNukeCustomClassLoaderDataProvider() throws IOException{
        return new FileDataProvider(Paths.get(customClassLoaderPATH));
    }

    @Override
    public AbstractValueFilter<Double> getDataNukeValueFilter() throws CsvValueFilterException {
        return (this.valueFilter!=null)?
                (this.valueFilter):
                (this.valueFilter=(new CsvValueFilter(this.csvValueFilterFile).buildFromFile()));
    }

    @Override
    public String getThreadRunnersConfigProviderFile() {
        return threadRunnerConfigFile;
    }

    @Override
    public String getThreadRunnersSourceProviderFile() {
        return threadRunnerSourcesFile;
    }

    @Override
    public Random getThreadRunnersRandomGenerator() throws Exception {
        return new Random();
    }

}
