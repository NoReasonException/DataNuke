package com.noreasonexception.datanuke.app.gui.leftBorder.dialogs;

import com.noreasonexception.datanuke.app.dataProvider.DataProvider;
import com.noreasonexception.datanuke.app.dataProvider.StringDataProvider;
import com.noreasonexception.datanuke.app.gui.dialog.InvalidFieldsError;
import com.noreasonexception.datanuke.app.gui.dialog.SaveDialog;
import com.noreasonexception.datanuke.app.gui.dialog.UnknownIOErrorDialog;
import com.noreasonexception.datanuke.app.gui.etc.SaveOrCancelNode;
import com.noreasonexception.datanuke.app.gui.factory.DataNukeAbstractGuiFactory;
import com.noreasonexception.datanuke.app.gui.utills.DataNukeGuiOption;
import com.noreasonexception.datanuke.app.gui.utills.OptionsTable;
import com.noreasonexception.datanuke.app.threadRunner.Utills;
import com.noreasonexception.datanuke.app.threadRunner.error.ConvertException;
import com.noreasonexception.datanuke.app.threadRunner.etc.ClassInfo;
import javafx.application.Application;
import javafx.collections.ArrayChangeListener;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import javax.json.*;
import javax.security.auth.callback.Callback;
import javax.xml.crypto.Data;
import java.io.IOException;
import java.nio.file.Paths;
import java.time.*;
import java.time.chrono.ChronoLocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;

public class ClassInfoDialog extends Application {
    TableView<DataNukeGuiOption> optionTableView = null;
    DataNukeAbstractGuiFactory parentFactory=null;
    ClassInfo info=null;


    /***
     * The String just before the Class ID
     */
    private static String IDString="ID";
    private static Integer IDIndex=0;

    /***
     * The String just before Class Name
     */
    private static String NameString="Name";
    private static Integer NameIndex=1;


    /***
     * The String just before Next Event widget
     */
    private static String NextEventString="Next Event";
    private static Integer NextEventIndex=2;
    @Override


    public void start(Stage primaryStage) throws Exception {
        VBox box= new VBox();
        box.getChildren().add(optionTableView=(TableView<DataNukeGuiOption>)getConfigurationArea());
        box.getChildren().add(new Separator());
        box.getChildren().add(getButtonArea(primaryStage));
        primaryStage.setScene(new Scene(box,700,300));
        primaryStage.show();


    }

    /***
     * Constructs and returns the save-cancel button area . performs the save operation
     * ///TODO Refactor ! may create a central system for save and just call it from here?!?
     */
    public Node getButtonArea(Stage primaryStage) {
        return new SaveOrCancelNode(primaryStage) {

            /**
             * Save button handler
             * Saves the new data into the source file.
             * TODO needs refactor
             * @return EventHandler<ActionEvent>
             */
            @Override
            public EventHandler<ActionEvent> getSaveButtonHandler() {
                return new EventHandler<ActionEvent>() {
                    @Override
                    public void handle(ActionEvent event) {
                        HBox h=(HBox)optionTableView.getColumns().get(1).getCellData(NextEventIndex);

                        LocalDate newDate=((DatePicker)h.getChildren().get(0)).getValue();
                        HBox dateTimeBox=(HBox) h.getChildren().get(2);
                        Integer newTimeHour;
                        Integer newTimeMins;
                        JsonObject obj;
                        try{

                            obj=DataProvider.Utills.dataProviderToJsonObject(
                                    parentFactory.getCoreFactory().getThreadRunnersSourceProvider()
                            );
                        }catch (Exception e){
                            new UnknownIOErrorDialog("Sources Provider IOError").show();
                            return;
                        }

                        long nextInterval;
                        try {
                           newTimeHour=Integer.valueOf(((TextField)dateTimeBox.getChildren().get(0)).getText());
                           newTimeMins=Integer.valueOf(((TextField)dateTimeBox.getChildren().get(4)).getText());

                        }catch (NumberFormatException e){
                            new InvalidFieldsError().show();
                            return;
                        }
                        if(newTimeHour>23||newTimeMins>59){
                            new InvalidFieldsError().show();
                            return;
                        }
                        LocalDateTime localDateTime=LocalDateTime.of(newDate.getYear(),
                                                                    newDate.getMonth(),
                                                                    newDate.getDayOfMonth(),
                                                                    newTimeHour,
                                                                    newTimeMins);

                        String className=((Label)(optionTableView.getItems().get(1).getNode())).getText();



                        JsonArrayBuilder builder=javax.json.Json.createArrayBuilder();
                        builder.add((String.valueOf(System.currentTimeMillis())));

                        builder.add((
                                String.valueOf(
                                        localDateTime.atZone(ZoneId.systemDefault()).toInstant().toEpochMilli()-
                                                System.currentTimeMillis())));
                        builder.add(((Label)optionTableView.getItems().get(0).getNode()).getText());

                        JsonObjectBuilder rebuilder = Json.createObjectBuilder();
                        for (String k:
                             obj.keySet()) {
                            if(className.equals(k)){
                                JsonArray e;
                                rebuilder.add(className,e=builder.build());
                                System.out.println(e);
                                continue;
                            }
                            rebuilder.add(k,obj.get(k));
                        }
                        obj=rebuilder.build();
                        try {

                            DataProvider.Utills.writeDataProviderToFile(
                                    DataProvider.Utills.jsonObjectToDataProvider(obj), Paths.get(parentFactory.getCoreFactory().getThreadRunnersSourceProviderFile()));

                        }catch (IOException|ConvertException e){
                            new UnknownIOErrorDialog("Convert of json To DataProvider failed").show();
                            return;

                        }
                        new SaveDialog().
                                setAdditionalMessage("In order the changes to take effect , it is essential to restart your program").show();
                    }
                };
            }

            /***
             * Cancel button handler
             */
            @Override
            public EventHandler<ActionEvent> getCancelButtonHandler(Stage parentStage) {
                return new EventHandler<ActionEvent>() {
                    private Stage parentStage = null;

                    @Override
                    public void handle(ActionEvent event) {
                        parentStage.close();
                    }

                    public EventHandler<ActionEvent> init(Stage stage) {
                        this.parentStage = stage;
                        return this;
                    }
                }.init(parentStage);
            }
        };
    }

