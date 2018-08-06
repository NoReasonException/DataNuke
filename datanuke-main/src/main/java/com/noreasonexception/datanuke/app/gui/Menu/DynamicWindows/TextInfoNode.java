package com.noreasonexception.datanuke.app.gui.menu.dynamicwindows;

import com.noreasonexception.datanuke.app.gui.menu.dynamicwindows.intefaces.MessageExporter;
import javafx.scene.control.TextArea;


public class TextInfoNode extends TextArea implements MessageExporter {
    private java.lang.String messagePrefix = null;

    @Override
    public void sendMessage(String msg) {
        setText(getText()+"\n"+messagePrefix+msg);
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
