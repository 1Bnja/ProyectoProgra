package org.example.prototipo.protoboard;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import java.util.ArrayList;
import java.util.List;

public class Controller_Builder {

    @FXML
    private AnchorPane Anchor_PanelFondo;

    @FXML
    private Button Boton_Cable, Boton_Led, Boton_Switch, Boton_Bateria, Boton_Eliminar, Proto;

    @FXML
    private Label Lebel_Agregar;

    private List<Node> elementos = new ArrayList<>();
    private Node elemento_seleccionado;

    double origenX = Main.origenX;
    double origenY = Main.origenY;

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

        // Crear un nuevo cable con posiciones iniciales
        Cable cable = new Cable(50, 50, 150, 150);

        // Asegurarse de que los cables no interfieren al ser añadidos
        cable.toFront();
        agregar(cable);// Asegúrate de que Anchor_PanelFondo es tu contenedor
        for (int i = 0 ; i < elementos.size() ; i++) {
            if (elementos.get(i) instanceof Prototipo_Protoboard) {
                cable.setProtoboard((Prototipo_Protoboard)elementos.get(i));
            }
            if (elementos.get(i) instanceof Bateria) {
                cable.setBateria((Bateria) elementos.get(i));
            }
        }

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
        // Corregir boolean
        boolean existe = elementos.stream().anyMatch(nodo -> nodo instanceof Bateria);

        if (existe) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Información");
            alert.setHeaderText(null);
            alert.setContentText("Ya existe una batería en la pantalla. :p");
            alert.showAndWait();  // Mostrar el mensaje
        } else {
            Bateria bateria = new Bateria();
            bateria.toFront();
            System.out.println("Se ha agregado una batería");
            agregar(bateria);


        }
    }

    @FXML
    void Click_Proto(ActionEvent event) {
        boolean existe = elementos.stream().anyMatch(nodo -> nodo instanceof Prototipo_Protoboard);

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

            for (int i = 0 ; i < elementos.size() ; i++) {
                if (elementos.get(i) instanceof Cable) {
                    ((Cable) elementos.get(i)).setProtoboard(proto);
                }
            }

        }
    }

    @FXML
    void Click_Eliminar(ActionEvent event) {
        System.out.println("Se ha eliminado " + elemento_seleccionado);
        if (elemento_seleccionado != null) {
            Anchor_PanelFondo.getChildren().remove(elemento_seleccionado);
            elementos.remove(elemento_seleccionado);
            elemento_seleccionado = null;
        }
    }
}
