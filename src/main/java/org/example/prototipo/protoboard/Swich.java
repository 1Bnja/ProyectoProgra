package org.example.prototipo.protoboard;

import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.scene.shape.Polygon;
import javafx.scene.Group;

public class Swich extends Pane {

    private Group nodo = new Group();

    double origenX = Main.origenX;
    double origenY = Main.origenY;

    public Swich() {

        // Cuadrado exterior usando líneas
        Line lineaSuperiorCE = new Line(origenX - 500, origenY - 100, origenX - 560, origenY - 100);
        Line lineaInferiorCE = new Line(origenX - 500, origenY - 40, origenX - 560, origenY - 40);
        Line lineaIzquierdaCE = new Line(origenX - 500, origenY - 100, origenX - 500, origenY - 40 );
        Line lineaDerechaCE = new Line(origenX - 560, origenY - 100, origenX - 560, origenY - 40);

        // Cuadrado Interno
        Line lineaSuperiorCI = new Line(origenX - 510, origenY - 90, origenX - 550, origenY - 90);
        Line lineaInferiorCI = new Line(origenX - 510, origenY - 50, origenX - 550, origenY - 50);
        Line lineaIzquierdaCI = new Line(origenX - 510, origenY - 90, origenX - 510, origenY - 50 );
        Line lineaDerechaCI = new Line(origenX - 550, origenY - 90, origenX - 550, origenY - 50);

        // Patas
        Line pata1 = new Line(origenX - 505, origenY - 100, origenX - 505, origenY - 107.5);
        Line pata2= new Line(origenX - 555, origenY - 100, origenX - 555, origenY - 107.5);
        Line pata3 = new Line(origenX - 505, origenY - 40, origenX - 505, origenY - 32.5);
        Line pata4 = new Line(origenX - 555, origenY - 40, origenX - 555, origenY - 32.5);

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

        pata1.setStroke(Color.BLACK);
        pata2.setStroke(Color.BLACK);
        pata3.setStroke(Color.BLACK);
        pata4.setStroke(Color.BLACK);

        nodo.getChildren().addAll(
                fondoCuadradoE, fondoCuadradoI, lineaSuperiorCE, lineaInferiorCE,
                lineaIzquierdaCE, lineaDerechaCE, lineaSuperiorCI, lineaInferiorCI,
                lineaIzquierdaCI, lineaDerechaCI, pata1, pata2, pata3, pata4
        );

        this.getChildren().add(nodo);
    }
}
