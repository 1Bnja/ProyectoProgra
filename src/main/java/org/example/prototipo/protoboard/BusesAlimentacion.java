package org.example.prototipo.protoboard;

import javafx.scene.Group;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.scene.text.Text;

public class BusesAlimentacion extends Group {

    public BusesAlimentacion(double desplazamientoX, double desplazamientoY, char[] simbolos) {

        double tamanioCeldas = 13;
        double espacioCeldas = 12;
        int espacioExtra = 25;

        for (int j = 0; j < simbolos.length; j++){
            Text simbolo = new Text(desplazamientoX - 20, desplazamientoY + j * (tamanioCeldas + espacioCeldas) + 10, String.valueOf(simbolos[j]));
            simbolo.setStroke(Color.BLACK);
            simbolo.setRotate(270);
            this.getChildren().add(simbolo);
        }

        for (int j = 0; j < simbolos.length; j++){
            Text simboloDerecha = new Text(desplazamientoX + 722, desplazamientoY + j * (tamanioCeldas + espacioCeldas) + 10, String.valueOf(simbolos[j]));
            simboloDerecha.setStroke(Color.BLACK);
            simboloDerecha.setRotate(270);
            this.getChildren().add(simboloDerecha);
        }

        for(int i = 0; i < 25 ; i++) {
            for(int j = 0; j < 2; j++) {
                double x2 = i * (tamanioCeldas + espacioCeldas) + (i / 5) * espacioExtra + desplazamientoX;
                double y2 = j * (tamanioCeldas + espacioCeldas) + desplazamientoY;

                for (int k = 0; k < tamanioCeldas; k++) {
                    Line relleno2 = new Line(x2, y2 + k, x2 + tamanioCeldas, y2 + k);
                    relleno2.setFill(Color.GRAY);
                    this.getChildren().add(relleno2);
                }

                Line top2 = new Line(x2, y2, x2 + tamanioCeldas, y2);
                top2.setStroke(Color.BLACK);

                Line right2 = new Line(x2 + tamanioCeldas, y2, x2 + tamanioCeldas, y2 + tamanioCeldas);
                right2.setStroke(Color.BLACK);

                Line bottom2 = new Line(x2, y2 + tamanioCeldas, x2 + tamanioCeldas, y2 + tamanioCeldas);
                bottom2.setStroke(Color.BLACK);

                Line left2 = new Line(x2, y2, x2, y2 + tamanioCeldas);
                left2.setStroke(Color.BLACK);

                this.getChildren().addAll(top2, right2, bottom2, left2);

            }
        }
    }
}
