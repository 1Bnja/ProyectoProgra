package org.example.prototipo.protoboard;

// JDK 22.0.2
// IDE: 21.0.3+13-b509.4

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.Scene;

import java.awt.*;
import java.io.IOException;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.scene.control.ScrollPane;

public class Main extends Application {

    // Definición de las dimensiones de la escena
    static double anchoEscena = 1280;
    static double altoEscena = 920;

    // Coordenadas de origen calculadas a partir de las dimensiones de la escena
    static double origenX = anchoEscena / 2;
    static double origenY = altoEscena / 2;

    @Override
    public void start(Stage primaryStage) throws IOException {

        // Carga el archivo FXML que define la interfaz gráfica
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/org/example/prototipo/Fondo_builder.fxml"));
        Parent fxmlContent = fxmlLoader.load();

        // Crea un panel de fondo y añade el contenido FXML cargado
        Pane fondo = new Pane();
        fondo.getChildren().add(fxmlContent);

        // Crea un ScrollPane para permitir el desplazamiento si el contenido excede el tamaño de la ventana
        ScrollPane scrollPane = new ScrollPane();
        scrollPane.setContent(fondo);

        // Crea la escena con el ScrollPane como contenido principal y establece las dimensiones
        Scene scene = new Scene(scrollPane, 1280, 840);

        // Configura el escenario principal con la escena creada
        primaryStage.setScene(scene);
        primaryStage.setTitle("Prototipo Proyecto");
        primaryStage.show();

    }

    public static void main(String[] args) {
        // Lanza la aplicación JavaFX
        launch(args);
    }
}
