package org.example.prototipo.protoboard;

import javafx.scene.Group;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.CubicCurve;
import javafx.scene.shape.Line;

public class LED extends Pane{
    private Group nodo = new Group();

    double origenX = Main.origenX;
    double origenY = Main.origenY;

    public LED(){

        // LED 1
        Line led1 = new Line(origenX - 550 , origenY - 150, origenX - 515 , origenY - 150);
        CubicCurve curva1 = new CubicCurve(origenX - 550, origenY - 150, origenX- 549.25, origenY - 200, origenX - 515.75, origenY - 200, origenX - 515, origenY -150);
        Line pata1 = new Line(origenX - 545 , origenY - 150, origenX - 545, origenY - 135);
        Line pata2= new Line(origenX - 520 , origenY - 150, origenX - 520, origenY - 135);

        led1.setStroke(Color.BLACK);
        curva1.setStroke(Color.BLACK);
        pata1.setStroke(Color.BLACK);
        pata2.setStroke(Color.BLACK);

        curva1.setFill(Color.BLUE);

        nodo.getChildren().addAll(
                led1, curva1, pata1, pata2
        );

        this.getChildren().add(nodo);
    }
}
