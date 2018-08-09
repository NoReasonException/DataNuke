package com.noreasonexception.datanuke.app.gui.dialog;

import javafx.scene.control.Alert;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

public class AbstractDataNukeDialog extends Alert {
    public AbstractDataNukeDialog(AlertType alertType) {
        super(alertType);
        ((Stage)this.getDialogPane().getScene().getWindow()).getIcons().add(new Image("file:logos/size7.png"));

    }
}
