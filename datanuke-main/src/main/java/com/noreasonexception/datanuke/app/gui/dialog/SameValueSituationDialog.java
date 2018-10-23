package com.noreasonexception.datanuke.app.gui.dialog;

import com.noreasonexception.datanuke.app.utills.LoadableClassUtills;
import com.noreasonexception.loadable.base.error.InvalidSourceArchitectureException;
import org.apache.commons.lang3.ClassUtils;

import java.util.Date;

public class SameValueSituationDialog extends AbstractDataNukeDialog {
    public SameValueSituationDialog(String klassName) {
        super(AlertType.WARNING);
        this.setTitle("Same event situation");
        this.setHeaderText("The "+ LoadableClassUtills.classNametoClassID(klassName)+" event has the same value as before");
        this.setContentText("Last request attemt on "+new Date().toString());
    }
}
