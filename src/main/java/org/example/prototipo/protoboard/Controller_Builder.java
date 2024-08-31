package org.example.prototipo.protoboard;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import java.util.ArrayList;
import java.util.List;
import javafx.scene.Node;

public class Controller_Builder {

    @FXML
    private AnchorPane Anchor_PanelFondo;

    @FXML
    private Button Boton_Cable, Boton_Led, Boton_Switch, Boton_Bateria, Boton_Eliminar, Proto;

    @FXML
    private Label Lebel_Agregar;

    private List<Node> elementos = new ArrayList<>();
    private Node elemento_seleccionado;

    private void agregar(Node elemento) {
        elementos.add(elemento);
        Anchor_PanelFondo.getChildren().add(elemento);
        seleccionar(elemento);
    }

    private void agregarProto(Node elemento) {
        elementos.add(elemento);
        Anchor_PanelFondo.getChildren().add(elemento);
        elemento.toBack();
    }

    private void seleccionar(Node elemento) {
        elemento.setOnMouseClicked(mouseEvent -> {
            elemento_seleccionado = elemento;
            elemento.toFront();
        });
    }

    @FXML
    void Click_Cable(ActionEvent event) {
        System.out.println("Se ha agregado un cable");
        Cable cable = new Cable();
        cable.Crear_linea();
        cable.toFront();
        agregar(cable);
    }

    @FXML
    void Click_Led(ActionEvent event) {
        System.out.println("Se ha agregado un led");
        LED led = new LED();
        led.toFront();
        agregar(led);
    }

    @FXML
    void Click_Switch(ActionEvent event) {
        System.out.println("Se ha agregado un switch");
        Swich swich = new Swich();
        swich.toFront();
        agregar(swich);

    }

    @FXML
    void Click_Bateria(ActionEvent event) {
        System.out.println("Se ha agregado un bateria");
        Bateria bateria = new Bateria();
        bateria.toFront();
        agregar(bateria);
    }


    @FXML
    void Click_Proto(ActionEvent event) {

        boolean existe = false;

        for (Node nodo : Anchor_PanelFondo.getChildren()) {
            if (nodo instanceof Prototipo_Protoboard) {
                existe = true;
                break;
            }
        }

        if (existe) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Información");
            alert.setHeaderText(null);
            alert.setContentText("Ya existe un protoboard en la pantalla. :p");
            alert.showAndWait();
        } else {
            Prototipo_Protoboard proto = new Prototipo_Protoboard();
            proto.toFront();
            System.out.println("Se ha agregado un protoboard");
            agregarProto(proto);
        }
    }


    @FXML
    void Click_Eliminar(ActionEvent event) {
        System.out.println("se ha eliminado "+elemento_seleccionado);
        if(elemento_seleccionado != null) {
            Anchor_PanelFondo.getChildren().remove(elemento_seleccionado);
            elementos.remove(elemento_seleccionado);
            elemento_seleccionado = null;
        }
    }

}
