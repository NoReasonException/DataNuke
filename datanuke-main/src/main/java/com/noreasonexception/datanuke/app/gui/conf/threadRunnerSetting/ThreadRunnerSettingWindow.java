package com.noreasonexception.datanuke.app.gui.conf.threadRunnerSetting;


import com.noreasonexception.datanuke.app.factory.DataNukeAbstractFactory;
import com.noreasonexception.datanuke.app.gui.factory.DataNukeAbstractGuiFactory;
import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class ThreadRunnerSettingWindow extends Application {
    private DataNukeAbstractGuiFactory parentFactory=null;
    public ThreadRunnerSettingWindow(DataNukeAbstractGuiFactory parentFactory) {
        this.parentFactory=parentFactory;

    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        primaryStage.getIcons().add(new Image("file:logos/faviconhd.png"));
        primaryStage.setScene(new Scene(new ThreadRunnerSettingView(parentFactory),500,250));
        primaryStage.show();
    }
}
