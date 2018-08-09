package com.noreasonexception.datanuke.app.gui.conf.threadRunnerSetting.ThreadRunnerSettingWindow;

import com.noreasonexception.datanuke.app.dataProvider.DataProvider;
import com.noreasonexception.datanuke.app.gui.conf.threadRunnerSetting.SettingView;
import com.noreasonexception.datanuke.app.gui.conf.threadRunnerSetting.SettingWindow;
import com.noreasonexception.datanuke.app.gui.factory.DataNukeAbstractGuiFactory;
import javafx.scene.Node;
import javafx.scene.control.Label;

import javax.json.JsonObject;
import java.io.IOException;

public class GeneralSettingsWindow extends SettingWindow {
    public GeneralSettingsWindow(DataNukeAbstractGuiFactory parentFactory) {
        super(parentFactory);
    }

    @Override
    protected SettingView onSettingViewGet() {
        return new SettingView(getParentFactory()) {
            @Override
            public DataProvider onDataProviderGet() {
                try {
                    return getParentFactory().getCoreFactory().getFactoryDefaultConfigProvider();

                }catch (IOException e){e.printStackTrace();return null;}
            }

            @Override
            public Node onNodeGetByOptionName(String optionName, JsonObject jsonObject) {
                return new Label(jsonObject.getString(optionName));
            }
        };
    }
}
