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
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.paint.Color;
import javafx.util.Callback;

public class OptionsTable extends TableView<ClassInfo> {
    private ObservableList<ClassInfo> items;
    private TableColumn<ClassInfo,String> ConfigName;
    private TableColumn<ClassInfo,Node> option;

    private TableColumn<ClassInfo,String> getOptionColumn(){
        ConfigName =new TableColumn<>("Name");
        ConfigName.setSortable(false);
        ConfigName.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<ClassInfo, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<ClassInfo, String> param) {
                return new ObservableStringValue() {
                    @Override
                    public String get() {
                        return null;
                    }

                    @Override
                    public void addListener(ChangeListener<? super String> listener) { }

                    @Override
                    public void removeListener(ChangeListener<? super String> listener) { }

                    @Override
                    public String getValue() {
                        if(param.getValue().getInterval()==0)return null;
                        return param.getValue().getID();
                    }

                    @Override
                    public void addListener(InvalidationListener listener) { }

                    @Override
                    public void removeListener(InvalidationListener listener) { }
                };
            }
        });

        ConfigName.setCellFactory((coll)->{
            return new TableCell<ClassInfo,String>(){
                @Override
                public void updateItem(String ci,boolean se){
                    super.updateItem(ci,se);
                    if(getIndex()==0){
                        setTextFill(Color.rgb(255,0,0));
                    }
                    System.out.println(getIndex());
                    setText(ci);
                }
            };
        });
        return ConfigName;
    }
    private TableColumn<ClassInfo,Node> getConfigColumn(){
        option =new TableColumn<>("option");

        return option;
    }
    public OptionsTable() {
        items=FXCollections.observableArrayList();
        setItems(items);
        setColumnResizePolicy(CONSTRAINED_RESIZE_POLICY);
        getColumns().addAll(getOptionColumn(),
                getConfigColumn());

    }
    public ThreadRunnerTaskListener getCoreTaskListener(){
        return new ThreadRunnerTaskListener() {
            @Override
            public void onClassReadInfo(String classname, Object[] args) {
                items.add((ClassInfo) args[0]);
            }
        };
    }

    public ThreadRunnerStateListener getCoreStateListener(){
        return new ThreadRunnerStateListener() {
            @Override
            public void run() {
            }
        };
    }
}