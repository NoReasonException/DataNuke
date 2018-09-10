package com.noreasonexception.datanuke.app.gui.rightBorder;

import com.noreasonexception.datanuke.app.gui.Colors;
import com.noreasonexception.datanuke.app.gui.factory.DataNukeAbstractGuiFactory;
import com.noreasonexception.datanuke.app.gui.utills.DataNukeGuiOption;
import com.noreasonexception.datanuke.app.gui.utills.OptionsTable;
import com.noreasonexception.datanuke.app.threadRunner.ThreadRunnerState;
import com.noreasonexception.datanuke.app.threadRunner.ThreadRunnerStateListener;
import com.noreasonexception.datanuke.app.threadRunner.ThreadRunnerTaskListener;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Orientation;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.Separator;
import javafx.scene.layout.HBox;

import java.util.Date;

public class UserOptionTable extends OptionsTable {
    private Button                      onOffBtn;
    private Label                       nextEventTaskNameLabel;
    private Label                       nextEventTaskTimeLabel;
    private final java.lang.String      statusOptionNameString = "Status";
    private final java.lang.String      statusOptionONString = "ON";
    private final java.lang.String      statusOptionOFFString = "OFF";
    public UserOptionTable(DataNukeAbstractGuiFactory factory) {
        super(factory);
    }

    @Override
    protected ObservableList<DataNukeGuiOption> getOptions() {
        ObservableList<DataNukeGuiOption> options = FXCollections.observableList(FXCollections.observableArrayList());
        HBox box = new HBox();
        ComboBox<String> e = new ComboBox<>();
        e.setOnAction(getStatusComboBoxHandler());
        e.getItems().add(statusOptionOFFString);
        e.getItems().add(statusOptionONString);
        box.getChildren().add(e);
        box.getChildren().add(new Separator(Orientation.HORIZONTAL));
        box.getChildren().add(this.onOffBtn = new Button(statusOptionOFFString));
        onOffBtn.setStyle("-fx-background-color:" + Colors.ΟFF_COLOR);
        options.add(new DataNukeGuiOption("Status", box));
        options.add(new DataNukeGuiOption("Uptime", new Label(new Date().toString())));
        options.add(new DataNukeGuiOption("Next Event", nextEventTaskNameLabel = new Label("-")));
        options.add(new DataNukeGuiOption("Next Event(Time)", nextEventTaskTimeLabel = new Label("-")));
        return options;

    }
    protected javafx.event.EventHandler<javafx.event.ActionEvent> getStatusComboBoxHandler(){
        return new javafx.event.EventHandler<javafx.event.ActionEvent>() {
            @Override
            public void handle(javafx.event.ActionEvent event) {
                String res;
                System.out.println(res=((ComboBox<String>)event.getSource()).getValue());
                if(res!=null&&res.equals(statusOptionONString))
                    getParentfactory().getCoreFactory().getThreadRunner().startMainThread(
                            getParentfactory().getMessageExporter(),
                            getParentfactory().getMessageExporter());
                else getParentfactory().getCoreFactory().getThreadRunner().stopMainThread();
            }
        };
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
}
