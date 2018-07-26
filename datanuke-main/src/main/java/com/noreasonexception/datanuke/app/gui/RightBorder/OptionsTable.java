package com.noreasonexception.datanuke.app.gui.RightBorder;


import com.noreasonexception.datanuke.app.gui.Factory.DataNukeAbstractGuiFactory;
import javafx.beans.InvalidationListener;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableStringValue;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Orientation;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.util.Callback;

import java.awt.event.ActionEvent;
import java.beans.EventHandler;
import java.util.Date;

public class OptionsTable extends TableView<DataNukeGuiOption> {
    private ObservableList<DataNukeGuiOption> items;
    private final java.lang.String configNameColumnString = "Name";
    private final java.lang.String configNodeColumnString = "Option";
    private final java.lang.String statusOptionNameString = "Status";
        private final java.lang.String statusOptionONString = "ON";
        private final java.lang.String statusOptionOFFString = "OFF";

    private TableColumn<DataNukeGuiOption,String>      configName;
    private TableColumn<DataNukeGuiOption,Node>        configNode;
    private DataNukeAbstractGuiFactory parentfactory =null;
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
                        return param.getValue().getNode();
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
    protected ObservableList<DataNukeGuiOption> getOptions(){
        ObservableList<DataNukeGuiOption> options=FXCollections.observableArrayList();
        HBox box=new HBox();
        ComboBox<String> e = new ComboBox<>();
        e.setOnAction(getStatusComboBoxHandler());
        e.getItems().add("ON");
        e.getItems().add("OFF");
        box.getChildren().add(e);
        box.getChildren().add(new Separator(Orientation.HORIZONTAL));
        box.getChildren().add(new Button("OFF"));
        options.add(new DataNukeGuiOption("Status",box));
        options.add(new DataNukeGuiOption("Uptime",new Label(new Date().toString())));
        options.add(new DataNukeGuiOption("Next Event",new Label("A12")));
        options.add(new DataNukeGuiOption("Next Event(Time)",new Label(new Date().toString())));
        return options;
    }
    protected javafx.event.EventHandler<javafx.event.ActionEvent> getStatusComboBoxHandler(){
        return new javafx.event.EventHandler<javafx.event.ActionEvent>() {
            @Override
            public void handle(javafx.event.ActionEvent event) {
                String res;
                System.out.println(res=((ComboBox<String>)event.getSource()).getValue());
                if(res!=null&&res.equals(statusOptionONString))
                    getParentfactory().getCoreFactory().getThreadRunner().startMainThread();
                else getParentfactory().getCoreFactory().getThreadRunner().stopMainThread();
            }
        };
    }

    public DataNukeAbstractGuiFactory getParentfactory() {
        return parentfactory;
    }

    public OptionsTable(DataNukeAbstractGuiFactory factory) {
        this.parentfactory=factory;
        items=FXCollections.observableArrayList();
        setItems(items);
            setColumnResizePolicy(CONSTRAINED_RESIZE_POLICY);
        getColumns().addAll(getConfigName(),
                            getConfigNode());
        items.addAll(getOptions());
    }
}