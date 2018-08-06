package com.noreasonexception.datanuke.app.gui.leftBorder;

import com.noreasonexception.datanuke.app.threadRunner.ThreadRunnerState;
import com.noreasonexception.datanuke.app.threadRunner.ThreadRunnerStateListener;
import com.noreasonexception.datanuke.app.threadRunner.ThreadRunnerTaskListener;
import com.noreasonexception.datanuke.app.threadRunner.etc.ClassInfo;
import javafx.beans.InvalidationListener;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableStringValue;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.*;
import javafx.scene.paint.Color;
import javafx.util.Callback;

public class ClassesTable extends TableView<ClassInfo> {
    private ObservableList<ClassInfo> items;
    private TableColumn<ClassInfo,String> id;
    private TableColumn<ClassInfo,Button> status;

    private TableColumn<ClassInfo,String> getIdColumn(){
        id=new TableColumn<>("ID");
        id.setSortable(false);
        id.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<ClassInfo, String>, ObservableValue<String>>() {
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

        id.setCellFactory((coll)->{
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
        return id;
    }
    private TableColumn<ClassInfo,Button> getStatusColumn(){
        status=new TableColumn<>("Status");
        status.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<ClassInfo, Button>, ObservableValue<Button>>() {
            @Override
            public ObservableValue<Button> call(TableColumn.CellDataFeatures<ClassInfo, Button> param) {

                return new ObservableValue<Button>() {
                    @Override
                    public void addListener(ChangeListener<? super Button> listener) {

                    }

                    @Override
                    public void removeListener(ChangeListener<? super Button> listener) {

                    }

                    @Override
                    public Button getValue() {
                        Button retval=new Button();

                        retval.setStyle("-fx-background-color: blue;-fx-fill-height: true;-fx-fill-width: true");
                        if(param.getValue().getInterval()==0){
                            retval.setStyle("-fx-background-color: red");
                        }
                        retval.setDisable(true);
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
        return status;
    }
    public ClassesTable() {
        items=FXCollections.observableArrayList();
        setItems(items);
        setColumnResizePolicy(CONSTRAINED_RESIZE_POLICY);
        getColumns().addAll(getIdColumn(),
                            getStatusColumn());

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
                if(getState().equals(ThreadRunnerState.NONE))items.clear();
            }
        };
    }
}
