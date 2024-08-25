package org.example.prototipo.protoboard;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.Scene;
import java.io.IOException;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;


public class Main extends Application {

    static double anchoEscena = 1280;
    static double altoEscena = 920;

    static double origenX = anchoEscena/2;
    static double origenY = altoEscena/2;

    @Override
    public void start(Stage primaryStage) throws IOException {

        Group grupo = new Group();

        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/org/example/prototipo/Fondo_builder.fxml"));
        Parent fxmlContent = fxmlLoader.load();

        Prototipo_Protoboard proto = new Prototipo_Protoboard();
        Bateria bateria = new Bateria();
        Swich swich = new Swich();
        LED led = new LED();

        GridPane gridPane = proto.getGridPane();
        Pane pane = new Pane();
        pane.getChildren().add(gridPane);

        //Tamaño cuadrados
        gridPane.setVgap(9);
        gridPane.setHgap(9);

        gridPane.setLayoutX(450);//450
        gridPane.setLayoutY(195);//195

        grupo.getChildren().addAll(proto.getGridPane(), proto, fxmlContent, bateria, swich, led);

        Scene scene= new Scene(grupo, 1280, 840);

        primaryStage.setScene(scene);
        primaryStage.setTitle("Prototipo Proyecto");
        primaryStage.show();

    }

    public static void main(String[] args) {
        launch(args);
    }}

