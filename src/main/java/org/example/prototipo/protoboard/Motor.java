package org.example.prototipo.protoboard;

import javafx.geometry.Bounds;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.scene.shape.Polygon;

public class Motor extends Pane {

    private Group nodo = new Group();
    double origenX = Main.origenX - 440;
    double origenY = Main.origenY + 280;

    public Motor() {


        Line fondo= new Line(origenX - 50, origenY - 70, origenX + 50, origenY - 70);
        fondo.setStroke(Color.BLACK);
        fondo.setStrokeWidth(70);

        Line fondodetras= new Line(origenX - 48, origenY - 74, origenX + 50, origenY - 74);
        fondodetras.setStroke(Color.GREY);
        fondodetras.setStrokeWidth(74);

        Line sobre= new Line(origenX - 60, origenY - 90, origenX + 60, origenY - 90);
        sobre.setStroke(Color.GAINSBORO);
        sobre.setStrokeWidth(20);

        Cuadrados boton= new Cuadrados(30,10);
        boton.setX(origenX-15);
        boton.setY(origenY-72);
        boton.setFill(Color.DARKGREY);

        Line conector= new Line(origenX +70, origenY -112, origenX +70, origenY -200);
        conector.setStrokeWidth(10);
        conector.setStroke(Color.DARKGREY);


        nodo.getChildren().addAll(fondodetras,fondo,sobre,boton,conector);
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

}

