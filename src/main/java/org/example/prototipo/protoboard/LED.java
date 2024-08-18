package org.example.prototipo.protoboard;

import javafx.scene.Group;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.CubicCurve;
import javafx.scene.shape.Line;

public class LED extends Pane {
    private Group nodo = new Group();
    private Line fin1 = new Line();
    private Line fin2 = new Line();
    private Line pata1;
    private Line pata2;

    // Variables para la posición del mouse
    private double mouseX;
    private double mouseY;
    private boolean line_en_arrastre = false;

    double origenX = Main.origenX;
    double origenY = Main.origenY;

    public LED() {
        // LED
        Line led1 = new Line(origenX - 550, origenY - 150, origenX - 515, origenY - 150);
        CubicCurve curva1 = new CubicCurve(origenX - 550, origenY - 150, origenX - 549.25, origenY - 200, origenX - 515.75, origenY - 200, origenX - 515, origenY - 150);
        pata1 = new Line(origenX - 545, origenY - 150, origenX - 545, origenY - 135);
        pata2 = new Line(origenX - 520, origenY - 150, origenX - 520, origenY - 135);

        fin1 = Esquina_Estirable(pata1.getEndX(), pata1.getEndY());
        fin2 = Esquina_Estirable(pata2.getEndX(), pata2.getEndY());

        // Eventos de arrastre para las líneas rojas
        fin1.setOnMousePressed(e -> Empezar_arrastre(e, pata1));
        fin1.setOnMouseDragged(e -> Arrastre(e, pata1));

        fin2.setOnMousePressed(e -> Empezar_arrastre(e, pata2));
        fin2.setOnMouseDragged(e -> Arrastre(e, pata2));

        // mover
        nodo.setOnMousePressed(e -> {
            if (!line_en_arrastre) {
                mouseX = e.getSceneX() - nodo.getLayoutX();
                mouseY = e.getSceneY() - nodo.getLayoutY();
            }
        });

        nodo.setOnMouseDragged(e -> {
            if (!line_en_arrastre) {
                nodo.setLayoutX(e.getSceneX() - mouseX);
                nodo.setLayoutY(e.getSceneY() - mouseY);
            }
        });

        led1.setStroke(Color.BLACK);
        curva1.setStroke(Color.BLACK);
        pata1.setStroke(Color.BLACK);
        pata2.setStroke(Color.BLACK);
        curva1.setFill(Color.LIGHTBLUE);

        nodo.getChildren().addAll(led1, curva1, pata1, pata2, fin1, fin2);
        this.getChildren().add(nodo);
    }

    private void Empezar_arrastre(MouseEvent event, Line line) {
        // Indicar que estamos arrastrando una línea
        line_en_arrastre = true;
        mouseX = event.getSceneX();
        mouseY = event.getSceneY();
    }

    private void Arrastre(MouseEvent event, Line line) {
        double offsetX = event.getSceneX() - mouseX;
        double offsetY = event.getSceneY() - mouseY;

        if (line.equals(pata1)) {
            line.setEndX(line.getEndX() + offsetX);
            line.setEndY(line.getEndY() + offsetY);
            fin1.setEndX(fin1.getEndX() + offsetX);
            fin1.setEndY(fin1.getEndY() + offsetY);
        } else if (line.equals(pata2)) {
            line.setEndX(line.getEndX() + offsetX);
            line.setEndY(line.getEndY() + offsetY);
            fin2.setEndX(fin2.getEndX() + offsetX);
            fin2.setEndY(fin2.getEndY() + offsetY);
        }

        mouseX = event.getSceneX();
        mouseY = event.getSceneY();

        Actual_arrastrePuntos(); // Actualizar los puntos de arrastre
    }

    private Line Esquina_Estirable(double x, double y) {
        Line point = new Line(x, y, x, y);
        point.setStroke(Color.RED);
        point.setStrokeWidth(8);
        return point;
    }

    private void Actual_arrastrePuntos() {
        fin1.setStartX(pata1.getEndX() - 5);
        fin1.setStartY(pata1.getEndY());
        fin1.setEndX(pata1.getEndX() + 5);
        fin1.setEndY(pata1.getEndY());

        fin2.setStartX(pata2.getEndX() - 5);
        fin2.setStartY(pata2.getEndY());
        fin2.setEndX(pata2.getEndX() + 5);
        fin2.setEndY(pata2.getEndY());
    }
}