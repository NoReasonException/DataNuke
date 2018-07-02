package com.noreasonexception.datanuke.app;
import com.noreasonexception.datanuke.app.factory.DataNukeDefaultFactory;
import com.noreasonexception.datanuke.app.threadRunner.AbstractThreadRunner;
import com.noreasonexception.datanuke.app.threadRunner.ThreadRunnerStateListener;
import com.noreasonexception.datanuke.app.threadRunner.ThreadRunnerTaskListener;

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

            @Override
            public void onClassReadInfo(String classname) {
                System.out.println(classname+"loaded!");
            }
        });

        new Thread(runner).start();
    }



}
