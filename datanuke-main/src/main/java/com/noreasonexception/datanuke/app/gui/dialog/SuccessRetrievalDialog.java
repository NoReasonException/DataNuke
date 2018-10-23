package com.noreasonexception.datanuke.app.gui.dialog;

import com.noreasonexception.datanuke.app.utills.LoadableClassUtills;

import java.util.Date;

public class SuccessRetrievalDialog extends AbstractDataNukeDialog {
    public SuccessRetrievalDialog(String klassName, Double value) {
        super(AlertType.CONFIRMATION);
        this.setTitle("Success retrieval");
        this.setHeaderText("The "+ LoadableClassUtills.classNametoClassID(klassName)+" event has retrieve the value "+value);
        this.setContentText("On "+new Date().toString());
    }
}
