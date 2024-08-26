package org.example.prototipo.protoboard;

import javafx.scene.Group;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;

public class Cable extends Pane {

    Line line;
    Line inicio, fin;
    private Group nodo = new Group();

    private double mouseX, mouseY;

    public void Crear_linea() {

        // Crear línea
        line = new Line(50, 50, 100, 100);
        line.setStroke(Color.BURLYWOOD);
        line.setStrokeWidth(7);

        // Crear puntos de arrastre
        inicio = Esquina_Estirable(line.getStartX(), line.getStartY());
        fin = Esquina_Estirable(line.getEndX(), line.getEndY());

        // Añadir manejadores de eventos de arrastre
        inicio.setOnMouseDragged(e -> Arrastre(e, line, true));
        fin.setOnMouseDragged(e -> Arrastre(e, line, false));

        // Mover linea completa
        line.setOnMousePressed(this::IniciarMovimientoLinea);
        line.setOnMouseDragged(this::MoverLineaCompleta);

        line.setOnMousePressed(e -> {
            nodo.toFront(); // Mover al frente al iniciar el movimiento
            IniciarMovimientoLinea(e);
        });

        // Añadir línea y puntos al panel
        nodo.getChildren().addAll(line, inicio, fin);
        this.getChildren().add(nodo);

        this.setPickOnBounds(false);
    }

    private void IniciarMovimientoLinea(MouseEvent event) {
        mouseX = event.getX();
        mouseY = event.getY();
    }

    private void MoverLineaCompleta(MouseEvent event) {
        double offsetX = event.getX() - mouseX;
        double offsetY = event.getY() - mouseY;

        line.setStartX(line.getStartX() + offsetX);
        line.setStartY(line.getStartY() + offsetY);
        line.setEndX(line.getEndX() + offsetX);
        line.setEndY(line.getEndY() + offsetY);

        Actual_arrastrePuntos();

        mouseX = event.getX();
        mouseY = event.getY();
    }

    private void Arrastre(MouseEvent event, Line line, boolean isStartPoint) {
        double offsetX = event.getX();
        double offsetY = event.getY();

        if (isStartPoint) {
            line.setStartX(offsetX);
            line.setStartY(offsetY);
        } else {
            line.setEndX(offsetX);
            line.setEndY(offsetY);
        }

        Actual_arrastrePuntos();
    }

    private Line Esquina_Estirable(double x, double y) {
        Line point = new Line(x, y, x, y);
        point.setStroke(Color.BROWN);
        point.setStrokeWidth(8);
        point.setOnMousePressed(e -> {
            nodo.toFront();
        });
        return point;
    }

    private void Actual_arrastrePuntos() {
        // Actualizar la posición de los puntos de arrastre
        inicio.setStartX(line.getStartX() - 5);
        inicio.setStartY(line.getStartY());
        inicio.setEndX(line.getStartX() + 5);
        inicio.setEndY(line.getStartY());

        fin.setStartX(line.getEndX() - 5);
        fin.setStartY(line.getEndY());
        fin.setEndX(line.getEndX() + 5);
        fin.setEndY(line.getEndY());
    }
}