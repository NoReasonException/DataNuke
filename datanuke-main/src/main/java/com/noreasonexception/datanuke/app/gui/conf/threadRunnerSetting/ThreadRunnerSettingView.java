package com.noreasonexception.datanuke.app.gui.conf.threadRunnerSetting;

import com.noreasonexception.datanuke.app.gui.factory.DataNukeAbstractGuiFactory;
import com.noreasonexception.datanuke.app.gui.rightBorder.DataNukeGuiOption;
import com.noreasonexception.datanuke.app.gui.rightBorder.OptionsTable;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;

import javax.swing.text.Element;
import javax.swing.text.TableView;

public class ThreadRunnerSettingView extends OptionsTable {
    public ThreadRunnerSettingView(DataNukeAbstractGuiFactory factory) {
        super(factory);
    }

    @Override
    protected ObservableList<DataNukeGuiOption> getOptions() {
        ObservableList<DataNukeGuiOption> options = FXCollections.observableArrayList();
        options.add(new DataNukeGuiOption("initializationString",new TextField()));
        return options;
    }
}
