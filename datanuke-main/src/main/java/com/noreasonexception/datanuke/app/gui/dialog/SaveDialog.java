package com.noreasonexception.datanuke.app.gui.dialog;

import com.noreasonexception.datanuke.app.gui.dialog.AbstractDataNukeDialog;

public class SaveDialog extends AbstractDataNukeDialog {
    public SaveDialog() {
        super(AlertType.INFORMATION);
        this.setTitle("Operation completed");
        this.setHeaderText(null);
        this.setContentText("Save Completed");

    }
}
