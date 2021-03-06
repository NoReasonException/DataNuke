package com.noreasonexception.datanuke.app.gui.conf.threadRunnerSetting;


import com.noreasonexception.datanuke.app.gui.dialog.SaveDialog;
import com.noreasonexception.datanuke.app.gui.etc.SaveOrCancelNode;
import com.noreasonexception.datanuke.app.gui.factory.DataNukeAbstractGuiFactory;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

abstract public class SettingWindow extends Application {
    private DataNukeAbstractGuiFactory parentFactory=null;
    SettingView settingView;
    Node node;
    public SettingWindow(DataNukeAbstractGuiFactory parentFactory) {
        this.parentFactory=parentFactory;

    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        primaryStage.getIcons().add(new Image("file:logos/faviconhd.png"));
        VBox box=new VBox();
        box.getChildren().add(this.settingView=onSettingViewGet());
        box.getChildren().add(new Separator());
        box.getChildren().add(node=getMenuNode(primaryStage));

        box.getChildren().add(new Separator());

        primaryStage.setScene(new Scene(box,400,200));
        primaryStage.show();
    }
    public Node getMenuNode(Stage parentStage){
        return new SaveOrCancelNode(parentStage) {
            @Override
            public EventHandler<ActionEvent> getSaveButtonHandler() {
                return new EventHandler<ActionEvent>() {
                    @Override
                    public void handle(ActionEvent event) {
                        new SaveDialog().show();
                    }
                };
            }

            @Override
            public EventHandler<ActionEvent> getCancelButtonHandler(Stage parentStage) {
                return new EventHandler<ActionEvent>() {
                    private Stage parentStage=null;
                    @Override
                    public void handle(ActionEvent event) {
                        parentStage.close();
                    }
                    public EventHandler<ActionEvent> init(Stage stage){
                        this.parentStage=stage;
                        return this;
                    }
                }.init(parentStage);
            }
        };
    }

    abstract protected SettingView onSettingViewGet();

    protected DataNukeAbstractGuiFactory getParentFactory() {
        return parentFactory;
    }
}
