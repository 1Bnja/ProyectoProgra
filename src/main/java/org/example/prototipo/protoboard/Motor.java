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

    public Motor() {

        this.encendido = true;

        Line fondo = new Line(origenX - 50, origenY - 70, origenX + 50, origenY - 70);
        fondo.setStroke(Color.BLACK);
        fondo.setStrokeWidth(70);

        Line fondodetras = new Line(origenX - 48, origenY - 74, origenX + 50, origenY - 74);
        fondodetras.setStroke(Color.GREY);
        fondodetras.setStrokeWidth(74);

        Line sobre = new Line(origenX - 60, origenY - 90, origenX + 60, origenY - 90);
        sobre.setStroke(Color.GAINSBORO);
        sobre.setStrokeWidth(20);

        Cuadrados boton = new Cuadrados(30, 10);
        boton.setX(origenX - 15);
        boton.setY(origenY - 72);
        boton.setFill(Color.DARKGREY);
        boton.toFront();

        Line conector = new Line(origenX + 70, origenY - 112, origenX + 70, origenY - 143);
        conector.setStrokeWidth(10);
        conector.setStroke(Color.DARKGREY);

        nodo.getChildren().addAll(fondodetras, fondo, boton, sobre, conector);
        this.getChildren().add(nodo);

        boton.setOnMouseClicked(mouseEvent -> {
            if (encendido) {
                System.out.println("Batería Encendida");
                sobre.setStroke(Color.YELLOW);
                bateria.conectorNegativo.setSigno(-1);
                bateria.conectorPositivo.setSigno(1);
                bateria.conectorNegativo.setFill(Color.DARKBLUE);
                bateria.conectorPositivo.setFill(Color.DARKRED);
                for (int i=0; i < protoboard.getCablesConctados().size(); i++){
                    //celda.alternarColumna(protoboard.getCablesConctados().get(i).getInicio().getCol(), 0);
                    protoboard.getCablesConctados().get(i).signoBateria(protoboard.getCablesConctados().get(i).getInicio().getSigno(),protoboard.getCablesConctados().get(i).getInicio(), protoboard.getCablesConctados().get(i).getFin(),protoboard.getCablesConctados().get(i).line);
                }
                protoboard.getCelda1().onOff(0,true);
                protoboard.getCelda2().onOff(0,true);
                protoboard.getBus1().onOff(0,true);
                protoboard.getBus2().onOff(0,true);
                // Notificar a los cables conectados para actualizar su color
                actualizarCablesConectados();
            } else {
                System.out.println("Batería Apagada");
                sobre.setStroke(Color.DARKGREY);
                bateria.conectorNegativo.setSigno(0);
                bateria.conectorPositivo.setSigno(0);
                bateria.conectorNegativo.setFill(Color.DARKGREY);
                bateria.conectorPositivo.setFill(Color.DARKGREY);

                for (int i=0; i < protoboard.getCablesConctados().size(); i++){
                    //celda.alternarColumna(protoboard.getCablesConctados().get(i).getInicio().getCol(), 0);
                    protoboard.getCablesConctados().get(i).signoBateria(0, protoboard.getCablesConctados().get(i).getInicio(), protoboard.getCablesConctados().get(i).getFin() , protoboard.getCablesConctados().get(i).line);


                }
                protoboard.getCelda1().onOff(0, false);
                protoboard.getCelda2().onOff(0, false);
                protoboard.getBus1().onOff(0, false);
                protoboard.getBus2().onOff(0, false);

                // Notificar a los cables conectados para actualizar su color
                actualizarCablesConectados();
            }
            encendido = !encendido;
        });
    }

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
