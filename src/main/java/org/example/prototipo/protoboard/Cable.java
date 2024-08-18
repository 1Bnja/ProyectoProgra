package org.example.prototipo.protoboard;

import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;

public class Cable {

    Line line;
    Line inicio, fin;

    public Cable(Pane root) {
        Crear_linea(root);
    }

    public void Crear_linea(Pane root) {

        // Crear línea
        line = new Line(50, 50, 100, 100);
        line.setStroke(Color.BLUE);
        line.setStrokeWidth(7);

        // Crear puntos de arrastre
        inicio = Esquina_Estirable(line.getStartX(), line.getStartY());
        fin = Esquina_Estirable(line.getEndX(), line.getEndY());

        // Añadir manejadores de eventos de arrastre
        inicio.setOnMouseDragged(e -> Arrastre(e, line, true));
        fin.setOnMouseDragged(e -> Arrastre(e, line, false));

        // Añadir línea y puntos al panel
        root.getChildren().addAll(line, inicio, fin);
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

        // Actualizar la posición de los puntos de arrastre
        Actual_arrastrePuntos();
    }

    private Line Esquina_Estirable(double x, double y) {
        // Crear un "punto" usando una línea
        Line point = new Line(x , y, x , y); // Horizontal
        point.setStroke(Color.RED);
        point.setStrokeWidth(8);
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