    /***
     * Constructs and returns the whole configuration area , as OptionsTable
     */
    public Node getConfigurationArea(){
        return new OptionsTable(parentFactory) {
            @Override
            protected ObservableList<DataNukeGuiOption> getOptions() {
                ObservableList<DataNukeGuiOption> options=FXCollections.observableArrayList();
                options.add(new DataNukeGuiOption(IDString,getIDArea()));
                options.add(new DataNukeGuiOption(NameString,getNameArea()));
                options.add(new DataNukeGuiOption(NextEventString,getNextEventArea()));
                return options;
            }


            /***
             * @return the ID of that area (A1,A2 ...)
             */
            public Node getIDArea(){
                return new Label(info.getID());
            }

            /***
             * @return the name of the class...
             */
            public Node getNameArea(){
                return new Label(info.getClassname());
            }

            /***
             * constructs and returns the next-event configuration area
             */
            public Node getNextEventArea(){
                HBox box=new HBox();
                Date next=new Date(Utills.getDeadline(
                        info.getDate().getTime(),System.currentTimeMillis(),info.getInterval()));
                LocalDate localDate;
                LocalDateTime localDateTime;
                box.getChildren().add(getDateEditNode(next.getTime()));
                localDateTime=Instant.ofEpochMilli(next.getTime()).atZone(ZoneId.systemDefault()).toLocalDateTime();
                box.getChildren().add(new Separator());
                box.getChildren().add(getTimeEditNode(localDateTime.getHour(),localDateTime.getMinute()));

                return box;
            }

            /***
             * Returns the Date widget
             * @param mills the current next timestamp , in order to config the Date widget
             */
            public Node getDateEditNode(long mills){
                DatePicker p=new DatePicker((Instant.ofEpochMilli(mills).
                        atZone(ZoneId.systemDefault()).toLocalDate()));
                p.setEditable(false);
                p.setDayCellFactory(getDayCellFactory());

                return p;
            }

            /***
             * getDayCellFactory
             * This method returns a cell factory , when applied to a DatePicker , makes unable
             * to choice every day before today!
             * @return a CallBack in order to create every cell
             */
            public javafx.util.Callback<DatePicker,DateCell> getDayCellFactory(){
                return new javafx.util.Callback<DatePicker, DateCell>() {
                    @Override
                    public DateCell call(DatePicker param) {
                        return new DateCell(){
                            @Override
                            public void updateItem(LocalDate item, boolean empty) {
                                super.updateItem(item, empty);
                                if(item.isBefore(ChronoLocalDate.from(LocalDate.now()))){
                                    setDisable(true);
                                    setStyle("-fx-background-color: #ffc0cb;");
                                }
                            }
                        };
                    }
                };
            }
            public Node getTimeEditNode(int hour,int mins){
                HBox box=new HBox();
                TextField tmp;
                box.getChildren().add(tmp=new TextField(String.valueOf(hour)));
                box.getChildren().add(new Separator());
                box.getChildren().add(new Label(":"));
                box.getChildren().add(new Separator());
                box.getChildren().add(new TextField(String.valueOf(mins)));
                box.getChildren().add(new Separator());
                return box;
            }
        };

    }

    public ClassInfoDialog setClassInfo(ClassInfo info){
        this.info=info;
        return this;
    }
    public ClassInfoDialog setParentFactory(DataNukeAbstractGuiFactory parentFactory){
        this.parentFactory=parentFactory;
        return this;
    }

}
