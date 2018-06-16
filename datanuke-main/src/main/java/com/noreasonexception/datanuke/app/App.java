package com.noreasonexception.datanuke.app;

import com.noreasonexception.datanuke.app.factory.DataNukeDefaultFactory;
import com.noreasonexception.datanuke.app.threadRunner.AbstractThreadRunner;
import com.noreasonexception.datanuke.app.threadRunner.ThreadRunnerStateListener;

import java.io.IOException;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args )throws IOException
    {
        System.out.println(System.getProperty("user.dir"));
        AbstractThreadRunner runner;
        runner=new DataNukeDefaultFactory().loadDefaultConfiguration().getThreadRunner();
        runner.subscribeStateListener(new ThreadRunnerStateListener() {
            @Override
            public void run() {
                //System.out.println(getState().getMessage());
            }
        });
        new Thread(runner).start();
    }


}
