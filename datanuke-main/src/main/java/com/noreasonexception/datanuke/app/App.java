package com.noreasonexception.datanuke.app;
import com.noreasonexception.datanuke.app.factory.DataNukeAbstractFactory;
import com.noreasonexception.datanuke.app.factory.DataNukeDefaultFactory;
import com.noreasonexception.datanuke.app.gui.dialog.GeneralExceptionDialog;
import com.noreasonexception.datanuke.app.gui.dialog.SaveDialog;
import com.noreasonexception.datanuke.app.gui.factory.DataNukeAbstractGuiFactory;
import com.noreasonexception.datanuke.app.gui.factory.DataNukeDefaultGuiFactory;

import com.noreasonexception.datanuke.app.threadRunner.AbstractThreadRunner;
import com.noreasonexception.datanuke.app.threadRunner.ThreadRunnerTaskListener;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;


public class App extends Application {
    DataNukeAbstractFactory coreFactory=null;
    DataNukeAbstractGuiFactory guiFactory=null;
    public static void main(String[] args) throws Exception {
        launch(args);

    }

    @Override
    public void init() throws Exception {
        coreFactory=new DataNukeDefaultFactory().loadDefaultConfiguration();
        guiFactory=new DataNukeDefaultGuiFactory(coreFactory);
    }

    @Override
    public void stop() throws Exception {
        super.stop();
        AbstractThreadRunner th;
        if((th=coreFactory.getThreadRunner())!=null){
            coreFactory.getThreadRunner().dismiss();
        }
    }

    @Override
    public void start(Stage primaryStage) throws Exception {

        BorderPane p = new BorderPane();
        Scene scene;
        primaryStage.setMinHeight(350);
        primaryStage.setMinWidth(850);
        p.setTop(guiFactory.getTopBorder());
        p.setLeft(guiFactory.getLeftBorder());
        p.setRight(guiFactory.getRightBorder());
        p.setCenter(guiFactory.getCenterBorder());
        p.setBottom(guiFactory.getBottomBorder());
        primaryStage.setScene(scene=new Scene(p,600,400));
        primaryStage.setTitle("DataNuke");
        primaryStage.getIcons().add(new Image("file:logos/faviconhd.png"));
        coreFactory.getThreadRunner().subscribeTaskListener(getCoreExceptionlistener());
        primaryStage.show();
    }
    public ThreadRunnerTaskListener getCoreExceptionlistener(){
        return new ThreadRunnerTaskListener() {
            public void callGeneralExceptionGuiDialog(String classname,Throwable e){
                Platform.runLater(()->{
                    new GeneralExceptionDialog(classname,e).show();
                });
            }
            @Override
            public void onClassLoadingFailed(String classname, Throwable e, Object[] args) {
                callGeneralExceptionGuiDialog(classname,e);
            }

            @Override
            public void onClassInstanceCreatedFailed(String classname, Throwable e, Object[] args) {
                callGeneralExceptionGuiDialog(classname,e);
            }

            @Override
            public void onTaskThreadValueRetrievedFailed(String classname, Throwable e, Object[] args) {
                callGeneralExceptionGuiDialog(classname,e);
            }
        };
    }

}
