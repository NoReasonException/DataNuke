package com.noreasonexception.datanuke.app.gui.leftBorder.dialogs;

import com.noreasonexception.datanuke.app.gui.conf.threadRunnerSetting.dialogs.SaveDialog;
import com.noreasonexception.datanuke.app.gui.etc.SaveOrCancelNode;
import com.noreasonexception.datanuke.app.gui.factory.DataNukeAbstractGuiFactory;
import com.noreasonexception.datanuke.app.gui.utills.DataNukeGuiOption;
import com.noreasonexception.datanuke.app.gui.utills.OptionsTable;
import com.noreasonexception.datanuke.app.threadRunner.Utills;
import com.noreasonexception.datanuke.app.threadRunner.etc.ClassInfo;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;

public class ClassInfoDialog extends Application {
    ClassInfo info=null;
    DataNukeAbstractGuiFactory parentFactory=null;
    @Override
    public void start(Stage primaryStage) throws Exception {
        VBox box= new VBox();
        box.getChildren().add(getConfigurationArea());
        box.getChildren().add(new DatePicker());
        box.getChildren().add(new Separator());
        box.getChildren().add(getButtonArea(primaryStage));
        primaryStage.setScene(new Scene(box,700,300));
        primaryStage.show();


    }
    public Node getButtonArea(Stage primaryStage) {
        return new SaveOrCancelNode(primaryStage) {
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
                    private Stage parentStage = null;

                    @Override
                    public void handle(ActionEvent event) {
                        parentStage.close();
                    }

                    public EventHandler<ActionEvent> init(Stage stage) {
                        this.parentStage = stage;
                        return this;
                    }
                }.init(parentStage);
            }
        };
    }
    public Node getConfigurationArea(){
        return new OptionsTable(parentFactory) {
            @Override
            protected ObservableList<DataNukeGuiOption> getOptions() {
                ObservableList<DataNukeGuiOption> options=FXCollections.observableArrayList();
                options.add(new DataNukeGuiOption("ID",getIDArea()));
                options.add(new DataNukeGuiOption("Name",getNameArea()));
                options.add(new DataNukeGuiOption("Interval",getIntervalArea()));
                options.add(new DataNukeGuiOption("Next Event",getNextEventArea()));
                return options;
            }
            public Node getIDArea(){
                return new Label(info.getID());
            }
            public Node getNameArea(){
                return new Label(info.getClassname());
            }
            public Node getIntervalArea(){
                return new Label(String.valueOf(info.getInterval()));
            }
            public Node getNextEventArea(){
                HBox box=new HBox();
                Date next=new Date(Utills.getDeadline(
                        info.getDate().getTime(),System.currentTimeMillis(),info.getInterval()));
                LocalDate localDate;
                box.getChildren().add(new DatePicker(localDate=Instant.ofEpochMilli(next.getTime()).atZone(ZoneId.systemDefault()).toLocalDate()));


                return box;
            }
        };
    }

    public ClassInfoDialog setClassInfo(ClassInfo info){
        this.info=info;
        return this;
    }
    public ClassInfoDialog setParentFactory(DataNukeAbstractGuiFactory parentFactory){
        this.parentFactory=parentFactory;
        return this;
    }

}
