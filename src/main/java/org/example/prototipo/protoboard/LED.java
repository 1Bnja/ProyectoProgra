package org.example.prototipo.protoboard;

import javafx.scene.Group;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.CubicCurve;
import javafx.scene.shape.Line;

public class LED extends Pane {
    private Group nodo = new Group();
    private Line fin1;
    private Line fin2;
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

        fin1 = crearEstirable(pata1);
        fin2 = crearEstirable(pata2);

        // Eventos de arrastre para las líneas rojas
        fin1.setOnMousePressed(e -> empezarArrastre(e, pata1));
        fin1.setOnMouseDragged(e -> arrastrePata(e, pata1, fin1));

        fin2.setOnMousePressed(e -> empezarArrastre(e, pata2));
        fin2.setOnMouseDragged(e -> arrastrePata(e, pata2, fin2));

        // mover
        nodo.setOnMousePressed(e -> {
            if (!line_en_arrastre) {
                mouseX = e.getSceneX();
                mouseY = e.getSceneY();
            }
        });

        nodo.setOnMouseDragged(e -> {
            if (!line_en_arrastre) {
                double dX = e.getSceneX() - mouseX;
                double dY = e.getSceneY() - mouseY;

                nodo.setLayoutX(nodo.getLayoutX() + dX);
                nodo.setLayoutY(nodo.getLayoutY() + dY);

                mouseX = e.getSceneX();
                mouseY = e.getSceneY();

                actualizarPosiciones();
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

    private void empezarArrastre(MouseEvent event, Line pata) {
        // Indicar que estamos arrastrando una línea
        line_en_arrastre = true;
        mouseX = event.getSceneX();
        mouseY = event.getSceneY();
    }

    private void arrastrePata(MouseEvent event, Line pata, Line estirable) {
        double offsetX = event.getSceneX() - mouseX;
        double offsetY = event.getSceneY() - mouseY;

        pata.setEndX(pata.getEndX() + offsetX);
        pata.setEndY(pata.getEndY() + offsetY);

        actualizarEstirable(estirable, pata);

        mouseX = event.getSceneX();
        mouseY = event.getSceneY();

        line_en_arrastre = false;
    }

    private Line crearEstirable(Line pata) {
        Line esquina = new Line(
                pata.getEndX() - 5, pata.getEndY(),
                pata.getEndX() + 5, pata.getEndY()
        );
        esquina.setStroke(Color.RED);
        esquina.setStrokeWidth(8);
        return esquina;
    }

    private void actualizarEstirable(Line esquina, Line pata){
        esquina.setStartX(pata.getEndX() - 5);
        esquina.setStartY(pata.getEndY());
        esquina.setEndX(pata.getEndX() + 5);
        esquina.setEndY(pata.getEndY());
    }

    private void actualizarPosiciones(){
        actualizarEstirable(fin1, pata1);
        actualizarEstirable(fin2, pata2);
    }
}


