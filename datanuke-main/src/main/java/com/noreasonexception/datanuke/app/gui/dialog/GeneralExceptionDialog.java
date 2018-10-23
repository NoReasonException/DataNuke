package com.noreasonexception.datanuke.app.gui.dialog;

import com.noreasonexception.loadable.base.error.InvalidSourceArchitectureException;

import java.util.Date;

import static com.noreasonexception.datanuke.app.utills.LoadableClassUtills.classNametoClassID;

public class GeneralExceptionDialog extends AbstractDataNukeDialog {
    public GeneralExceptionDialog(String klassName,Throwable e) {
        super(AlertType.ERROR);

        this.setTitle("Some event crashed");
        this.setHeaderText("The "+classNametoClassID(klassName)+" parser has detect a change on source");
        this.setContentText("On" +new Date().toString()+" the "+classNametoClassID(klassName)+" reports: \n"+e.getMessage());
    }
}
