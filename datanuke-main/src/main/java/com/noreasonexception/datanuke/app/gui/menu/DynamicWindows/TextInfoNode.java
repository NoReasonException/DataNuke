package com.noreasonexception.datanuke.app.gui.menu.dynamicwindows;

import com.noreasonexception.datanuke.app.gui.menu.dynamicwindows.intefaces.MessageExporter;
import javafx.application.Platform;
import javafx.scene.control.TextArea;

import java.util.Date;


public class TextInfoNode extends TextArea implements MessageExporter {
    private java.lang.String messagePrefix = null;

    @Override
    public void sendMessage(String msg) {
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                setText(getText()+"\n"+messagePrefix+(new Date().toString())+": "+msg);
            }
        });
    }

    public TextInfoNode(java.lang.String prefix) {
        super();
        messagePrefix=prefix;
    }

    public String getMessagePrefix() {
        return messagePrefix;
    }

    public void setMessagePrefix(String messagePrefix) {
        this.messagePrefix = messagePrefix;
    }

}
