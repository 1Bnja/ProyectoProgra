package org.example.prototipo.protoboard;

import javafx.geometry.Bounds;
import javafx.scene.Node;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;

public class Cable extends Pane {

    public Line line;
    private Cuadrados inicio;
    private Cuadrados fin;
    private double mouseX, mouseY;
    Bateria bateria= new Bateria();
    LED led;
    Swich boton;
    Prototipo_Protoboard protoboard;

    public Cable(double startX, double startY, double endX, double endY) {

        line = new Line(startX, startY, endX, endY);
        line.setStroke(Color.GREENYELLOW);
        line.setStrokeWidth(7);

        // Crear los extremos del cable con la clase Cuadrados
        inicio = new Cuadrados(12, 2);
        fin = new Cuadrados(12, 2);
        inicio.setFillColor(Color.RED);
        fin.setFillColor(Color.RED);


        inicio.setX(startX - inicio.getWidth() / 2);
        inicio.setY(startY - inicio.getHeight() / 2);
        fin.setX(endX - fin.getWidth() / 2);
        fin.setY(endY - fin.getHeight() / 2);


        this.setPickOnBounds(false);

        inicio.setOnMouseDragged(event -> arrastrarExtremo(event, true));
        fin.setOnMouseDragged(event -> arrastrarExtremo(event, false));

        line.setOnMousePressed(this::iniciarMovimientoLinea);
        line.setOnMouseDragged(this::moverLineaCompleta);

        inicio.setOnMouseReleased(event ->{

            double mouseX = event.getSceneX();
            double mouseY = event.getSceneY();
            Node arriba = verificarSiEstaEnCelda(mouseX,mouseY,(GridPane) protoboard.getCelda1().getChildren().getFirst());
            Node abajo = verificarSiEstaEnCelda(mouseX,mouseY,(GridPane) protoboard.getCelda2().getChildren().getFirst());
            Node bus_arriba = verificarSiEstaEnCelda(mouseX,mouseY,(GridPane) protoboard.getBus1().getChildren().getFirst());
            Node bus_abajo = verificarSiEstaEnCelda(mouseX,mouseY,(GridPane) protoboard.getBus2().getChildren().getFirst());
            Integer col = 0;
            if(arriba != null) {
                col =  ((GridPane) protoboard.getCelda1().getChildren().getFirst()).getColumnIndex(arriba)-1;
                protoboard.getCelda1().alternarColumna(col);
            }
            if(abajo != null) {
                 col = ((GridPane) protoboard.getCelda2().getChildren().getFirst()).getColumnIndex(abajo)-1;
                 protoboard.getCelda2().alternarColumna(col);
            }
            if(bus_abajo != null) {
                Integer row = ((GridPane) protoboard.getBus2().getChildren().getFirst()).getRowIndex(bus_abajo);
                protoboard.getBus2().toggleFilaBus(row);
            }
            if(bus_arriba != null) {
                Integer row = ((GridPane) protoboard.getBus1().getChildren().getFirst()).getRowIndex(bus_arriba);
                protoboard.getBus1().toggleFilaBus(row);


            }



            if(inicio !=null){
                if(bateria.conectorPositivo.getBoundsInParent().intersects(inicio.getBoundsInParent())){
                    inicio.setFill(bateria.conectorPositivo.getFill());
                    fin.setFill(bateria.conectorPositivo.getFill());
                    line.setStroke(bateria.conectorPositivo.getFillColor());
                } else if (bateria.conectorNegativo.getBoundsInParent().intersects(inicio.getBoundsInParent())){
                    inicio.setFill(bateria.conectorNegativo.getFill());
                    fin.setFill(bateria.conectorNegativo.getFill());
                    line.setStroke(bateria.conectorNegativo.getFillColor());
                }else{
                    System.out.println("no se pudo :p");}
            }
        });

        this.getChildren().addAll(line, inicio, fin);
    }

    private void arrastrarExtremo(MouseEvent event, boolean esInicio) {
        event.consume();  // Consumir el evento para evitar que se propague

        double offsetX = event.getX();
        double offsetY = event.getY();

        if (esInicio) {
            line.setStartX(offsetX);
            line.setStartY(offsetY);
        } else {
            line.setEndX(offsetX);
            line.setEndY(offsetY);
        }


        actualizarPosiciones();
    }


    private void iniciarMovimientoLinea(MouseEvent event) {
        mouseX = event.getX();
        mouseY = event.getY();
    }

    private void moverLineaCompleta(MouseEvent event) {
        event.consume();

        double offsetX = event.getX() - mouseX;
        double offsetY = event.getY() - mouseY;


        line.setStartX(line.getStartX() + offsetX);
        line.setStartY(line.getStartY() + offsetY);
        line.setEndX(line.getEndX() + offsetX);
        line.setEndY(line.getEndY() + offsetY);


        actualizarPosiciones();

        mouseX = event.getX();
        mouseY = event.getY();
    }

    private void actualizarPosiciones() {
        inicio.setX(line.getStartX() - inicio.getWidth() / 2);
        inicio.setY(line.getStartY() - inicio.getHeight() / 2);

        fin.setX(line.getEndX() - fin.getWidth() / 2);
        fin.setY(line.getEndY() - fin.getHeight() / 2);
    }

    public Prototipo_Protoboard getProtoboard() {
        return protoboard;
    }

    public void setProtoboard(Prototipo_Protoboard protoboard) {
        this.protoboard = protoboard;
    }

    public Swich getBoton() {
        return boton;
    }

    public void setBoton(Swich boton) {
        this.boton = boton;
    }

    public LED getLed() {
        return led;
    }

    public void setLed(LED led) {
        this.led = led;
    }

    public Bateria getBateria() {
        return bateria;
    }

    public void setBateria(Bateria bateria) {
        this.bateria = bateria;
    }

    private Node verificarSiEstaEnCelda(double mouseX, double mouseY, GridPane gridPane) {
        for (Node child : gridPane.getChildren()) {
            // Obtener los límites de la celda en coordenadas de la escena
            Bounds boundsInScene = child.localToScene(child.getBoundsInLocal());

            // Verificar si el mouse está dentro de los límites de la celda
            if (boundsInScene.contains(mouseX, mouseY)) {
                Integer row = GridPane.getRowIndex(child);
                Integer col = GridPane.getColumnIndex(child);
                System.out.println("El nodo está sobre la celda en fila: " + row + ", columna: " + col);
                return child;
            }
        }
        return null;
    }
}
