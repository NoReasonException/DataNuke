package com.noreasonexception.datanuke.app.gui.dialog;

public class InvalidFieldsError extends AbstractDataNukeDialog {
    public InvalidFieldsError() {
        super(AlertType.ERROR);
        this.setTitle("Operation not completed");
        this.setHeaderText(null);
        this.setContentText("Please fill the Text boxes with proper values!");

    }
}
