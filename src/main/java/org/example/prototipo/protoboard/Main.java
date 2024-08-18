package org.example.prototipo.protoboard;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.Scene;
import  java.io.IOException;
import javafx.stage.Stage;


public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws IOException {

<<<<<<< Updated upstream

        Prototipo_Protoboard proto = new Prototipo_Protoboard();



        Scene scene= new Scene(proto, 1280, 840);
=======
        Group grupo=new Group();

        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/org/example/prototipo/Fondo_builder.fxml"));
        Parent fxmlContent = fxmlLoader.load();

        Prototipo_Protoboard proto = new Prototipo_Protoboard();
        Bateria bateria = new Bateria();


        grupo.getChildren().addAll(proto,bateria );
        grupo.getChildren().add(fxmlContent);



        Scene scene= new Scene(grupo, 1280, 840);
>>>>>>> Stashed changes

        primaryStage.setScene(scene);
        primaryStage.setTitle("Prototipo Proyecto");
        primaryStage.show();

    }

    public static void main(String[] args) {
        launch(args);
    }}

