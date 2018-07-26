package com.noreasonexception.datanuke.app.gui.Factory;

import com.noreasonexception.datanuke.app.factory.DataNukeAbstractFactory;
import com.noreasonexception.datanuke.app.gui.LeftBorder.ClassesTable;
import com.noreasonexception.datanuke.app.gui.Menu.MainMenu;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;

import java.util.Date;

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
        return classesTable;
    }

    @Override
    public Node getCenterBorder() {
        return null;
    }

    @Override
    public Node getBottomBorder() {
        return null;
    }

    @Override
    public Node getRightBorder() {
        return new ClassesTable();
    }
}
