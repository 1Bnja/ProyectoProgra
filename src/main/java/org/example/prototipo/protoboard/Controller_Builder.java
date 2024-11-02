package org.example.prototipo.protoboard;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import java.io.OutputStream;
import java.io.PrintStream;
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

    @FXML
    private TextArea TextArea;

    // Lista para almacenar todos los elementos agregados al panel
    private List<Node> elementos = new ArrayList<>();

    // Elemento actualmente seleccionado
    private Node elemento_seleccionado;

    // Coordenadas de origen (posiblemente para posicionamiento inicial)
    double origenX = Main.origenX;
    double origenY = Main.origenY;



    public void initialize() {
        // Redirigir el System.out al TextArea
        PrintStream ps = new PrintStream(new OutputStream() {
            @Override
            public void write(int b) {
                // Agrega el carácter al TextArea
                TextArea.appendText(String.valueOf((char) b));
            }
        });
        System.setOut(ps);
    }




    // Método para agregar un elemento al panel y a la lista de elementos
    private void agregar(Node elemento) {
        elementos.add(elemento);
        Anchor_PanelFondo.getChildren().add(elemento);
        seleccionar(elemento); // Permitir selección del elemento
    }

    // Método específico para agregar el protoboard al panel y enviarlo al fondo
    private void agregarProto(Node elemento) {
        elementos.add(elemento);
        Anchor_PanelFondo.getChildren().add(elemento);
        elemento.toBack(); // Envía el protoboard al fondo para que otros elementos estén encima
    }

    // Método para configurar la selección de un elemento al hacer clic
    private void seleccionar(Node elemento) {
        elemento.setOnMouseClicked(mouseEvent -> {
            elemento_seleccionado = elemento; // Establece el elemento como seleccionado
            elemento.toFront(); // Trae el elemento al frente
        });
    }

    // Acción al hacer clic en el botón para agregar un Cable
    @FXML
    void Click_Cable(ActionEvent event) {
        System.out.println("Se ha agregado un cable");

        // Crear un nuevo cable con posiciones iniciales
        Cable cable = new Cable(50, 50, 150, 150);
        cable.toFront(); // Traer el cable al frente
        agregar(cable); // Agregar el cable al panel y a la lista

        // Asignar el protoboard y la batería al cable si existen
        for (int i = 0 ; i < elementos.size() ; i++) {
            if (elementos.get(i) instanceof Prototipo_Protoboard) {
                cable.setProtoboard((Prototipo_Protoboard)elementos.get(i));
            }
            if (elementos.get(i) instanceof Bateria) {
                cable.setBateria((Bateria) elementos.get(i));
            }
        }
    }

    // Acción al hacer clic en el botón para agregar un Chip
    @FXML
    void Click_Chip(ActionEvent event) {
        System.out.println("Se ha agregado un chip");
        Chip chip = new Chip();
        agregar(chip);
        chip.toFront();

        // Asignar el protoboard al chip si existe
        for (Node elemento : elementos) {
            if (elemento instanceof Prototipo_Protoboard) {
                Prototipo_Protoboard protoboard = (Prototipo_Protoboard) elemento;
                chip.setProtoboard(protoboard);
                break;
            }
        }
    }

    // Acción al hacer clic en el botón para agregar un LED
    @FXML
    void Click_Led(ActionEvent event) {
        System.out.println("Se ha agregado un led");

        // Crear una lista de colores
        List<String> colores = new ArrayList<>();
        colores.add("Rojo");
        colores.add("Verde");
        colores.add("Azul");
        colores.add("Amarillo");

        // Crear un diálogo para que el usuario elija un color
        ChoiceDialog<String> dialog = new ChoiceDialog<>("Rojo", colores);
        dialog.setTitle("Seleccionar Color del LED");
        dialog.setHeaderText("Elige un color para el LED");
        dialog.setContentText("Colores disponibles:");

        // Obtener la selección del usuario
        dialog.showAndWait().ifPresent(colorSeleccionado -> {
            Color colorLed;

            // Asignar el color según la elección del usuario
            switch (colorSeleccionado) {
                case "Verde":
                    colorLed = Color.LIGHTGREEN;
                    break;
                case "Azul":
                    colorLed = Color.LIGHTBLUE;
                    break;
                case "Amarillo":
                    colorLed = Color.LIGHTYELLOW;
                    break;
                case "Rojo":
                default:
                    colorLed = Color.LIGHTCORAL;
                    break;
            }

            // Crear el LED con el color seleccionado
            LED led = new LED(colorLed);
            led.toFront();
            agregar(led); // Agrega el LED al panel y a la lista de elementos

            // Buscar y asignar el protoboard al LED si existe
            for (Node elemento : elementos) {
                if (elemento instanceof Prototipo_Protoboard) {
                    Prototipo_Protoboard protoboard = (Prototipo_Protoboard) elemento;
                    led.setProtoboard(protoboard);
                    protoboard.agregarComponenteConectado(led);
                    break;
                }
            }
        });
    }


    // Acción al hacer clic en el botón para agregar una Resistencia
    @FXML
    void Click_Resistencia(ActionEvent event) {
        System.out.println("Se ha agregado una resistencia");

        // Crear un diálogo para ingresar el valor de la resistencia
        Dialog<Double> dialog = new Dialog<>();
        dialog.setTitle("Ingresar valor de resistencia");

        TextField inputValor = new TextField();
        ComboBox<String> unidades = new ComboBox<>();
        unidades.getItems().addAll("Ω", "kΩ", "MΩ");
        unidades.setValue("Ω");

        // Configurar el contenido del diálogo
        VBox vbox = new VBox(new Label("Valor de Resistencia:"), inputValor, new Label("Unidades:"), unidades);
        dialog.getDialogPane().setContent(vbox);

        // Manejar el evento de presionar ENTER en el campo de texto
        inputValor.setOnKeyPressed(keyEvent -> {
            if (keyEvent.getCode().toString().equals("ENTER")) {
                String valorTexto = inputValor.getText();
                if (!valorTexto.isEmpty()) {
                    try {
                        double valor = Double.parseDouble(valorTexto);
                        String unidad = unidades.getValue();

                        // Convertir el valor según la unidad seleccionada
                        if (unidad.equals("kΩ")) valor *= 1000;
                        else if (unidad.equals("MΩ")) valor *= 1_000_000;

                        // Crear y agregar la resistencia
                        Resistencia resistencia = new Resistencia(valor);
                        resistencia.getFin1().setVoltaje(valor);
                        resistencia.getFin2().setVoltaje(valor);
                        resistencia.toFront();
                        agregar(resistencia);

                        // Asignar el protoboard a la resistencia si existe
                        for (int i = 0; i < elementos.size(); i++) {
                            if (elementos.get(i) instanceof Prototipo_Protoboard) {
                                resistencia.setProtoboard((Prototipo_Protoboard)elementos.get(i));
                            }if (elementos.get(i) instanceof Bateria) {
                                resistencia.setBateria((Bateria) elementos.get(i));
                            }if (elementos.get(i) instanceof LED) {
                                resistencia.setLed((LED) elementos.get(i));
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
        dialog.showAndWait(); // Mostrar el diálogo y esperar
    }

    // Acción al hacer clic en el botón para agregar un Switch
    @FXML
    void Click_Switch(ActionEvent event) {
        System.out.println("Se ha agregado un switch");
        Swich swich = new Swich();
        swich.toFront();
        agregar(swich);

        // Asignar el protoboard y el LED al switch si existen
        for (Node elemento : elementos) {
            if (elemento instanceof Prototipo_Protoboard) {
                swich.setProtoboard((Prototipo_Protoboard) elemento);
            }
            if (elemento instanceof LED) {
                swich.setLed((LED) elemento);
            }
        }
    }

    // Acción al hacer clic en el botón para agregar un Switch de 8 posiciones
    @FXML
    void Click_Switch8(ActionEvent event) {
        System.out.println("Se ha agregado un switch de 8 posiciones");
        Switch_8 switch8 = new Switch_8();
        switch8.toFront();
        agregar(switch8);

        // Asignar el protoboard y el LED al switch de 8 posiciones si existen
        for (Node elemento : elementos) {
            if (elemento instanceof Prototipo_Protoboard) {
                switch8.setProtoboard((Prototipo_Protoboard) elemento);
            }
            if (elemento instanceof LED) {
                switch8.setLed((LED) elemento);
            }
        }
    }

    // Acción al hacer clic en el botón para agregar una Batería
    @FXML
    void Click_Bateria(ActionEvent event) {
        boolean existe = elementos.stream().anyMatch(nodo -> nodo instanceof Bateria);

        if (existe) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Información");
            alert.setHeaderText(null);
            alert.setContentText("Ya existe una batería en la pantalla. :p");
            alert.showAndWait();
        } else {
            ChoiceDialog<Integer> dialog = new ChoiceDialog<>(9, 1, 2, 3, 4, 5, 6, 7, 8, 9);
            dialog.setTitle("Seleccionar Voltaje de la Batería");
            dialog.setHeaderText("Elige el voltaje para la batería");
            dialog.setContentText("Voltajes disponibles (1-9V):");

            dialog.showAndWait().ifPresent(voltage -> {
                Bateria bateria = new Bateria();
                bateria.toFront();
                bateria.getConectorPositivo().setVoltaje(voltage);
                bateria.getConectorNegativo().setVoltaje(voltage);

                System.out.println("Se ha agregado una batería de " + voltage + "V");
                agregar(bateria);

                Motor motor = new Motor();
                agregarProto(motor);
                motor.setBateria(bateria);

                for (Node elemento : elementos) {
                    if (elemento instanceof Prototipo_Protoboard) {
                        motor.setProtoboard((Prototipo_Protoboard) elemento);
                    }
                }
            });
        }
    }


    // Acción al hacer clic en el botón para agregar un Protoboard
    @FXML
    void Click_Proto(ActionEvent event) {
        // Verificar si ya existe un protoboard en la lista de elementos
        boolean existe = elementos.stream().anyMatch(nodo -> nodo instanceof Prototipo_Protoboard);

        if (existe) {
            // Mostrar alerta si ya hay un protoboard
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Información");
            alert.setHeaderText(null);
            alert.setContentText("Ya existe un protoboard en la pantalla. :p");
            alert.showAndWait();
        } else {
            // Crear y agregar el protoboard
            Prototipo_Protoboard proto = new Prototipo_Protoboard();
            proto.toFront();
            System.out.println("Se ha agregado un protoboard");
            agregarProto(proto);

            // Asignar el protoboard a los cables y LEDs existentes
            for (int i = 0 ; i < elementos.size() ; i++) {
                if (elementos.get(i) instanceof Cable) {
                    ((Cable) elementos.get(i)).setProtoboard(proto);
                }
                if (elementos.get(i) instanceof LED) {
                    ((LED) elementos.get(i)).setProtoboard(proto);
                }
            }
        }
    }

    // Acción al hacer clic en el botón para eliminar un elemento seleccionado
    @FXML
    void Click_Eliminar(ActionEvent event) {
        System.out.println("Se ha eliminado " + elemento_seleccionado);
        if (elemento_seleccionado != null) {
            Anchor_PanelFondo.getChildren().remove(elemento_seleccionado);
            elementos.remove(elemento_seleccionado);
            elemento_seleccionado = null;
        }
    }

    // Acción al hacer clic en el botón Reset
    @FXML
    void Click_Reset(ActionEvent event) {
        System.out.println("Se ha reiniciado el protoboard");


        for (Node elemento : elementos) {
            Anchor_PanelFondo.getChildren().remove(elemento);
        }

        elementos.clear();

        elemento_seleccionado = null;

        origenX = Main.origenX;
        origenY = Main.origenY;
        
        TextArea.clear();
        TextArea.appendText("El sistema ha sido reiniciado.\n");
    }


}
