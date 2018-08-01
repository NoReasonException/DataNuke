package com.noreasonexception.datanuke.app.gui.RightBorder;


import com.noreasonexception.datanuke.app.gui.Colors;
import com.noreasonexception.datanuke.app.gui.Factory.DataNukeAbstractGuiFactory;
import com.noreasonexception.datanuke.app.threadRunner.ThreadRunnerState;
import com.noreasonexception.datanuke.app.threadRunner.ThreadRunnerStateListener;
import com.noreasonexception.datanuke.app.threadRunner.ThreadRunnerTaskListener;
import com.sun.deploy.uitoolkit.impl.fx.ui.FXAppContext;
import javafx.application.Platform;
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
    private Button                     onOffBtn=null;
    private Label                     nextEventTaskNameLabel=null;
    private Label                     nextEventTaskTimeLabel=null;
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
        ObservableList<DataNukeGuiOption> options=FXCollections.observableList(FXCollections.observableArrayList());
        HBox box=new HBox();
        ComboBox<String> e = new ComboBox<>();
        e.setOnAction(getStatusComboBoxHandler());
        e.getItems().add("ON");
        e.getItems().add("OFF");
        box.getChildren().add(e);
        box.getChildren().add(new Separator(Orientation.HORIZONTAL));
        box.getChildren().add(onOffBtn=new Button(statusOptionOFFString));
        onOffBtn.setStyle("-fx-background-color:"+Colors.ΟFF_COLOR);
        options.add(new DataNukeGuiOption("Status",box));
        options.add(new DataNukeGuiOption("Uptime",new Label(new Date().toString())));
        options.add(new DataNukeGuiOption("Next Event",nextEventTaskNameLabel=new Label("-")));
        options.add(new DataNukeGuiOption("Next Event(Time)",nextEventTaskTimeLabel=new Label("-")));
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

            setColumnResizePolicy(CONSTRAINED_RESIZE_POLICY);
        getColumns().addAll(getConfigName(),
                            getConfigNode());
        items.addAll(getOptions());
        setItems(items);
    }
    public ThreadRunnerStateListener getOnOffSwitchStateListener(){
        return new ThreadRunnerStateListener(){
            @Override
            public void run() {
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        Platform.runLater(new Runnable() {
                            private ThreadRunnerState currState=null;
                            @Override
                            public void run() {
                                if(currState.equals(ThreadRunnerState.NONE)){
                                    onOffBtn.setText(statusOptionOFFString);
                                    onOffBtn.setStyle("-fx-background-color:"+Colors.ΟFF_COLOR);

                                }
                                else{
                                    onOffBtn.setText(statusOptionONString);
                                    onOffBtn.setStyle("-fx-background-color:"+Colors.ON_COLOR);

                                }
                            }
                            public Runnable init(ThreadRunnerState currState){
                                this.currState=currState;
                                return this;
                            }
                        }.init(getState()));
                    }
                }).start();

            }
        };
    }
    public ThreadRunnerStateListener getNextEventStateListener(){
        return new ThreadRunnerStateListener() {
            @Override
            public void run() {
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        Platform.runLater(new Runnable() {
                            @Override
                            public void run() {
                                if(getState().equals(ThreadRunnerState.NONE)){
                                    nextEventTaskNameLabel.setText(" - ");
                                    nextEventTaskTimeLabel.setText(" - ");
                                }
                            }
                        });
                    }
                }).start();
            }
        };
    }
    public ThreadRunnerTaskListener getNextEventTaskListener(){
        return new ThreadRunnerTaskListener() {
            @Override
            public void onClassWaitUntillDeadline(String classname, Object[] args) {
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        Platform.runLater(new Runnable() {
                            @Override
                            public void run() {
                                String[]e;
                                nextEventTaskNameLabel.setText((e=classname.split("\\."))[e.length-1]);
                                nextEventTaskTimeLabel.setText(new Date((Long)args[0]).toString());
                            }
                        });
                    }
                }).start();
            }
        };
    }
}