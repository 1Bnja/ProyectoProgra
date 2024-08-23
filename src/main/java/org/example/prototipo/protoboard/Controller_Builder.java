package org.example.prototipo.protoboard;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;

public class Controller_Builder {

    @FXML
    private AnchorPane Anchor_PanelFondo;

    @FXML
    private Button Boton_Cable;

    @FXML
    private Button Boton_Led;

    @FXML
    private Button Boton_Switch;

    @FXML
    private Button Boton_Bateria;

    @FXML
    private Label Lebel_Agregar;


    @FXML
    void Click_Cable(ActionEvent event) {
        System.out.println("Se ha agregado un cable");
        Cable cable= new Cable(Anchor_PanelFondo);

    }

    @FXML
    void Click_Led(ActionEvent event) {
        System.out.println("Se ha agregado un led");
        LED led = new LED();
        Anchor_PanelFondo.getChildren().add(led);

    }

    @FXML
    void Click_Switch(ActionEvent event) {
        System.out.println("Se ha agregado un switch");
        Swich swich = new Swich();
        Anchor_PanelFondo.getChildren().add(swich);

    }

    @FXML
    void Click_Bateria(ActionEvent event) {
        System.out.println("Se ha agregado un bateria");
        Bateria bateria = new Bateria();
        Anchor_PanelFondo.getChildren().add(bateria);
    }

    @FXML
    void Click_Eliminar(ActionEvent event) {
        System.out.println("listop");
    }

}
