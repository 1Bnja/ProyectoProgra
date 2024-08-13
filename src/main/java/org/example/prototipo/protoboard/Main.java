package org.example.prototipo.protoboard;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import  java.io.IOException;
import javafx.stage.Stage;

import org.example.prototipo.HelloApplication;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws IOException {


        Prototipo_Protoboard proto = new Prototipo_Protoboard();



        Scene scene= new Scene(proto, 1280, 840);

        primaryStage.setScene(scene);
        primaryStage.setTitle("Prototipo Proyecto");
        primaryStage.show();

    }

    public static void main(String[] args) {
        launch(args);
    }}

