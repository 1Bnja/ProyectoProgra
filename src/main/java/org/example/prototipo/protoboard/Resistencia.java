package org.example.prototipo.protoboard;

import javafx.geometry.Bounds;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.scene.shape.Polygon;

public class Resistencia extends Pane {
    private Group nodo = new Group();
    private Line pata1, pata2;
    private Line lineaSu, lineaIn, lineaIzq, lineaDer;
    private Line franja1, franja2;
    private Cuadrados fin1, fin2;
    private Polygon fondo;

    private double mouseX, mouseY;
    private boolean line_en_arrastre = false;

    private double origenX = Main.origenX;
    private double origenY = Main.origenY;
    Prototipo_Protoboard protoboard;

    private boolean fin1Conectada = false;
    private boolean fin2Conectada = false;

    private boolean valorResistencia;

    private int signoFin1 = 0;
    private int signoFin2 = 0;

    public Resistencia() {
        lineaSu = crearLinea(origenX - 550, origenY - 150, origenX - 532, origenY - 150, Color.BLACK);
        lineaIn = crearLinea(origenX - 550, origenY - 126, origenX - 532, origenY - 126, Color.BLACK);
        lineaIzq = crearLinea(origenX - 550, origenY - 150, origenX - 550, origenY - 126, Color.BLACK);
        lineaDer = crearLinea(origenX - 532, origenY - 150, origenX - 532, origenY - 126, Color.BLACK);

        pata1 = crearLinea(origenX - 541, origenY - 150, origenX - 541, origenY - 160, Color.BLACK);
        pata2 = crearLinea(origenX - 541, origenY - 126, origenX - 541, origenY - 116, Color.BLACK);

        fondo = crearFondo(origenX - 550, origenY - 150, origenX - 532, origenY - 126, Color.LIGHTGRAY);

        franja1 = crearLinea(origenX - 549, origenY - 142, origenX - 533, origenY - 142, Color.RED);
        franja2 = crearLinea(origenX - 549, origenY - 134, origenX - 533, origenY - 134, Color.GREEN);

        fin1 = crearEstirable(pata1, Color.RED);
        fin2 = crearEstirable(pata2, Color.RED);

        configurarArrastre(fin1, pata1);
        configurarArrastre(fin2, pata2);

        configurarArrastreNodo();

        nodo.getChildren().addAll(fondo, lineaSu, lineaIn, lineaIzq, lineaDer, pata1, pata2, franja1, franja2, fin1, fin2);

        this.getChildren().add(nodo);
        this.setPickOnBounds(false);

    }

    // Dibujo
    private Line crearLinea(double startX, double startY, double endX, double endY, Color color) {
        Line linea = new Line(startX, startY, endX, endY);
        linea.setStroke(color);
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

    private Cuadrados crearEstirable(Line pata, Color color) {
        Cuadrados pin = new Cuadrados(8, 2);
        pin.setFill(color);
        pin.setX(pata.getEndX() - 4);
        pin.setY(pata.getEndY() - 4);
        return pin;
    }

    // Movimiento pines
    private void empezarArrastre(MouseEvent e) {
        line_en_arrastre = true;
        mouseX = e.getSceneX();
        mouseY = e.getSceneY();
    }

    private void arrastrePata(MouseEvent event, Line pata, Cuadrados estirable) {
        double offsetX = event.getSceneX() - mouseX;
        double offsetY = event.getSceneY() - mouseY;

        pata.setEndX(pata.getEndX() + offsetX);
        pata.setEndY(pata.getEndY() + offsetY);

        actualizarEstirable(estirable, pata);

        mouseX = event.getSceneX();
        mouseY = event.getSceneY();

        line_en_arrastre = false;
    }

    private void actualizarEstirable(Cuadrados esquina, Line pata) {
        esquina.setX(pata.getEndX() - 4);
        esquina.setY(pata.getEndY() - 4);
    }

    private void updateFinConnection(Cuadrados estirable) {
        double sceneX = estirable.localToScene(estirable.getBoundsInLocal()).getMinX() + estirable.getWidth() / 2;
        double sceneY = estirable.localToScene(estirable.getBoundsInLocal()).getMinY() + estirable.getHeight() / 2;

        int signoCelda = 0;
        boolean connected = false;

        if (protoboard != null) {
            Node celdaEncontrada = null;
            GridPane[] gridPanes = {
                    (GridPane) protoboard.getCelda1().getChildren().get(0),
                    (GridPane) protoboard.getCelda2().getChildren().get(0),
                    (GridPane) protoboard.getBus1().getChildren().get(0),
                    (GridPane) protoboard.getBus2().getChildren().get(0)
            };

            for (GridPane gridPane : gridPanes) {
                celdaEncontrada = verificarSiEstaEnCelda(sceneX, sceneY, gridPane);
                if (celdaEncontrada != null) {
                    int col = GridPane.getColumnIndex(celdaEncontrada) - 1;
                    int row = GridPane.getRowIndex(celdaEncontrada);

                    if (gridPane == gridPanes[0]) {
                        signoCelda = protoboard.getCelda1().getSigno(row, col);
                    } else if (gridPane == gridPanes[1]) {
                        signoCelda = protoboard.getCelda2().getSigno(row, col);
                    } else if (gridPane == gridPanes[2]) {
                        signoCelda = protoboard.getBus1().getSigno(row, col);
                    } else if (gridPane == gridPanes[3]) {
                        signoCelda = protoboard.getBus2().getSigno(row, col);
                    }
                    connected = true;
                    break;
                }
            }

            if (!connected) {
                signoCelda = 0;
            }

            if (estirable == fin1) {
                fin1Conectada = connected;
                signoFin1 = signoCelda;
            } else if (estirable == fin2) {
                fin2Conectada = connected;
                signoFin2 = signoCelda;
            }
        }
    }

    private Node verificarSiEstaEnCelda(double x, double y, GridPane gridPane) {
        for (Node child : gridPane.getChildren()) {
            Bounds boundsInScene = child.localToScene(child.getBoundsInLocal());
            if (boundsInScene.contains(x, y)) {
                return child;
            }
        }
        return null;
    }

    private void configurarArrastre(Cuadrados estirable, Line pata) {
        estirable.setOnMousePressed(e -> {
            empezarArrastre(e);
            estirable.toFront();
        });
        estirable.setOnMouseDragged(e -> arrastrePata(e, pata, estirable));

        estirable.setOnMouseReleased(event -> {
            updateFinConnection(estirable);
        });
    }

    //Arrastre Nodo
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
                checkFinConnections();
            }
        });
    }

    private void actualizarPosiciones() {
        actualizarEstirable(fin1, pata1);
        actualizarEstirable(fin2, pata2);
    }

    public void checkFinConnections() {
        updateFinConnection(fin1);
        updateFinConnection(fin2);
    }

    public Prototipo_Protoboard getProtoboard() {
        return protoboard;
    }

    public void setProtoboard(Prototipo_Protoboard protoboard) {
        this.protoboard = protoboard;
    }

}