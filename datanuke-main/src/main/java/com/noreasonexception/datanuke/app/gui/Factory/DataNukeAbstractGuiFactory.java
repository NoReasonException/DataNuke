package com.noreasonexception.datanuke.app.gui.Factory;

import com.noreasonexception.datanuke.app.factory.DataNukeAbstractFactory;
import com.noreasonexception.datanuke.app.gui.Menu.MainMenu;
import javafx.scene.Node;
import javafx.scene.control.MenuBar;
import javafx.scene.control.TableView;


abstract public class DataNukeAbstractGuiFactory {
    abstract public Node getTopBorder();
    abstract public Node getLeftBorder();
    abstract public Node getCenterBorder();
    abstract public Node getBottomBorder();

}
