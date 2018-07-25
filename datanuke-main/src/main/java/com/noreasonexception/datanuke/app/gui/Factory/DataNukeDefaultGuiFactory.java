package com.noreasonexception.datanuke.app.gui.Factory;

import com.noreasonexception.datanuke.app.factory.DataNukeAbstractFactory;
import com.noreasonexception.datanuke.app.gui.LeftBorder.ClassesTable;
import com.noreasonexception.datanuke.app.gui.Menu.MainMenu;
import com.sun.xml.internal.bind.v2.bytecode.ClassTailor;
import javafx.scene.Node;

public class DataNukeDefaultGuiFactory extends DataNukeAbstractGuiFactory {

    DataNukeAbstractFactory coreFactory=null;
    public DataNukeDefaultGuiFactory(DataNukeAbstractFactory coreFactory) {
        this.coreFactory = coreFactory;
    }

    @Override
    public Node getTopBorder() {
        return new MainMenu();
    }

    @Override
    public Node getLeftBorder() {
        ClassesTable classesTable = new ClassesTable();
        //make sure that ClassesTable will be able to hear about core changes...
        coreFactory.getThreadRunner().subscribeStateListener(classesTable.getCoreStateListener());
        coreFactory.getThreadRunner().subscribeTaskListener(classesTable.getCoreTaskListener());
        return new ClassesTable();
    }

    @Override
    public Node getCenterBorder() {
        return null;
    }

    @Override
    public Node getBottomBorder() {
        return null;
    }
}
