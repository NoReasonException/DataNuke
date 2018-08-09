package com.noreasonexception.datanuke.app.gui.leftBorder.dialogs;

import com.noreasonexception.datanuke.app.threadRunner.etc.ClassInfo;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class ClassInfoDialog extends Application {
    ClassInfo info=null;
    @Override
    public void start(Stage primaryStage) throws Exception {
        VBox box= new VBox();
        box.getChildren().add(new Label(info.getID()));
        box.getChildren().add(new Label(info.getClassname()));
        box.getChildren().add(new Label(String.valueOf(info.getInterval())));
        box.getChildren().add(new Label(info.getDate().toString()));
        primaryStage.setScene(new Scene(box,200,200));
        primaryStage.show();


    }
    public ClassInfoDialog setClassInfo(ClassInfo info){
        this.info=info;
        return this;
    }

}
