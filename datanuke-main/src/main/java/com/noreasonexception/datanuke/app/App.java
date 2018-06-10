package com.noreasonexception.datanuke.app;

import com.noreasonexception.datanuke.app.classloader.singleClassClassLoader.SingleClassLoader;
import com.noreasonexception.datanuke.app.dataProvider.FileDataProvider;
import com.noreasonexception.datanuke.app.factory.DataNukeDefaultFactory;
import com.noreasonexception.datanuke.app.threadRunner.AbstractThreadRunner;
import com.noreasonexception.datanuke.app.threadRunner.ThreadRunnerListener;
import com.noreasonexception.datanuke.app.threadRunner.ThreadRunnerState;

import java.awt.*;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args )throws IOException
    {

        /*AbstractThreadRunner runner;
        runner=new DataNukeDefaultFactory().loadDefaultConfiguration().getThreadRunner();
        runner.subscribeListener(new ThreadRunnerListener() {
            @Override
            public void run() {
                //System.out.println(getState().getMessage());
            }
        });
        new Thread(runner).start();*/
        System.out.println(SingleClassLoader.verifyClassNamespace("com.noreasonexception.loadable.childs.TestClass"));
    }

}
