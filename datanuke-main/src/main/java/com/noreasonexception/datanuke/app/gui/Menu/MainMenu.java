package com.noreasonexception.datanuke.app.gui.Menu;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;

public class MainMenu extends MenuBar {
    private static final java.lang.String fileMenuString=   "File";
    private static final java.lang.String optionsMenuString="Options";
        private static final java.lang.String optionsMenuString_GeneralSettings=       "General Settings";
        private static final java.lang.String optionsMenuString_SourcesSettings=       "Sources Settings";
        private static final java.lang.String optionsMenuString_ThreadRunnerSettings=  "T.R Settings";

    private static final java.lang.String traceMenuString=  "Trace";
        private static final java.lang.String traceMenuString_ShowLogWindow=   "Show Logs";
        private static final java.lang.String traceMenuString_ShowErrorWindow= "Show Errors";
    private static final java.lang.String helpMenuString=   "Help";
    private static final java.lang.String aboutMenuString=  "About";
    private Menu fileMenu;
    private Menu optionsMenu;
        private MenuItem optionsMenu_GeneralSettings;
        private MenuItem optionsMenu_SourceSettings;
    private MenuItem optionsMenu_ThreadRunnerSettings;
    private Menu traceMenu;
        private MenuItem traceMenu_ShowLogWindow;
        private MenuItem traceMenu_ShowErrorWindow;
    private Menu helpMenu;
    private Menu aboutMenu;

    public MainMenu() {
        getMenus().addAll(topLevelStructureInit());
    }

    public ObservableList<Menu> topLevelStructureInit(){
        ObservableList<Menu> menus=FXCollections.observableArrayList();
        menus.addAll(fileMenuInitializer(),
                optionsMenuInitializer(),
                traceMenuInitializer(),
                helpMenuInitializer(),
                aboutMenuInitializer());
        return menus;
    }
    public Menu fileMenuInitializer(){
        fileMenu=new Menu(fileMenuString);
        return fileMenu;
    }

    public Menu optionsMenuInitializer(){
        optionsMenu=new Menu(optionsMenuString);
        optionsMenu.getItems().add(optionsMenu_GeneralSettings=new MenuItem(optionsMenuString_GeneralSettings));
        optionsMenu.getItems().add(optionsMenu_SourceSettings=new MenuItem(optionsMenuString_SourcesSettings));
        optionsMenu.getItems().add(optionsMenu_ThreadRunnerSettings=new MenuItem(optionsMenuString_ThreadRunnerSettings));
        return optionsMenu;
    }

    public Menu traceMenuInitializer(){
        traceMenu=new Menu(traceMenuString);
        traceMenu.getItems().add(traceMenu_ShowLogWindow=new MenuItem(traceMenuString_ShowLogWindow));
        traceMenu.getItems().add(traceMenu_ShowErrorWindow=new MenuItem(traceMenuString_ShowErrorWindow));
        return traceMenu;
    }

    public Menu helpMenuInitializer(){
        helpMenu=new Menu(helpMenuString);
        return helpMenu;
    }

    public Menu aboutMenuInitializer(){
        aboutMenu=new Menu(aboutMenuString);
        return aboutMenu;
    }

}
