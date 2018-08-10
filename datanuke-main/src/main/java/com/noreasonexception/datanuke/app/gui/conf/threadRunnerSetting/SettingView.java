package com.noreasonexception.datanuke.app.gui.conf.threadRunnerSetting;

import com.noreasonexception.datanuke.app.dataProvider.DataProvider;
import com.noreasonexception.datanuke.app.gui.factory.DataNukeAbstractGuiFactory;
import com.noreasonexception.datanuke.app.gui.utills.DataNukeGuiOption;
import com.noreasonexception.datanuke.app.gui.utills.OptionsTable;
import com.noreasonexception.datanuke.app.threadRunner.AbstractThreadRunner;
import com.noreasonexception.datanuke.app.threadRunner.Utills;
import com.noreasonexception.datanuke.app.threadRunner.error.ConfigurationLoaderException;
import com.noreasonexception.datanuke.app.threadRunner.error.ConvertException;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

import javax.json.JsonObject;
import java.io.IOException;
import java.util.Date;

abstract public class SettingView extends OptionsTable {
    public SettingView(DataNukeAbstractGuiFactory factory) {
        super(factory);
    }

    @Override
    protected ObservableList<DataNukeGuiOption> getOptions() {
        ObservableList<DataNukeGuiOption> options = FXCollections.observableArrayList();
        JsonObject obj;
        try{
            obj=DataProvider.Utills.dataProviderToJsonObject(onDataProviderGet());
            for (String confName:obj.keySet()) {
                options.add(new DataNukeGuiOption(confName, onNodeGetByOptionName(confName,obj)));
            }
        }
        catch(Exception e){

        }

        return options;
    }

    abstract public DataProvider onDataProviderGet();
    abstract public Node onNodeGetByOptionName(String optionName,JsonObject jsonObject);


}
