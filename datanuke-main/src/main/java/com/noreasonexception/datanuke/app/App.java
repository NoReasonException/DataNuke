package com.noreasonexception.datanuke.app;
import com.noreasonexception.datanuke.app.factory.DataNukeDefaultFactory;
import com.noreasonexception.datanuke.app.gui.Menu.MainMenu;
import com.noreasonexception.datanuke.app.threadRunner.AbstractThreadRunner;
import com.noreasonexception.datanuke.app.threadRunner.ThreadRunnerStateListener;

import com.noreasonexception.datanuke.app.threadRunner.ThreadRunnerTaskListener;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

import java.util.Date;

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

            @Override
            public void onClassWaitUntillDeadline(String classname, Object[] args) {
                System.out.println("ON CLASS WAIT"+new Date((Long)args[0]));
            }
        });

        Thread a=new Thread(runner);
        a.start();
    }

    @Override
    public void stop() throws Exception {
        super.stop();
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        BorderPane p = new BorderPane();
        p.setTop(new MainMenu());
        primaryStage.setScene(new Scene(p,200,200));
        primaryStage.show();
        primaryStage.setMinHeight(350);
        primaryStage.setMinWidth(350);
    }
}
