package org.example.prototipo.protoboard;

import javafx.scene.Group;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;

public class Motor extends Pane {

    private Group nodo = new Group();
    double origenX = Main.origenX - 440;
    double origenY = Main.origenY + 280;
    Bateria bateria;
    private Prototipo_Protoboard protoboard;

    Boolean encendido;

    // Constructor de la clase Motor
    public Motor() {

        this.encendido = true;

        // Línea de fondo que representa la base del motor
        Line fondo = new Line(origenX - 50, origenY - 70, origenX + 50, origenY - 70);
        fondo.setStroke(Color.BLACK);
        fondo.setStrokeWidth(70);

        // Línea detrás del fondo para crear un efecto de sombra
        Line fondodetras = new Line(origenX - 48, origenY - 74, origenX + 50, origenY - 74);
        fondodetras.setStroke(Color.GREY);
        fondodetras.setStrokeWidth(74);

        // Línea superior que representa el indicador de encendido/apagado
        Line sobre = new Line(origenX - 60, origenY - 90, origenX + 60, origenY - 90);
        sobre.setStroke(Color.GAINSBORO);
        sobre.setStrokeWidth(20);

        // Botón para encender/apagar el motor
        Cuadrados boton = new Cuadrados(30, 10);
        boton.setX(origenX - 15);
        boton.setY(origenY - 72);
        boton.setFill(Color.DARKGREY);
        boton.toFront();

        // Conector que simboliza la conexión del motor
        Line conector = new Line(origenX + 70, origenY - 112, origenX + 70, origenY - 143);
        conector.setStrokeWidth(10);
        conector.setStroke(Color.DARKGREY);

        // Añadir todos los elementos al nodo
        nodo.getChildren().addAll(fondodetras, fondo, boton, sobre, conector);
        this.getChildren().add(nodo);

        // Evento al hacer clic en el botón para encender/apagar el motor
        boton.setOnMouseClicked(mouseEvent -> {
            if (encendido) {
                System.out.println("Batería Encendida");
                sobre.setStroke(Color.YELLOW);
                bateria.conectorNegativo.setSigno(-1);
                bateria.conectorPositivo.setSigno(1);
                bateria.conectorNegativo.setFill(Color.DARKBLUE);
                bateria.conectorPositivo.setFill(Color.DARKRED);


                // Actualizar el estado de los cables conectados
                for (int i = 0; i < protoboard.getCablesConctados().size(); i++) {
                    Cable cable = protoboard.getCablesConctados().get(i);
                    cable.signoBateria(cable.getInicio().getSigno(), cable.getInicio(), cable.getFin(), cable.line);
                    cable.pintar(cable);
                }
                //protoboard.getCelda1().onOff(0, true);
                //protoboard.getCelda2().onOff(0, true);
                //protoboard.getBus1().onOff(0, true);
                //protoboard.getBus2().onOff(0, true);

                // Notificar a los cables conectados para actualizar su color
                actualizarCablesConectados();
                protoboard.notificarLEDSConectados();
                protoboard.notificarChipsConectados();
            } else {
                System.out.println("Batería Apagada");
                sobre.setStroke(Color.DARKGREY);
                bateria.conectorNegativo.setSigno(0);
                bateria.conectorPositivo.setSigno(0);
                bateria.conectorNegativo.setFill(Color.DARKGREY);
                bateria.conectorPositivo.setFill(Color.DARKGREY);

                // Actualizar el estado de los cables conectados
                for (int i = 0; i < protoboard.getCablesConctados().size(); i++) {
                    Cable cable = protoboard.getCablesConctados().get(i);
                    cable.signoBateria(0, cable.getInicio(), cable.getFin(), cable.line);
                }
                protoboard.getCelda1().onOff(0, false);
                protoboard.getCelda2().onOff(0, false);
                protoboard.getBus1().onOff(0, false);
                protoboard.getBus2().onOff(0, false);

                // Notificar a los cables conectados para actualizar su color
                actualizarCablesConectados();
                protoboard.notificarLEDSConectados();
                protoboard.notificarChipsConectados();
            }
            // Cambiar el estado de encendido
            encendido = !encendido;
        });
    }

    // Método para actualizar los cables conectados a la batería cuando el motor se enciende o apaga
    private void actualizarCablesConectados() {
        // Actualizar cables conectados al terminal positivo
        for (Cable cable : bateria.getCablesConectadosPositivo()) {
            cable.actualizarColorDesdeTerminal(bateria.getConectorPositivo());
        }

        // Actualizar cables conectados al terminal negativo
        for (Cable cable : bateria.getCablesConectadosNegativo()) {
            cable.actualizarColorDesdeTerminal(bateria.getConectorNegativo());
        }
    }

    // Getters y setters para la batería y el protoboard

    public Bateria getBateria() {
        return bateria;
    }

    public void setBateria(Bateria bateria) {
        this.bateria = bateria;
    }

    public Prototipo_Protoboard getProtoboard() {
        return protoboard;
    }

    public void setProtoboard(Prototipo_Protoboard protoboard) {
        this.protoboard = protoboard;
    }
}
