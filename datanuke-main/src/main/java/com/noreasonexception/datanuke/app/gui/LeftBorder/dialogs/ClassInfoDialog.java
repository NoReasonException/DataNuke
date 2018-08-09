package com.noreasonexception.datanuke.app.gui.leftBorder.dialogs;

import com.noreasonexception.datanuke.app.gui.factory.DataNukeAbstractGuiFactory;
import com.noreasonexception.datanuke.app.gui.utills.DataNukeGuiOption;
import com.noreasonexception.datanuke.app.gui.utills.OptionsTable;
import com.noreasonexception.datanuke.app.threadRunner.Utills;
import com.noreasonexception.datanuke.app.threadRunner.etc.ClassInfo;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.DateCell;
import javafx.scene.control.Label;
import javafx.scene.control.Separator;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.util.Date;

public class ClassInfoDialog extends Application {
    ClassInfo info=null;
    DataNukeAbstractGuiFactory parentFactory=null;
    @Override
    public void start(Stage primaryStage) throws Exception {
        VBox box= new VBox();
        box.getChildren().add(getConfigurationArea());
        box.getChildren().add(new Separator());
        box.getChildren().add(getButtonArea());
        primaryStage.setScene(new Scene(box,700,300));
        primaryStage.show();


    }
    public Node getButtonArea(){
        BorderPane pane=new BorderPane();
        HBox box = new HBox();
        box.getChildren().add(new Button("Save"));
        box.getChildren().add(new Button("Cancel"));
        pane.setRight(box);
        return pane;
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
                box.getChildren().add(new Label(new Date(Utills.getDeadline(
                        info.getDate().getTime(),System.currentTimeMillis(),info.getInterval())).toString()));
                box.getChildren().add(new Separator());
                box.getChildren().add(new Button("Change"));
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
