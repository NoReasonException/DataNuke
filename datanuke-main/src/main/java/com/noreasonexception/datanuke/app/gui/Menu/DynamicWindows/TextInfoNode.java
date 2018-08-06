package com.noreasonexception.datanuke.app.gui.menu.dynamicwindows;

import com.noreasonexception.datanuke.app.gui.menu.dynamicwindows.intefaces.MessageExporter;

import java.awt.*;

public class TextInfoNode extends TextArea implements MessageExporter {
    private java.lang.String messagePrefix = null;

    @Override
    public void sendMessage(String msg) {
        setText(getText()+"\n"+messagePrefix+msg);
    }

    public TextInfoNode(java.lang.String prefix) throws HeadlessException {
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
