package com.noreasonexception.datanuke.app.gui.conf.threadRunnerSetting;

import com.noreasonexception.datanuke.app.gui.factory.DataNukeAbstractGuiFactory;
import com.noreasonexception.datanuke.app.gui.utills.DataNukeGuiOption;
import com.noreasonexception.datanuke.app.gui.utills.OptionsTable;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.TextField;

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
