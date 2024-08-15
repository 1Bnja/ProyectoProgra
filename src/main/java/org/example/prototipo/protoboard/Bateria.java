package org.example.prototipo.protoboard;

import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.CubicCurve;
import javafx.scene.shape.Line;
import javafx.scene.Group;

public class Bateria extends Pane {

    private Group nodo = new Group();

    double origenX = Main.origenX;
    double origenY = Main.origenY;

    public Bateria() {
        Line lineaIzquierda = new Line(origenX - 500, origenY + 80, origenX - 500, origenY + 180);
        Line lineaDerecha = new Line(origenX - 400, origenY + 80, origenX - 400, origenY + 180);
        Line lineaSuperiorI = new Line(origenX - 460, origenY + 80, origenX - 460, origenY + 70);
        Line lineaSuperiorD = new Line(origenX - 440, origenY + 80, origenX - 440, origenY + 70);
        Line lineaSuperiorS = new Line(origenX - 460, origenY + 70, origenX - 440, origenY + 70);

        CubicCurve curvaInferior = new CubicCurve(origenX - 500, origenY + 180, origenX - 475, origenY + 210,
                origenX - 425, origenY + 210, origenX - 400, origenY + 180);
        CubicCurve curvaMedia = new CubicCurve(origenX - 500, origenY + 120, origenX - 475, origenY + 140,
                origenX - 425, origenY + 140, origenX - 400, origenY + 120);
        CubicCurve curvaSuperior = new CubicCurve(origenX - 500, origenY + 80, origenX - 475, origenY + 110,
                origenX - 425, origenY + 110, origenX - 400, origenY + 80);
        CubicCurve curvaSuperior2 = new CubicCurve(origenX - 500, origenY + 80, origenX - 475, origenY + 50,
                origenX - 425, origenY + 50, origenX - 400, origenY + 80);


        curvaInferior.setStroke(Color.BLACK);
        curvaMedia.setStroke(Color.BLACK);
        curvaSuperior.setStroke(Color.BLACK);
        curvaSuperior2.setStroke(Color.BLACK);

        lineaIzquierda.setStroke(Color.BLACK);
        lineaDerecha.setStroke(Color.BLACK);
        lineaSuperiorI.setStroke(Color.BLACK);
        lineaSuperiorD.setStroke(Color.BLACK);
        lineaSuperiorS.setStroke(Color.BLACK);

        curvaInferior.setFill(Color.TRANSPARENT);
        curvaMedia.setFill(Color.TRANSPARENT);
        curvaSuperior.setFill(Color.TRANSPARENT);
        curvaSuperior2.setFill(Color.TRANSPARENT);

        nodo.getChildren().addAll(lineaIzquierda, lineaDerecha, curvaInferior, curvaMedia,
                curvaSuperior, curvaSuperior2, lineaSuperiorI, lineaSuperiorD, lineaSuperiorS);

        this.getChildren().add(nodo);

    }
}
