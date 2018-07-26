package com.noreasonexception.datanuke.app.gui.RightBorder;


import com.noreasonexception.datanuke.app.threadRunner.ThreadRunnerStateListener;
import com.noreasonexception.datanuke.app.threadRunner.ThreadRunnerTaskListener;
import com.noreasonexception.datanuke.app.threadRunner.etc.ClassInfo;
import javafx.beans.InvalidationListener;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableStringValue;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Node;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.util.Callback;

public class OptionsTable extends TableView<DataNukeOption> {
    private ObservableList<DataNukeOption> items;
    private final java.lang.String configNameColumnString = "Name";
    private final java.lang.String configNodeColumnString = "Option";
    private TableColumn<DataNukeOption,String>      configName;
    private TableColumn<DataNukeOption,Node>        configNode;

    private TableColumn<DataNukeOption,String> getConfigName(){
        configName =new TableColumn<>(configNameColumnString);
        configName.setSortable(false);
        configName.setCellValueFactory(
                new Callback<TableColumn.CellDataFeatures<DataNukeOption, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<DataNukeOption, String> param) {
                return new ObservableStringValue() {
                    @Override
                    public String get() {
                        return null;
                    }

                    @Override
                    public void addListener(ChangeListener<? super String> listener) {

                    }

                    @Override
                    public void removeListener(ChangeListener<? super String> listener) {

                    }

                    @Override
                    public String getValue() {
                        return param.getValue().getName();
                    }

                    @Override
                    public void addListener(InvalidationListener listener) {

                    }

                    @Override
                    public void removeListener(InvalidationListener listener) {

                    }
                };
            }
        });
        return this.configName;
    }
    private TableColumn<DataNukeOption,Node> getConfigNode(){
        configNode =new TableColumn<>(configNodeColumnString);

        return configNode;
    }
    public OptionsTable() {
        items=FXCollections.observableArrayList();
        setItems(items);
        setColumnResizePolicy(CONSTRAINED_RESIZE_POLICY);
        getColumns().addAll(getConfigName(),
                            getConfigNode());

    }
    public ThreadRunnerTaskListener getCoreTaskListener(){
        return null;
    }

    public ThreadRunnerStateListener getCoreStateListener(){
        return null;
    }
}