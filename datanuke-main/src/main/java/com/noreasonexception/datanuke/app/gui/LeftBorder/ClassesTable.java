package com.noreasonexception.datanuke.app.gui.LeftBorder;

import com.noreasonexception.datanuke.app.threadRunner.ThreadRunnerStateListener;
import com.noreasonexception.datanuke.app.threadRunner.ThreadRunnerTaskListener;
import com.noreasonexception.datanuke.app.threadRunner.etc.ClassInfo;
import com.sun.source.util.TaskListener;
import javafx.beans.InvalidationListener;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableStringValue;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Button;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.util.Callback;
import java.util.Date;

public class ClassesTable extends TableView<ClassInfo> {
    public ClassesTable() {
        int i=0;
        ObservableList<ClassInfo>s=FXCollections.observableArrayList();
        setItems(s);
        TableColumn<ClassInfo,String> str=new TableColumn<>("ID");
        TableColumn<ClassInfo,String> str2=new TableColumn<>("Date");
        TableColumn<ClassInfo,Button> strbtn=new TableColumn<>("Status");
        getColumns().add(str);
        //getColumns().add(str2);
        getColumns().add(strbtn);
        setColumnResizePolicy(CONSTRAINED_RESIZE_POLICY);
        str2.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<ClassInfo, String>, ObservableValue<String>>() {
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
                        return param.getValue().getDate().toString();
                    }

                    @Override
                    public void addListener(InvalidationListener listener) { }

                    @Override
                    public void removeListener(InvalidationListener listener) { }
                };
            }
        });
        str.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<ClassInfo, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<ClassInfo, String> param) {

                return new ObservableStringValue() {
                    @Override
                    public String get() {
                        return param.getValue().getClassname();
                    }

                    @Override
                    public void addListener(ChangeListener<? super String> listener) { }

                    @Override
                    public void removeListener(ChangeListener<? super String> listener) { }

                    @Override
                    public String getValue() {
                        return param.getValue().getClassname();
                    }

                    @Override
                    public void addListener(InvalidationListener listener) { }

                    @Override
                    public void removeListener(InvalidationListener listener) { }
                };

            }
        });

        strbtn.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<ClassInfo, Button>, ObservableValue<Button>>() {
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
        str.setCellFactory((coll)->{
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
        s.add(new ClassInfo(new Date(),12,"A1"));
        s.add(new ClassInfo(new Date(),0,"A2"));
        s.add(new ClassInfo(new Date(),12,"A3"));
    }
    public ThreadRunnerTaskListener getCoreTaskListener(){
        return new ThreadRunnerTaskListener() {

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
