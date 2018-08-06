package com.noreasonexception.datanuke.app.gui.menu.dynamicwindows;

import java.awt.*;

public class TextInfoNode extends TextArea {
    private java.lang.String messagePrefix = null;
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
