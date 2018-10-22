package com.noreasonexception.datanuke.app.gui.dialog;

import com.noreasonexception.loadable.base.error.InvalidSourceArchitectureException;

public class GeneralExceptionDialog extends AbstractDataNukeDialog {
    public GeneralExceptionDialog(String klassName,Throwable e) {
        super(AlertType.ERROR);

        this.setTitle("Some event crashed");
        this.setHeaderText("The "+classNametoClassID(klassName)+" event has detect a change on source");
        this.setContentText(new InvalidSourceArchitectureException(getClass()).getMessage());
    }
    private static String classNametoClassID(String className){
        String str;
        return (str=className.substring(className.lastIndexOf("."))).substring(1,str.indexOf("_"));
    }
}
