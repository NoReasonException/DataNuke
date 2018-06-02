package com.noreasonexception.datanuke.app;

import com.noreasonexception.datanuke.app.dataProvider.FileDataProvider;
import com.noreasonexception.datanuke.app.factory.DataNukeDefaultFactory;
import com.noreasonexception.datanuke.app.threadRunner.AbstractThreadRunner;
import com.noreasonexception.datanuke.app.threadRunner.ThreadRunnerListener;

import java.io.IOException;
import java.nio.file.Paths;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args )throws IOException
    {
        AbstractThreadRunner runner;
        new Thread(runner=new DataNukeDefaultFactory().loadDefaultConfiguration().getThreadRunner()).start();
        runner.subscribeListener(new ThreadRunnerListener() {
            @Override
            public void run() {
                System.out.println(getState().getMessage());
            }
        });
    }

}
