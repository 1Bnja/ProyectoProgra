package org.example.prototipo.protoboard;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;

import java.awt.event.MouseEvent;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;


public class Controller_Builder {


    @FXML
    private AnchorPane Anchor_PanelFondo;

    @FXML
    private Button Boton_Cable, Boton_Led, Boton_Switch, Boton_Bateria, Boton_Eliminar, Proto, Boton_Resistencia, Boton_Chip, Boton_Switch8;


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

        Cable cable = new Cable(50, 50, 150, 150);
        cable.toFront();
        agregar(cable);




        for (int i = 0 ; i < elementos.size() ; i++) { //se busca en la lista de elementos agregados
            if (elementos.get(i) instanceof Prototipo_Protoboard) { //Se busca un protoboard
                cable.setProtoboard((Prototipo_Protoboard)elementos.get(i)); //si lo encuentra se setea en el cable
            }
            if (elementos.get(i) instanceof Bateria) { //busca una bateria
                cable.setBateria((Bateria) elementos.get(i)); //si lo encuentra se setea en el cable
            }

        }

    }

    @FXML
    void Click_Chip(ActionEvent event) {
        System.out.println("Se ha agregado un chip");

        Chip chip= new Chip();
        chip.toFront();
        agregar(chip);
    }

    @FXML
    void Click_Led(ActionEvent event) {
        System.out.println("Se ha agregado un led");
        LED led = new LED();
        led.toFront();
        agregar(led); // Agrega el LED a la escena y a la lista 'elementos'


        // Buscar el protoboard en la lista de elementos
        for (Node elemento : elementos) {
            if (elemento instanceof Prototipo_Protoboard) {
                Prototipo_Protoboard protoboard = (Prototipo_Protoboard) elemento;
                led.setProtoboard(protoboard);
                protoboard.agregarComponenteConectado(led);
                break;
            }
        }
    }

    @FXML
    void Click_Resistencia(ActionEvent event) {
        System.out.println("Se ha agregado una resistencia");

        Dialog<Double> dialog = new Dialog<>();
        dialog.setTitle("Ingresar valor de resistencia");

        TextField inputValor = new TextField();
        ComboBox<String> unidades = new ComboBox<>();
        unidades.getItems().addAll("Ω", "kΩ", "MΩ");
        unidades.setValue("Ω");

        VBox vbox = new VBox(new Label("Valor de Resistencia:"), inputValor, new Label("Unidades:"), unidades);
        dialog.getDialogPane().setContent(vbox);

        inputValor.setOnKeyPressed(keyEvent -> {
            if (keyEvent.getCode().toString().equals("ENTER")) {
                String valorTexto = inputValor.getText();
                if (!valorTexto.isEmpty()) {
                    try {
                        double valor = Double.parseDouble(valorTexto);
                        String unidad = unidades.getValue();

                        if (unidad.equals("kΩ")) valor *= 1000;
                        else if (unidad.equals("MΩ")) valor *= 1_000_000;

                        Resistencia resistencia = new Resistencia(valor);
                        resistencia.toFront();
                        agregar(resistencia);

                        for (int i = 0; i < elementos.size(); i++) {
                            if (elementos.get(i) instanceof Prototipo_Protoboard) {
                                resistencia.setProtoboard((Prototipo_Protoboard)elementos.get(i));
                            }
                        }
                        dialog.setResult(valor);
                        dialog.close();
                    } catch (NumberFormatException e) {
                        System.out.println("Por favor ingresa un valor numérico válido.");
                    }
                } else {
                    System.out.println("El campo de valor está vacío.");
                }
            }
        });
        dialog.showAndWait();
    }


    @FXML
    void Click_Switch(ActionEvent event) {
        System.out.println("Se ha agregado un switch");
        Swich swich = new Swich();
        swich.toFront();
        agregar(swich);



        for (Node elemento : elementos) {
            if (elemento instanceof Prototipo_Protoboard) {
                swich.setProtoboard((Prototipo_Protoboard) elemento);
            }
            if (elemento instanceof LED) {
                swich.setLed((LED) elemento);
            }
        }
    }

    @FXML
    void Click_Switch8(ActionEvent event) {
        System.out.println("Se ha agregado un switch de 8 posiciones");
        Switch_8 switch8 = new Switch_8();
        switch8.toFront();
        agregar(switch8);
        for (Node elemento : elementos) {
            if (elemento instanceof Prototipo_Protoboard) {
                switch8.setProtoboard((Prototipo_Protoboard) elemento);
            }
            if (elemento instanceof LED) {
                switch8.setLed((LED) elemento);
            }
        }
    }

    @FXML
    void Click_Bateria(ActionEvent event) {
        // Corregir boolean
        boolean existe = elementos.stream().anyMatch(nodo -> nodo instanceof Bateria);
        Motor motor= new Motor();
        agregarProto(motor);


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
            motor.setBateria(bateria);
            for (int i = 0 ; i < elementos.size() ; i++) { //se busca en la lista de elementos agregados
                if (elementos.get(i) instanceof Prototipo_Protoboard) { //se busca un cable
                    motor.setProtoboard((Prototipo_Protoboard)elementos.get(i)); //si lo encuentra lo setea en la proto
                }

            }


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



            for (int i = 0 ; i < elementos.size() ; i++) { //se busca en la lista de elementos agregados
                if (elementos.get(i) instanceof Cable) { //se busca un cable
                    ((Cable) elementos.get(i)).setProtoboard(proto); //si lo encuentra lo setea en la proto
                }
                if (elementos.get(i) instanceof LED) { //Se busca un led
                    ((LED) elementos.get(i)).setProtoboard(proto); //si lo encuentra lo setea en el proto
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
