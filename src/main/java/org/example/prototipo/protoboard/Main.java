package org.example.prototipo.protoboard;

import javafx.application.Application;
import javafx.scene.Scene;
import  java.io.IOException;
import javafx.stage.Stage;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws IOException {

        Prototipo_Protoboard proto = new Prototipo_Protoboard();

        Scene scene= new Scene(proto);

        primaryStage.setScene(scene);
        primaryStage.setTitle("Prototipo Proyecto");
        primaryStage.setFullScreen(true);
        primaryStage.show();

    }

    public static void main(String[] args) {
        launch(args);
    }}

