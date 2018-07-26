package com.noreasonexception.datanuke.app.gui.RightBorder;


import javafx.beans.InvalidationListener;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableStringValue;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.util.Callback;

public class OptionsTable extends TableView<DataNukeGuiOption> {
    private ObservableList<DataNukeGuiOption> items;
    private final java.lang.String configNameColumnString = "Name";
    private final java.lang.String configNodeColumnString = "Option";
    private TableColumn<DataNukeGuiOption,String>      configName;
    private TableColumn<DataNukeGuiOption,Node>        configNode;

    private TableColumn<DataNukeGuiOption,String> getConfigName(){
        configName =new TableColumn<>(configNameColumnString);
        configName.setSortable(false);
        configName.setCellValueFactory(
                new Callback<TableColumn.CellDataFeatures<DataNukeGuiOption, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<DataNukeGuiOption, String> param) {
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
    private TableColumn<DataNukeGuiOption,Node> getConfigNode(){
        configNode =new TableColumn<>(configNodeColumnString);
        configNode.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<DataNukeGuiOption, Node>, ObservableValue<Node>>() {
            @Override
            public ObservableValue<Node> call(TableColumn.CellDataFeatures<DataNukeGuiOption, Node> param) {

                return new ObservableValue<Node>() {
                    @Override
                    public void addListener(ChangeListener<? super Node> listener) {

                    }

                    @Override
                    public void removeListener(ChangeListener<? super Node> listener) {

                    }

                    @Override
                    public Node getValue() {
                        Button retval=new Button("Test");

                        return retval;
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
        return configNode;
    }
    public OptionsTable() {
        items=FXCollections.observableArrayList();
        setItems(items);
        setColumnResizePolicy(CONSTRAINED_RESIZE_POLICY);
        getColumns().addAll(getConfigName(),
                            getConfigNode());


    }
}