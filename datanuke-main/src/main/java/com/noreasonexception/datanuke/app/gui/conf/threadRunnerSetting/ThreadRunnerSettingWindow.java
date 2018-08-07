package com.noreasonexception.datanuke.app.gui.conf.threadRunnerSetting;


import com.noreasonexception.datanuke.app.factory.DataNukeAbstractFactory;
import com.noreasonexception.datanuke.app.gui.conf.threadRunnerSetting.dialogs.SaveDialog;
import com.noreasonexception.datanuke.app.gui.factory.DataNukeAbstractGuiFactory;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class ThreadRunnerSettingWindow extends Application {
    private DataNukeAbstractGuiFactory parentFactory=null;
    BorderPane saveMenuPane;
    Node menuNode;
    ThreadRunnerSettingView settingView;
    public ThreadRunnerSettingWindow(DataNukeAbstractGuiFactory parentFactory) {
        this.parentFactory=parentFactory;

    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        primaryStage.getIcons().add(new Image("file:logos/faviconhd.png"));
        VBox box=new VBox();
        box.getChildren().add(this.settingView=new ThreadRunnerSettingView(parentFactory));
        box.getChildren().add(new Separator());
        box.getChildren().add(this.menuNode=getMenuNode());
        box.getChildren().add(new Separator());

        primaryStage.setScene(new Scene(box,400,200));
        primaryStage.show();
    }
    public Node getMenuNode(){
        Button button;
        this.saveMenuPane=new BorderPane();
        HBox box=new HBox();
        box.getChildren().add(button=new Button("Save"));
        button.setOnAction(getSaveButtonHandler());
        box.getChildren().add(new Separator());
        box.getChildren().add(new Button("Cancel"));
        box.getChildren().add(new Separator());

        saveMenuPane.setRight(box);
        return saveMenuPane;
    }
    public EventHandler<ActionEvent> getSaveButtonHandler(){
        return new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                Alert alert= new SaveDialog();

                alert.show();
            }
        };
    }
}
