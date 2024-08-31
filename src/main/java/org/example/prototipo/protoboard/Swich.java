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
        double achicar = 0.7; // Factor para achicar la figura

        // Cuadrado exterior usando líneas
        Line lineaSuperiorCE = crearLinea(origenX - 500 * achicar, origenY - 100 * achicar, origenX - 560 * achicar, origenY - 100 * achicar);
        Line lineaInferiorCE = crearLinea(origenX - 500 * achicar, origenY - 40 * achicar, origenX - 560 * achicar, origenY - 40 * achicar);
        Line lineaIzquierdaCE = crearLinea(origenX - 500 * achicar, origenY - 100 * achicar, origenX - 500 * achicar, origenY - 40 * achicar);
        Line lineaDerechaCE = crearLinea(origenX - 560 * achicar, origenY - 100 * achicar, origenX - 560 * achicar, origenY - 40 * achicar);

        // Cuadrado Interno
        Line lineaSuperiorCI = crearLinea(origenX - 510 * achicar, origenY - 90 * achicar, origenX - 550 * achicar, origenY - 90 * achicar);
        Line lineaInferiorCI = crearLinea(origenX - 510 * achicar, origenY - 50 * achicar, origenX - 550 * achicar, origenY - 50 * achicar);
        Line lineaIzquierdaCI = crearLinea(origenX - 510 * achicar, origenY - 90 * achicar, origenX - 510 * achicar, origenY - 50 * achicar);
        Line lineaDerechaCI = crearLinea(origenX - 550 * achicar, origenY - 90 * achicar, origenX - 550 * achicar, origenY - 50 * achicar);

        // Patas
        pata1 = crearLinea(origenX - 505 * achicar, origenY - 100 * achicar, origenX - 505 * achicar, origenY - 107.5 * achicar);
        pata2 = crearLinea(origenX - 555 * achicar, origenY - 100 * achicar, origenX - 555 * achicar, origenY - 107.5 * achicar);
        pata3 = crearLinea(origenX - 505 * achicar, origenY - 40 * achicar, origenX - 505 * achicar, origenY - 32.5 * achicar);
        pata4 = crearLinea(origenX - 555 * achicar, origenY - 40 * achicar, origenX - 555 * achicar, origenY - 32.5 * achicar);

        // Fondo del cuadrado exterior
        Polygon fondoCuadradoE = crearFondo(origenX - 500 * achicar, origenY - 100 * achicar, origenX - 560 * achicar, origenY - 40 * achicar, Color.LIGHTGRAY);
        Polygon fondoCuadradoI = crearFondo(origenX - 510 * achicar, origenY - 90 * achicar, origenX - 550 * achicar, origenY - 50 * achicar, Color.BLACK);

        fin1 = Esquina_Estirable(pata1);
        fin2 = Esquina_Estirable(pata2);
        fin3 = Esquina_Estirable(pata3);
        fin4 = Esquina_Estirable(pata4);

        configurarArrastre(fin1, pata1);
        configurarArrastre(fin2, pata2);
        configurarArrastre(fin3, pata3);
        configurarArrastre(fin4, pata4);

        // Mover el nodo completo
        configurarArrastreNodo();

        // Agregar los elementos al grupo
        nodo.getChildren().addAll(
                fondoCuadradoE, fondoCuadradoI, lineaSuperiorCE, lineaInferiorCE,
                lineaIzquierdaCE, lineaDerechaCE, lineaSuperiorCI, lineaInferiorCI,
                lineaIzquierdaCI, lineaDerechaCI, pata1, pata2, pata3, pata4, fin1, fin2, fin3, fin4
        );

        this.getChildren().add(nodo);

        this.setPickOnBounds(false);
    }

    private Line crearLinea(double startX, double startY, double endX, double endY) {
        Line linea = new Line(startX, startY, endX, endY);
        linea.setStroke(Color.BLACK);
        return linea;
    }

    private Polygon crearFondo(double startX, double startY, double endX, double endY, Color color) {
        Polygon fondo = new Polygon();
        fondo.getPoints().addAll(
                startX, startY,
                endX, startY,
                endX, endY,
                startX, endY
        );
        fondo.setFill(color);
        return fondo;
    }

    private Line Esquina_Estirable(Line pata) {
        Line point = new Line(
                pata.getEndX() - 5, pata.getEndY(),
                pata.getEndX() + 5, pata.getEndY()
        );
        point.setStroke(Color.RED);
        point.setStrokeWidth(8);
        return point;
    }

    private void configurarArrastre(Line estirable, Line pata) {
        estirable.setOnMousePressed(e -> {
            empezarArrastre(e, pata);
            nodo.toFront();
        });
        estirable.setOnMouseDragged(e -> Arrastre(e, pata, estirable));
    }

    private void configurarArrastreNodo() {
        nodo.setOnMousePressed(e -> {
            if (!line_en_arrastre) {
                nodo.toFront();
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
    }

    private void empezarArrastre(MouseEvent event, Line pata) {
        line_en_arrastre = true;
        mouseX = event.getSceneX();
        mouseY = event.getSceneY();
    }

    private void Arrastre(MouseEvent event, Line line, Line estirable) {
        double offsetX = event.getSceneX() - mouseX;
        double offsetY = event.getSceneY() - mouseY;

        line.setEndX(line.getEndX() + offsetX);
        line.setEndY(line.getEndY() + offsetY);

        actualizarEstirable(estirable, line);

        mouseX = event.getSceneX();
        mouseY = event.getSceneY();

        line_en_arrastre = false;
    }

    private void actualizarEstirable(Line estirable, Line pata) {
        estirable.setStartX(pata.getEndX() - 5);
        estirable.setStartY(pata.getEndY());
        estirable.setEndX(pata.getEndX() + 5);
        estirable.setEndY(pata.getEndY());
    }

    private void actualizarPosiciones(){
        actualizarEstirable(fin1, pata1);
        actualizarEstirable(fin2, pata2);
        actualizarEstirable(fin3, pata3);
        actualizarEstirable(fin4, pata4);
    }
}
