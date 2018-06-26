package com.noreasonexception.datanuke.app;

import com.noreasonexception.datanuke.app.ValueFilter.CsvValueFilter;
import com.noreasonexception.datanuke.app.factory.DataNukeDefaultFactory;
import com.noreasonexception.datanuke.app.threadRunner.AbstractThreadRunner;
import com.noreasonexception.datanuke.app.threadRunner.ThreadRunnerStateListener;
import com.noreasonexception.datanuke.app.threadRunner.ThreadRunnerTaskListener;

import java.io.IOException;
import java.lang.reflect.Method;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;

/**
 * Hello world!
 *
 */
public class App {
    public static void main(String[] args) throws Exception {
        //System.out.println(System.getProperty("user.dir"));
        AbstractThreadRunner runner;
        runner = new DataNukeDefaultFactory().loadDefaultConfiguration().getThreadRunner();
        runner.subscribeStateListener(new ThreadRunnerStateListener() {
            @Override
            public void run() {
                //System.out.println(getState().getMessage());
            }
        });
        runner.subscribeTaskListener(new ThreadRunnerTaskListener() {
            @Override
            public void onTaskThreadTerminated(String classname) {
                System.out.println("terminated");
            }

            @Override
            public void onTaskThreadReleased(String classname) {
                System.out.println("released");
            }
        });

        new Thread(runner).start();
    }



}
