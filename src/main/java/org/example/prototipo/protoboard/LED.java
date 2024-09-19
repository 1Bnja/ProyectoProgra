package org.example.prototipo.protoboard;

import javafx.geometry.Bounds;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.CubicCurve;
import javafx.scene.shape.Line;

public class LED extends Pane {
    private Group nodo = new Group();
    private Line pata1, pata2;
    private Cuadrados fin1, fin2;

    // Variables para la posición del mouse
    private double mouseX, mouseY;
    private boolean line_en_arrastre = false;

    double origenX = Main.origenX;
    double origenY = Main.origenY;
    Prototipo_Protoboard protoboard;


    public LED() {
        // LED
        Line led1 = crearLinea(origenX - 550, origenY - 150, origenX - 515, origenY - 150);
        CubicCurve curva = crearCurva(origenX - 550, origenY - 150, origenX - 549.25, origenY - 200, origenX - 515.75, origenY - 200, origenX - 515, origenY - 150);
        pata1 = crearLinea(origenX - 545, origenY - 150, origenX - 545, origenY - 135);
        pata2 = crearLinea(origenX - 520, origenY - 150, origenX - 520, origenY - 135);

        fin1 = crearEstirable(pata1);
        fin1.setSigno(-1);
        fin2 = crearEstirable(pata2);
        fin2.setSigno(1);

        configurarArrastre(fin1, pata1);
        configurarArrastre(fin2, pata2);

        configurarArrastreNodo();

        nodo.getChildren().addAll(led1, curva, pata1, pata2, fin1, fin2);
        this.getChildren().add(nodo);

        this.setPickOnBounds(false);
    }

    private Line crearLinea(double startX, double startY, double endX, double endY) {
        Line linea = new Line(startX, startY, endX, endY);
        linea.setStroke(Color.BLACK);
        return linea;
    }

    private CubicCurve crearCurva(double startX, double startY, double controlX1, double controlY1, double controlX2, double controlY2, double endX, double endY) {
        CubicCurve curva = new CubicCurve(startX, startY, controlX1, controlY1, controlX2, controlY2, endX, endY);
        curva.setStroke(Color.BLACK);
        curva.setFill(Color.LIGHTBLUE);
        return curva;
    }

    private void empezarArrastre(MouseEvent event, Line pata) {
        // Indicar que estamos arrastrando una línea
        line_en_arrastre = true;
        mouseX = event.getSceneX();
        mouseY = event.getSceneY();
    }

    private void configurarArrastre(Cuadrados estirable, Line pata) {
        estirable.setOnMousePressed(e -> {
            empezarArrastre(e, pata);
            estirable.toFront();
        });
        estirable.setOnMouseDragged(e -> arrastrePata(e, pata, estirable));

        estirable.setOnMouseReleased(event ->{

            double mouseX = event.getSceneX();
            double mouseY = event.getSceneY();
            if(protoboard != null){
                Node arriba = verificarSiEstaEnCelda(mouseX,mouseY,(GridPane) protoboard.getCelda1().getChildren().getFirst());
                Node abajo = verificarSiEstaEnCelda(mouseX,mouseY,(GridPane) protoboard.getCelda2().getChildren().getFirst());
                Node bus_arriba = verificarSiEstaEnCelda(mouseX,mouseY,(GridPane) protoboard.getBus1().getChildren().getFirst());
                Node bus_abajo = verificarSiEstaEnCelda(mouseX,mouseY,(GridPane) protoboard.getBus2().getChildren().getFirst());
                int col = 0;
                int row = 0;
                if(arriba != null) {
                    col =  ((GridPane) protoboard.getCelda1().getChildren().getFirst()).getColumnIndex(arriba)-1;
                    row =  ((GridPane) protoboard.getCelda1().getChildren().getFirst()).getRowIndex(arriba);

                    //protoboard.getCelda1().alternarColumna(col,estirable.getSigno()); //patas del led pintan columna
                }
                if(abajo != null) {
                    col = ((GridPane) protoboard.getCelda2().getChildren().getFirst()).getColumnIndex(abajo)-1;
                    row =  ((GridPane) protoboard.getCelda2().getChildren().getFirst()).getRowIndex(abajo);
                    //protoboard.getCelda2().alternarColumna(col, estirable.getSigno());


                }
                if(bus_abajo != null) {
                    row = ((GridPane) protoboard.getBus2().getChildren().getFirst()).getRowIndex(bus_abajo);
                    col = ((GridPane) protoboard.getBus2().getChildren().getFirst()).getColumnIndex(bus_abajo)-1;
                    //estirable.setSigno(protoboard.getBus2().getSigno(row,col));
                    //protoboard.getBus2().setSigno(row,col,estirable.getSigno());
                    //protoboard.getBus2().toggleFilaBus(row,estirable.getSigno() );

                }
                if(bus_arriba != null) {
                    row = ((GridPane) protoboard.getBus1().getChildren().getFirst()).getRowIndex(bus_arriba);
                    col = ((GridPane) protoboard.getBus1().getChildren().getFirst()).getColumnIndex(bus_arriba)-1;
                    //estirable.setSigno(protoboard.getBus1().getSigno(row,col));
                    //protoboard.getBus1().setSigno(row,col,estirable.getSigno());
                    //protoboard.getBus1().toggleFilaBus(row,estirable.getSigno() );
                }
            }

        });
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

    private Cuadrados crearEstirable(Line pata) {
        Cuadrados esquina = new Cuadrados(11,2);
        esquina.setFill(Color.RED);
        esquina.setX(pata.getEndX()-5);
        esquina.setY(pata.getEndY()-5);
        return esquina;
    }

    private void actualizarEstirable(Cuadrados esquina, Line pata){
        esquina.setX(pata.getEndX()-5);
        esquina.setY(pata.getEndY());
        esquina.setX(pata.getEndX()-5);
        esquina.setY(pata.getEndY());
    }

    private void actualizarPosiciones(){
        actualizarEstirable(fin1, pata1);
        actualizarEstirable(fin2, pata2);
    }

    public Cuadrados getFin1() {
        return fin1;
    }

    public Cuadrados getFin2() {
        return fin2;
    }

    public Prototipo_Protoboard getProtoboard() {
        return protoboard;
    }

    public void setProtoboard(Prototipo_Protoboard protoboard) {
        this.protoboard = protoboard;
    }
}


