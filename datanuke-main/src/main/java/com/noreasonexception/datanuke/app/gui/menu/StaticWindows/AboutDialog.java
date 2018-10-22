package com.noreasonexception.datanuke.app.gui.menu.staticWindows;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.Separator;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class AboutDialog extends Application {
    @Override
    public void start(Stage primaryStage) throws Exception {
        primaryStage.getIcons().add(new Image("file:logos/faviconhd.png"));

        FlowPane p = new FlowPane();
        p.setAlignment(Pos.CENTER);


        p.getChildren().add(new Label("",new ImageView(new Image("file:logos/about.png"))));
        p.getChildren().add(getTextSection());

        primaryStage.setScene(new Scene(p,500,250));
        primaryStage.show();
    }
    private Node getTextSection(){

        VBox textPane=new VBox();
        textPane.setPadding(new Insets(25,25,25,25));
        Label programmName =null;
        Label versionDialog=null;
        Label generalInfoText=null;



        textPane.getChildren().add(programmName=new Label("DataNuke v2.1"));
        programmName.setPadding(new Insets(0,0,0,10));
        programmName.setStyle("-fx-font-size: 25px;");
        textPane.getChildren().add(versionDialog=new Label("Economic Calendar bot"));
        versionDialog.setStyle("-fx-font-size: 15px;");

        versionDialog.setPadding(new Insets(0,0,0,20));
        textPane.getChildren().add(new Separator());

        textPane.getChildren().add(generalInfoText=new Label("Copyright 2018-2020 by Stefanos D. Stefanou"));

        return textPane;
    }
}
