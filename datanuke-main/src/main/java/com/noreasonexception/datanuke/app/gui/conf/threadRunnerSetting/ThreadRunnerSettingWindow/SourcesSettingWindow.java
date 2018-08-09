package com.noreasonexception.datanuke.app.gui.conf.threadRunnerSetting.ThreadRunnerSettingWindow;

import com.noreasonexception.datanuke.app.dataProvider.DataProvider;
import com.noreasonexception.datanuke.app.gui.conf.threadRunnerSetting.SettingView;
import com.noreasonexception.datanuke.app.gui.conf.threadRunnerSetting.SettingWindow;
import com.noreasonexception.datanuke.app.gui.factory.DataNukeAbstractGuiFactory;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import javax.json.JsonObject;
import java.io.IOException;

public class SourcesSettingWindow extends SettingWindow {
    @Override
    protected SettingView onSettingViewGet() {
        return new SettingView(getParentFactory()) {
            @Override
            public DataProvider onDataProviderGet() {
                try {
                    return getParentFactory().getCoreFactory().getThreadRunnersSourceProvider();

                }catch (IOException e){
                    e.printStackTrace();
                    return null;
                }
            }

            @Override
            public Node onNodeGetByOptionName(String optionName, JsonObject jsonObject) {
                VBox box = new VBox();
                box.getChildren().add(new Label(jsonObject.getJsonArray(optionName).getString(0)));
                box.getChildren().add(new Label(jsonObject.getJsonArray(optionName).getString(1)));
                box.getChildren().add(new Label(jsonObject.getJsonArray(optionName).getString(2)));
                return box;
            }
        };
    }

    public SourcesSettingWindow(DataNukeAbstractGuiFactory parentFactory) {
        super(parentFactory);
    }
}
