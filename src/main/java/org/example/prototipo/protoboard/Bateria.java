package org.example.prototipo.protoboard;

import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.CubicCurve;
import javafx.scene.shape.Line;
import javafx.scene.Group;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;

public class Bateria extends Pane {

    private Group nodo = new Group();

    private double mouseX;
    private double mouseY;

    double origenX = Main.origenX - 400;
    double origenY = Main.origenY + 150;

    public Bateria() {
        // Parte superior de la batería
        Line lineaSuperiorIzquierda = new Line(origenX - 60, origenY - 120, origenX - 60, origenY - 70);
        Line lineaSuperiorDerecha = new Line(origenX + 60, origenY - 120, origenX + 60, origenY - 70);
        Line lineaSuperior = new Line(origenX - 60, origenY - 120, origenX + 60, origenY - 120);
        Line lineaInferior = new Line(origenX - 60, origenY - 70, origenX + 60, origenY - 70);

        // Fondo en la parte superior
        for (int i = 0; i < 50; i++) {
            CubicCurve curvaCobre = new CubicCurve(
                    origenX - 60, origenY - 120 + i,
                    origenX - 40, origenY - 120 + i + 5,
                    origenX + 40, origenY - 120 + i + 5,
                    origenX + 60, origenY - 120 + i
            );
            curvaCobre.setFill(Color.DARKGOLDENROD);
            curvaCobre.setStroke(Color.DARKGOLDENROD);
            nodo.getChildren().add(curvaCobre);
        }

        // Conectores (líneas para los terminales)
        Line conectorPositivo = new Line(origenX - 30, origenY - 130, origenX - 30, origenY - 120);
        conectorPositivo.setStroke(Color.DARKRED);
        conectorPositivo.setStrokeWidth(3);

        Line conectorNegativo = new Line(origenX + 35, origenY - 130, origenX + 35, origenY - 120);
        conectorNegativo.setStroke(Color.BLACK);
        conectorNegativo.setStrokeWidth(3);

        // Parte inferior de la batería (zona negra)
        Line lineaInferiorIzquierda = new Line(origenX - 60, origenY - 70, origenX - 60, origenY + 80);
        Line lineaInferiorDerecha = new Line(origenX + 60, origenY - 70, origenX + 60, origenY + 80);
        Line lineaBase = new Line(origenX - 60, origenY + 80, origenX + 60, origenY + 80);

        // Fondo sólido en la parte inferior (color negro)
        for (int i = 0; i < 150; i++) {
            CubicCurve curvaNegra = new CubicCurve(
                    origenX - 60, origenY - 70 + i,
                    origenX - 40, origenY - 70 + i + 5,
                    origenX + 40, origenY - 70 + i + 5,
                    origenX + 60, origenY - 70 + i
            );
            curvaNegra.setFill(Color.BLACK);
            curvaNegra.setStroke(Color.BLACK);
            nodo.getChildren().add(curvaNegra);
        }

        // Simulación de la división entre la parte cobre y negra
        Line divisionColor = new Line(origenX - 60, origenY - 70, origenX + 60, origenY - 70);
        divisionColor.setStroke(Color.DARKGOLDENROD);
        divisionColor.setStrokeWidth(5);

        // Símbolos + y -
        Text simboloPositivo = new Text(origenX - 50, origenY - 50, "+");
        simboloPositivo.setFill(Color.BLACK);
        simboloPositivo.setStyle("-fx-font-size: 18px; -fx-font-weight: bold;");

        Text simboloNegativo = new Text(origenX + 30, origenY - 50, "-");
        simboloNegativo.setFill(Color.BLACK);
        simboloNegativo.setStyle("-fx-font-size: 18px; -fx-font-weight: bold;");

        // Texto 9V
        Text texto9V = new Text(origenX - 20, origenY + 30, "5V");
        texto9V.setFill(Color.WHITE);
        texto9V.setStyle("-fx-font-size: 24px; -fx-font-weight: bold;");

        // Agregar todos los elementos al nodo
        nodo.getChildren().addAll(
                lineaSuperior, lineaInferior, lineaSuperiorIzquierda, lineaSuperiorDerecha, conectorPositivo, conectorNegativo,
                lineaInferiorIzquierda, lineaInferiorDerecha, lineaBase, divisionColor,
                simboloPositivo, simboloNegativo, texto9V
        );

        nodo.setOnMousePressed(e -> {
            mouseX = e.getSceneX() - nodo.getLayoutX();
            mouseY = e.getSceneY() - nodo.getLayoutY();
        });

        nodo.setOnMouseDragged(e -> {
            nodo.setLayoutX(e.getSceneX() - mouseX);
            nodo.setLayoutY(e.getSceneY() - mouseY);
        });

        this.getChildren().add(nodo);
    }
}