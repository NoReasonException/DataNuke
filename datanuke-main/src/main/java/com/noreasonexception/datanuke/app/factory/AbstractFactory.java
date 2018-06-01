package com.noreasonexception.datanuke.app.factory;

import com.noreasonexception.datanuke.app.dataProvider.DataProvider;
import com.noreasonexception.datanuke.app.threadRunner.AbstractThreadRunner;

abstract public class AbstractFactory {
    abstract public AbstractThreadRunner getThreadRunner();
    abstract public DataProvider         getFileDataProvider();
    abstract public DataProvider         getNetworkDataProvider();
}
