package com.noreasonexception.datanuke.app;

import com.noreasonexception.datanuke.app.factory.DataNukeDefaultFactory;
import com.noreasonexception.datanuke.app.threadRunner.AbstractThreadRunner;
import com.noreasonexception.datanuke.app.threadRunner.ThreadRunnerStateListener;
import com.noreasonexception.datanuke.app.threadRunner.ThreadRunnerTaskListener;

import java.io.IOException;
import java.lang.reflect.Method;

/**
 * Hello world!
 *
 */
public class App {
    public static void main(String[] args) throws IOException {
        System.out.println(System.getProperty("user.dir"));
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
            public void onClassReadInfo(String classname) {
                System.out.println("onClassReadInfo " + classname + classname.length());
            }

            @Override
            public void onClassWaitUntillDeadline(String classname) {
                System.out.println("onClassWaitUntillDeadline " + classname);

            }

            @Override
            public void onClassLoading(String classname) {
                System.out.println("onClassLoading " + classname);

            }

            @Override
            public void onClassInstanceCreated(String classname) {
                System.out.println("onClassInstanceCreated " + classname);

            }

            @Override
            public void onTaskThreadStarted(String classname) {
                System.out.println("onTaskThreadStarted " + classname);

            }

            @Override
            public void onTaskThreadValueRetrieved(String classname) {
                System.out.println("onTaskThreadValueRetrieved " + classname);

            }

            @Override
            public void onTaskThreadTerminated(String classname) {
                System.out.println("onTaskThreadTerminated " + classname);
            }

            @Override
            public void onTaskThreadReleased(String classname) {
            }

            @Override
            public void onClassReleased(String classname) {
                System.out.println("onClassReleased " + classname);
            }
        });
        new Thread(runner).start();
    }


}
