package com.noreasonexception.datanuke.app;
import com.noreasonexception.datanuke.app.factory.DataNukeDefaultFactory;
import com.noreasonexception.datanuke.app.threadRunner.AbstractThreadRunner;
import com.noreasonexception.datanuke.app.threadRunner.ThreadRunnerStateListener;

import com.noreasonexception.datanuke.app.threadRunner.ThreadRunnerTaskListener;
import javafx.application.Application;
import javafx.stage.Stage;

import static javafx.application.Application.launch;


public class App extends Application {
    public static void main(String[] args) throws Exception {
        launch(args);

    }

    @Override
    public void init() throws Exception {
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
            public void onTaskThreadTerminated(String classname,Object ...e) {
                System.out.println("terminated");
            }

            @Override
            public void onTaskThreadReleased(String classname,Object...e) {
                System.out.println("released");
            }

            @Override
            public void onClassReadInfo(String classname,Object...e) {
                System.out.println(classname+" loaded!");
            }
        });

        new Thread(runner).start();
    }

    @Override
    public void stop() throws Exception {
        super.stop();
    }

    @Override
    public void start(Stage primaryStage) throws Exception {

    }
}
