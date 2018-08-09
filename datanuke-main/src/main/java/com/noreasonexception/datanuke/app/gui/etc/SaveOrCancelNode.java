package com.noreasonexception.datanuke.app.gui.etc;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.control.Separator;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

abstract public class SaveOrCancelNode extends BorderPane {
    public SaveOrCancelNode(Stage parentStage) {
        Button button;
        HBox box=new HBox();
        box.getChildren().add(button=new Button("Save"));
        button.setOnAction(getSaveButtonHandler());
        box.getChildren().add(new Separator());
        box.getChildren().add(button=new Button("Cancel"));
        button.setOnAction(getCancelButtonHandler(parentStage));
        box.getChildren().add(new Separator());
        this.setRight(box);


    }

    abstract public EventHandler<ActionEvent> getSaveButtonHandler();
    abstract public EventHandler<ActionEvent> getCancelButtonHandler(Stage parentStage);
}
