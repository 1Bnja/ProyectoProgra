package org.example.prototipo.protoboard;

import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.scene.Group;

public class Prototipo_Protoboard extends Pane {

    private Group nodo = new Group();

    public Prototipo_Protoboard() {

        double anchoEscena = 1280;
        double altoEscena = 920;

        double origenX = anchoEscena / 2;
        double origenY = altoEscena / 2;

        // Cuadrado Principal
        Line lineaArriba = new Line(origenX - 220,origenY - 280, origenX + 580, origenY - 280);
        Line lineaAbajo = new Line(origenX - 220, origenY + 280, origenX + 580, origenY + 280);
        Line lineaIzquierda = new Line(origenX + 580, origenY - 280, origenX + 580, origenY + 280);
        Line lineaDerecha = new Line(origenX - 220, origenY - 280, origenX - 220, origenY + 280);

        // Lineas Internas
        Line lineaInterna1 = new Line(origenX - 220, origenY - 210, origenX + 580, origenY - 210);
        Line lineaInterna2 = new Line(origenX - 220, origenY - 20, origenX + 580, origenY - 20);
        Line lineaInterna3 = new Line(origenX - 220, origenY + 20, origenX + 580, origenY + 20);
        Line lineaInterna4 = new Line(origenX - 220, origenY + 210, origenX + 580, origenY + 210);

        // Celdas 1
        int tamanioCeldas = 13;
        int espacioCeldas = 12;

        double desplazamientoX = origenX - 190;
        double desplazamientoY = origenY - 175;


        for(int i = 0; i < 30 ; i++) {
            for(int j = 0; j < 5; j++) {
                double x = i * (tamanioCeldas + espacioCeldas) + desplazamientoX;
                double y = j * (tamanioCeldas + espacioCeldas) + desplazamientoY;

                Line top = new Line(x, y, x + tamanioCeldas, y);
                top.setStroke(Color.BLACK);

                Line right = new Line(x + tamanioCeldas, y, x + tamanioCeldas, y + tamanioCeldas);
                right.setStroke(Color.BLACK);

                Line bottom = new Line(x, y + tamanioCeldas, x + tamanioCeldas, y + tamanioCeldas);
                bottom.setStroke(Color.BLACK);

                Line left = new Line(x, y, x, y + tamanioCeldas);
                left.setStroke(Color.BLACK);

                nodo.getChildren().addAll(top, right, bottom, left);

            }
        }

        double desplazamientoX1 = origenX - 190;
        double desplazamientoY1 = origenY + 60;

        for(int i = 0; i < 30 ; i++) {
            for(int j = 0; j < 5; j++) {
                double x1 = i * (tamanioCeldas + espacioCeldas) + desplazamientoX1;
                double y1 = j * (tamanioCeldas + espacioCeldas) + desplazamientoY1;

                Line top1 = new Line(x1, y1, x1 + tamanioCeldas, y1);
                top1.setStroke(Color.BLACK);

                Line right1 = new Line(x1 + tamanioCeldas, y1, x1 + tamanioCeldas, y1 + tamanioCeldas);
                right1.setStroke(Color.BLACK);

                Line bottom1 = new Line(x1, y1 + tamanioCeldas, x1 + tamanioCeldas, y1 + tamanioCeldas);
                bottom1.setStroke(Color.BLACK);

                Line left1 = new Line(x1, y1, x1, y1 + tamanioCeldas);
                left1.setStroke(Color.BLACK);

                nodo.getChildren().addAll(top1, right1, bottom1, left1);

            }
        }

        int espacioExtra = 25;
        double desplazamientoX2 = origenX - 175;
        double desplazamientoY2 = origenY - 265;

        for(int i = 0; i < 25 ; i++) {
            for(int j = 0; j < 2; j++) {
                double x2 = i * (tamanioCeldas + espacioCeldas) + (i / 5) * espacioExtra + desplazamientoX2;
                double y2 = j * (tamanioCeldas + espacioCeldas) + desplazamientoY2;

                Line top2 = new Line(x2, y2, x2 + tamanioCeldas, y2);
                top2.setStroke(Color.BLACK);

                Line right2 = new Line(x2 + tamanioCeldas, y2, x2 + tamanioCeldas, y2 + tamanioCeldas);
                right2.setStroke(Color.BLACK);

                Line bottom2 = new Line(x2, y2 + tamanioCeldas, x2 + tamanioCeldas, y2 + tamanioCeldas);
                bottom2.setStroke(Color.BLACK);

                Line left2 = new Line(x2, y2, x2, y2 + tamanioCeldas);
                left2.setStroke(Color.BLACK);

                nodo.getChildren().addAll(top2, right2, bottom2, left2);

            }
        }

        double desplazamientoX3 = origenX - 175;
        double desplazamientoY3 = origenY + 225;

        for(int i = 0; i < 25 ; i++) {
            for(int j = 0; j < 2; j++) {
                double x3 = i * (tamanioCeldas + espacioCeldas) + (i / 5) * espacioExtra + desplazamientoX3;
                double y3 = j * (tamanioCeldas + espacioCeldas) + desplazamientoY3;

                Line top3 = new Line(x3, y3, x3 + tamanioCeldas, y3);
                top3.setStroke(Color.BLACK);

                Line right3 = new Line(x3 + tamanioCeldas, y3, x3 + tamanioCeldas, y3 + tamanioCeldas);
                right3.setStroke(Color.BLACK);

                Line bottom3 = new Line(x3, y3 + tamanioCeldas, x3 + tamanioCeldas, y3 + tamanioCeldas);
                bottom3.setStroke(Color.BLACK);

                Line left3 = new Line(x3, y3, x3, y3 + tamanioCeldas);
                left3.setStroke(Color.BLACK);

                nodo.getChildren().addAll(top3, right3, bottom3, left3);

            }
        }

        // Lineas Exteriores
        Line lineaExterior = new Line(origenX - 220, origenY - 280, origenX - 210, origenY - 290);
        Line lineaExterior1 = new Line(origenX - 210, origenY - 290, origenX + 590, origenY - 290);
        Line lineaExterior2 = new Line(origenX + 590, origenY - 290, origenX + 590, origenY + 270);
        Line lineaExterior3 = new Line(origenX + 580, origenY - 280, origenX + 590, origenY - 290);
        Line lineaExterior4 = new Line(origenX + 580, origenY - 210, origenX + 590, origenY - 220);
        Line lineaExterior5 = new Line(origenX + 580, origenY - 20, origenX + 590, origenY - 30);
        Line lineaExterior6 = new Line(origenX + 580, origenY + 20, origenX + 590, origenY + 10);
        Line lineaExterior7 = new Line(origenX + 580, origenY + 210, origenX + 590, origenY + 200);
        Line lineaExterior8 = new Line(origenX + 580, origenY + 280, origenX + 590, origenY + 270);




        // Cuadrado Principal
        lineaArriba.setStroke(Color.BLACK);
        lineaAbajo.setStroke(Color.BLACK);
        lineaIzquierda.setStroke(Color.BLACK);
        lineaDerecha.setStroke(Color.BLACK);

        nodo.getChildren().addAll(lineaArriba,lineaAbajo,lineaDerecha,lineaIzquierda);

        //Lineas Internas
        lineaInterna1.setStroke(Color.BLACK);
        lineaInterna2.setStroke(Color.BLACK);
        lineaInterna3.setStroke(Color.BLACK);
        lineaInterna4.setStroke(Color.BLACK);
        nodo.getChildren().addAll(lineaInterna1,lineaInterna2,lineaInterna3, lineaInterna4);

        //Lineas Externas
        lineaExterior.setStroke(Color.BLACK);
        lineaExterior1.setStroke(Color.BLACK);
        lineaExterior2.setStroke(Color.BLACK);
        lineaExterior3.setStroke(Color.BLACK);
        lineaExterior4.setStroke(Color.BLACK);
        lineaExterior5.setStroke(Color.BLACK);
        lineaExterior6.setStroke(Color.BLACK);
        lineaExterior7.setStroke(Color.BLACK);
        lineaExterior8.setStroke(Color.BLACK);

        nodo.getChildren().addAll(lineaExterior,lineaExterior1, lineaExterior2, lineaExterior3, lineaExterior4, lineaExterior5, lineaExterior6, lineaExterior7, lineaExterior8);

        getChildren().add(nodo);
    }


}
