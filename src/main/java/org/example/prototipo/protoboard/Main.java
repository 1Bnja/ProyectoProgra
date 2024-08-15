package org.example.prototipo.protoboard;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import  java.io.IOException;

import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import org.example.prototipo.HelloApplication;

public class Main extends Application {

    static double anchoEscena = 1280;
    static double altoEscena = 920;

    static double origenX = anchoEscena / 2;
    static double origenY = altoEscena / 2;

    @Override
    public void start(Stage primaryStage) throws IOException {

        Prototipo_Protoboard proto = new Prototipo_Protoboard();
        Bateria bateria = new Bateria();

        Pane root = new Pane();
        root.getChildren().addAll(proto, bateria);

        Scene scene= new Scene(root, 1280, 840);

        primaryStage.setScene(scene);
        primaryStage.setTitle("Prototipo Proyecto");
        primaryStage.show();

    }

    public static void main(String[] args) {
        launch(args);
    }}

