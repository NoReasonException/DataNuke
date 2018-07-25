package com.noreasonexception.datanuke.app;
import com.noreasonexception.datanuke.app.factory.DataNukeAbstractFactory;
import com.noreasonexception.datanuke.app.factory.DataNukeDefaultFactory;
import com.noreasonexception.datanuke.app.gui.Factory.DataNukeAbstractGuiFactory;
import com.noreasonexception.datanuke.app.gui.Factory.DataNukeDefaultGuiFactory;
import com.noreasonexception.datanuke.app.gui.LeftBorder.ClassesTable;
import com.noreasonexception.datanuke.app.gui.Menu.MainMenu;
import com.noreasonexception.datanuke.app.threadRunner.AbstractThreadRunner;
import com.noreasonexception.datanuke.app.threadRunner.ThreadRunnerStateListener;

import com.noreasonexception.datanuke.app.threadRunner.ThreadRunnerTaskListener;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

import java.util.Date;

import static javafx.application.Application.launch;


public class App extends Application {
    DataNukeAbstractFactory coreFactory=null;
    DataNukeAbstractGuiFactory guiFactory=null;
    public static void main(String[] args) throws Exception {
        launch(args);

    }

    @Override
    public void init() throws Exception {
        coreFactory=new DataNukeDefaultFactory().loadDefaultConfiguration();
        guiFactory=new DataNukeDefaultGuiFactory(coreFactory);
    }

    @Override
    public void stop() throws Exception {
        super.stop();
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        BorderPane p = new BorderPane();
        Scene scene;
        primaryStage.setMinHeight(350);
        primaryStage.setMinWidth(350);
        p.setTop(guiFactory.getTopBorder());
        p.setLeft(guiFactory.getLeftBorder());
        primaryStage.setScene(scene=new Scene(p,600,400));
        primaryStage.setTitle("DataNuke");
        primaryStage.getIcons().add(new Image("file:logos/faviconhd.png"));
        primaryStage.show();
        new Thread(coreFactory.getThreadRunner()).start();
    }
}
