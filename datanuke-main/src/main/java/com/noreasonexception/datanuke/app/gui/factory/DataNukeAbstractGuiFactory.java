package com.noreasonexception.datanuke.app.gui.factory;

import com.noreasonexception.datanuke.app.factory.DataNukeAbstractFactory;
import com.noreasonexception.datanuke.app.gui.menu.dynamicwindows.intefaces.MessageExporter;
import javafx.scene.Node;


abstract public class DataNukeAbstractGuiFactory {
    private DataNukeAbstractFactory coreFactory;
    public DataNukeAbstractGuiFactory(DataNukeAbstractFactory coreFactory) {
        this.coreFactory=coreFactory;
    }

    public DataNukeAbstractFactory getCoreFactory() {
        return coreFactory;
    }

    abstract public Node getTopBorder();
    abstract public Node getLeftBorder();
    abstract public Node getRightBorder();
    abstract public Node getCenterBorder();
    abstract public Node getBottomBorder();
    abstract public MessageExporter getMessageExporter();

}
