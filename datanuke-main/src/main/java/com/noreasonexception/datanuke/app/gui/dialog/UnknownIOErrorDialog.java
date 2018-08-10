package com.noreasonexception.datanuke.app.gui.dialog;

public class UnknownIOErrorDialog extends AbstractDataNukeDialog {
    public UnknownIOErrorDialog(String msg) {
        super(AlertType.ERROR);

        this.setTitle("Operation not completed");
        this.setHeaderText(null);
        this.setContentText("Something gone extremely wrong , contact with the developer and give him this message : "+msg);
    }
}
