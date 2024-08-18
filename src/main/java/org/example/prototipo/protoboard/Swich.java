package org.example.prototipo.protoboard;

import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.scene.shape.Polygon;
import javafx.scene.Group;
import javafx.scene.input.MouseEvent;


public class Swich extends Pane {

    private Group nodo = new Group();

    private Line fin1, fin2, fin3, fin4;
    private Line pata1, pata2, pata3, pata4;

    // Posición del mouse
    private double mouseX;
    private double mouseY;
    private boolean line_en_arrastre = false;

    double origenX = Main.origenX;
    double origenY = Main.origenY;

    public Swich() {
        // Cuadrado exterior usando líneas
        Line lineaSuperiorCE = new Line(origenX - 500, origenY - 100, origenX - 560, origenY - 100);
        Line lineaInferiorCE = new Line(origenX - 500, origenY - 40, origenX - 560, origenY - 40);
        Line lineaIzquierdaCE = new Line(origenX - 500, origenY - 100, origenX - 500, origenY - 40);
        Line lineaDerechaCE = new Line(origenX - 560, origenY - 100, origenX - 560, origenY - 40);

        // Cuadrado Interno
        Line lineaSuperiorCI = new Line(origenX - 510, origenY - 90, origenX - 550, origenY - 90);
        Line lineaInferiorCI = new Line(origenX - 510, origenY - 50, origenX - 550, origenY - 50);
        Line lineaIzquierdaCI = new Line(origenX - 510, origenY - 90, origenX - 510, origenY - 50);
        Line lineaDerechaCI = new Line(origenX - 550, origenY - 90, origenX - 550, origenY - 50);

        // Patas
        pata1 = new Line(origenX - 505, origenY - 100, origenX - 505, origenY - 107.5);
        pata2 = new Line(origenX - 555, origenY - 100, origenX - 555, origenY - 107.5);
        pata3 = new Line(origenX - 505, origenY - 40, origenX - 505, origenY - 32.5);
        pata4 = new Line(origenX - 555, origenY - 40, origenX - 555, origenY - 32.5);

        // Fondo del cuadrado exterior
        Polygon fondoCuadradoE = new Polygon();
        fondoCuadradoE.getPoints().addAll(
                origenX - 500, origenY - 100,
                origenX - 560, origenY - 100,
                origenX - 560, origenY - 40,
                origenX - 500, origenY - 40
        );
        fondoCuadradoE.setFill(Color.LIGHTGRAY);

        // Fondo del cuadrado interno
        Polygon fondoCuadradoI = new Polygon();
        fondoCuadradoI.getPoints().addAll(
                origenX - 510, origenY - 90,
                origenX - 550, origenY - 90,
                origenX - 550, origenY - 50,
                origenX - 510, origenY - 50
        );
        fondoCuadradoI.setFill(Color.BLACK);

        lineaSuperiorCE.setStroke(Color.BLACK);
        lineaInferiorCE.setStroke(Color.BLACK);
        lineaIzquierdaCE.setStroke(Color.BLACK);
        lineaDerechaCE.setStroke(Color.BLACK);

        lineaSuperiorCI.setStroke(Color.BLACK);
        lineaInferiorCI.setStroke(Color.BLACK);
        lineaIzquierdaCI.setStroke(Color.BLACK);
        lineaDerechaCI.setStroke(Color.BLACK);

        fin1 = Esquina_Estirable(pata1.getEndX(), pata1.getEndY());
        fin2 = Esquina_Estirable(pata2.getEndX(), pata2.getEndY());
        fin3 = Esquina_Estirable(pata3.getEndX(), pata3.getEndY());
        fin4 = Esquina_Estirable(pata4.getEndX(), pata4.getEndY());

        // Eventos de arrastre para las líneas rojas
        fin1.setOnMousePressed(e -> Empezar_arrastre(e, pata1));
        fin1.setOnMouseDragged(e -> Arrastre(e, pata1));

        fin2.setOnMousePressed(e -> Empezar_arrastre(e, pata2));
        fin2.setOnMouseDragged(e -> Arrastre(e, pata2));

        fin3.setOnMousePressed(e -> Empezar_arrastre(e, pata3));
        fin3.setOnMouseDragged(e -> Arrastre(e, pata3));

        fin4.setOnMousePressed(e -> Empezar_arrastre(e, pata4));
        fin4.setOnMouseDragged(e -> Arrastre(e, pata4));

        // Mover el nodo completo
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

        // Agregar los elementos al grupo
        nodo.getChildren().addAll(
                fondoCuadradoE, fondoCuadradoI, lineaSuperiorCE, lineaInferiorCE,
                lineaIzquierdaCE, lineaDerechaCE, lineaSuperiorCI, lineaInferiorCI,
                lineaIzquierdaCI, lineaDerechaCI, pata1, pata2, pata3, pata4, fin1, fin2, fin3, fin4
        );

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

        line.setEndX(line.getEndX() + offsetX);
        line.setEndY(line.getEndY() + offsetY);

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

        fin3.setStartX(pata3.getEndX() - 5);
        fin3.setStartY(pata3.getEndY());
        fin3.setEndX(pata3.getEndX() + 5);
        fin3.setEndY(pata3.getEndY());

        fin4.setStartX(pata4.getEndX() - 5);
        fin4.setStartY(pata4.getEndY());
        fin4.setEndX(pata4.getEndX() + 5);
        fin4.setEndY(pata4.getEndY());
    }
}
