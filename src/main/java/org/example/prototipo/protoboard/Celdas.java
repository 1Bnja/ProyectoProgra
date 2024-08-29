package org.example.prototipo.protoboard;

import javafx.scene.Group;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.scene.text.Text;

public class Celdas extends Group {

    public Celdas(double desplazamientoX, double desplazamientoY, char[] letras1) {

        double tamanioCeldas = 13;
        double espacioCeldas = 12;

        for (int j = 0; j < letras1.length; j++){
            Text letra1Izquierda = new Text(desplazamientoX - 20, desplazamientoY + j * (tamanioCeldas + espacioCeldas) + 10, String.valueOf(letras1[j]));
            letra1Izquierda.setStroke(Color.BLACK);
            letra1Izquierda.setRotate(270);
            this.getChildren().add(letra1Izquierda);
        }

        for (int j = 0; j < letras1.length; j++){
            Text letra1Derecha = new Text(desplazamientoX + 747, desplazamientoY + j * (tamanioCeldas + espacioCeldas) + 10, String.valueOf(letras1[j]));
            letra1Derecha.setStroke(Color.BLACK);
            letra1Derecha.setRotate(270);
            this.getChildren().add(letra1Derecha);
        }

        for(int i = 0; i < 30 ; i++) {

            Text numero1 = new Text(desplazamientoX + i * (tamanioCeldas + espacioCeldas) + 2, desplazamientoY - 10, String.valueOf(i + 1));
            numero1.setStroke(Color.BLACK);
            numero1.setRotate(270);
            this.getChildren().add(numero1);

            for(int j = 0; j < 5; j++) {
                double x = i * (tamanioCeldas + espacioCeldas) + desplazamientoX;
                double y = j * (tamanioCeldas + espacioCeldas) + desplazamientoY;

                for (int k = 0; k < tamanioCeldas; k++) {
                    Line relleno = new Line(x, y + k, x + tamanioCeldas, y + k);
                    relleno.setFill(Color.GRAY);
                    this.getChildren().add(relleno);
                }

                Line top = new Line(x, y, x + tamanioCeldas, y);
                top.setStroke(Color.BLACK);

                Line right = new Line(x + tamanioCeldas, y, x + tamanioCeldas, y + tamanioCeldas);
                right.setStroke(Color.BLACK);

                Line bottom = new Line(x, y + tamanioCeldas, x + tamanioCeldas, y + tamanioCeldas);
                bottom.setStroke(Color.BLACK);

                Line left = new Line(x, y, x, y + tamanioCeldas);
                left.setStroke(Color.BLACK);

                this.getChildren().addAll(top, right, bottom, left);
            }
        }
    }
}



