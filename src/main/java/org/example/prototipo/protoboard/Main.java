package org.example.prototipo.protoboard;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.Scene;
import  java.io.IOException;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;


public class Main extends Application {

    static double anchoEscena = 1280;
    static double altoEscena = 840;

    static double origenX = anchoEscena/2;
    static double origenY = altoEscena/2;

    @Override
    public void start(Stage primaryStage) throws IOException {

        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/org/example/prototipo/Fondo_builder.fxml"));
        Parent fxmlContent = fxmlLoader.load();

        Pane fondo= new Pane();
        fondo.getChildren().add(fxmlContent);

        Scene scene = new Scene(fondo,1280, 840);

        primaryStage.setScene(scene);
        primaryStage.setTitle("Prototipo Proyecto");
        primaryStage.show();

    }

    public static void main(String[] args) {
        launch(args);
    }
}

