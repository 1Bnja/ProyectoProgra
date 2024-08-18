package org.example.prototipo.protoboard;

import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.scene.Group;
import javafx.scene.shape.Polygon;
import javafx.scene.text.Text;

public class Prototipo_Protoboard extends Pane {

    private Group nodo = new Group();

    double origenX = Main.origenX;
    double origenY = Main.origenY;

    public Prototipo_Protoboard() {

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

        Line lineaGuia1 = new Line(origenX - 175,origenY - 273, origenX + 539, origenY - 273);
        Line lineaGuia2 = new Line(origenX - 175, origenY - 217, origenX + 539, origenY - 217);
        Line lineaGuia3 = new Line(origenX - 175, origenY + 217, origenX + 539, origenY + 217);
        Line lineaGuia4 = new Line(origenX - 175, origenY + 273, origenX + 539, origenY + 273);

        //Pintar

        Polygon fondoTotal = new Polygon();
        fondoTotal.getPoints().addAll(
                origenX - 220, origenY - 280,
                origenX + 580, origenY - 280,
                origenX + 580, origenY + 280,
                origenX - 220, origenY + 280
        );
        fondoTotal.setFill(Color.LIGHTGRAY);
        nodo.getChildren().add(fondoTotal);

        Polygon fondoCarrilCentral = new Polygon();
        fondoCarrilCentral.getPoints().addAll(
                origenX - 220, origenY - 20,
                origenX + 580, origenY - 20,
                origenX + 580, origenY + 20,
                origenX - 220, origenY + 20
        );
        fondoCarrilCentral.setFill(Color.GRAY);
        nodo.getChildren().add(fondoCarrilCentral);

        // Celdas 1
        float tamanioCeldas = 13;
        float espacioCeldas = 12;

        double desplazamientoX = origenX - 190;
        double desplazamientoY = origenY - 175;

        char[] letras1 = {'j', 'i', 'h', 'g', 'f'};

        // Implementacion letras Parte Superior
        for (int j = 0; j < letras1.length; j++){
            Text letra1Izquierda = new Text(desplazamientoX - 20, desplazamientoY + j * (tamanioCeldas + espacioCeldas) + 10, String.valueOf(letras1[j]));
            letra1Izquierda.setStroke(Color.BLACK);
            letra1Izquierda.setRotate(270);
            nodo.getChildren().add(letra1Izquierda);
        }

        for (int j = 0; j < letras1.length; j++){
            Text letra1Derecha = new Text(desplazamientoX + 747, desplazamientoY + j * (tamanioCeldas + espacioCeldas) + 10, String.valueOf(letras1[j]));
            letra1Derecha.setStroke(Color.BLACK);
            letra1Derecha.setRotate(270);
            nodo.getChildren().add(letra1Derecha);
        }

        for(int i = 0; i < 30 ; i++) {
            Text numero1 = new Text(desplazamientoX + i * (tamanioCeldas + espacioCeldas) + 2, desplazamientoY - 10, String.valueOf(i + 1));
            numero1.setStroke(Color.BLACK);
            numero1.setRotate(270);
            nodo.getChildren().add(numero1);

            for(int j = 0; j < 5; j++) {
                double x = i * (tamanioCeldas + espacioCeldas) + desplazamientoX;
                double y = j * (tamanioCeldas + espacioCeldas) + desplazamientoY;

                for (int k = 0; k < tamanioCeldas; k++) {
                    Line relleno = new Line(x, y + k, x + tamanioCeldas, y + k);
                    relleno.setFill(Color.GRAY);
                    nodo.getChildren().add(relleno);
                }

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

        char[] letras2 = {'e', 'd', 'c', 'b', 'a'};

        // Implementacion letras Parte Inferior
        for (int j = 0; j < letras2.length; j++){
            Text letra2Izquierda = new Text(desplazamientoX1 - 20, desplazamientoY1 + j * (tamanioCeldas + espacioCeldas) + 10, String.valueOf(letras2[j]));
            letra2Izquierda.setStroke(Color.BLACK);
            letra2Izquierda.setRotate(270);
            nodo.getChildren().add(letra2Izquierda);
        }

        for (int j = 0; j < letras2.length; j++){
            Text letra2Derecha = new Text(desplazamientoX1 + 747, desplazamientoY1 + j * (tamanioCeldas + espacioCeldas) + 10, String.valueOf(letras2[j]));
            letra2Derecha.setStroke(Color.BLACK);
            letra2Derecha.setRotate(270);
            nodo.getChildren().add(letra2Derecha);
        }

        for(int i = 0; i < 30 ; i++) {
            Text numero2 = new Text(desplazamientoX1 + i * (tamanioCeldas + espacioCeldas) + 2, desplazamientoY1 + 135, String.valueOf(i + 1));
            numero2.setStroke(Color.BLACK);
            numero2.setRotate(270);
            nodo.getChildren().add(numero2);
            for(int j = 0; j < 5; j++) {
                double x1 = i * (tamanioCeldas + espacioCeldas) + desplazamientoX1;
                double y1 = j * (tamanioCeldas + espacioCeldas) + desplazamientoY1;

                for (int k = 0; k < tamanioCeldas; k++) {
                    Line relleno1 = new Line(x1, y1 + k, x1 + tamanioCeldas, y1 + k);
                    relleno1.setFill(Color.GRAY);
                    nodo.getChildren().add(relleno1);
                }

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

        char[] simbolos = {'-', '+'};

        for (int j = 0; j < simbolos.length; j++){
            Text simbolo = new Text(desplazamientoX2 - 20, desplazamientoY2 + j * (tamanioCeldas + espacioCeldas) + 10, String.valueOf(simbolos[j]));
            simbolo.setStroke(Color.BLACK);
            simbolo.setRotate(270);
            nodo.getChildren().add(simbolo);
        }

        for (int j = 0; j < simbolos.length; j++){
            Text simboloDerecha = new Text(desplazamientoX2 + 722, desplazamientoY2 + j * (tamanioCeldas + espacioCeldas) + 10, String.valueOf(simbolos[j]));
            simboloDerecha.setStroke(Color.BLACK);
            simboloDerecha.setRotate(270);
            nodo.getChildren().add(simboloDerecha);
        }

        for(int i = 0; i < 25 ; i++) {
            for(int j = 0; j < 2; j++) {
                double x2 = i * (tamanioCeldas + espacioCeldas) + (i / 5) * espacioExtra + desplazamientoX2;
                double y2 = j * (tamanioCeldas + espacioCeldas) + desplazamientoY2;

                for (int k = 0; k < tamanioCeldas; k++) {
                    Line relleno2 = new Line(x2, y2 + k, x2 + tamanioCeldas, y2 + k);
                    relleno2.setFill(Color.GRAY);
                    nodo.getChildren().add(relleno2);
                }

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

        for (int j = 0; j < simbolos.length; j++){
            Text simbolo1 = new Text(desplazamientoX3 - 20, desplazamientoY3 + j * (tamanioCeldas + espacioCeldas) + 10, String.valueOf(simbolos[j]));
            simbolo1.setStroke(Color.BLACK);
            simbolo1.setRotate(270);

            nodo.getChildren().add(simbolo1);
        }

        for (int j = 0; j < simbolos.length; j++){
            Text simbolo1Derecha = new Text(desplazamientoX3 + 722, desplazamientoY3 + j * (tamanioCeldas + espacioCeldas) + 10, String.valueOf(simbolos[j]));
            simbolo1Derecha.setStroke(Color.BLACK);
            simbolo1Derecha.setRotate(270);

            nodo.getChildren().add(simbolo1Derecha);
        }

        for(int i = 0; i < 25 ; i++) {
            for(int j = 0; j < 2; j++) {
                double x3 = i * (tamanioCeldas + espacioCeldas) + (i / 5) * espacioExtra + desplazamientoX3;
                double y3 = j * (tamanioCeldas + espacioCeldas) + desplazamientoY3;

                for (int k = 0; k < tamanioCeldas; k++) {
                    Line relleno3 = new Line(x3, y3 + k, x3 + tamanioCeldas, y3 + k);
                    relleno3.setFill(Color.GRAY);
                    nodo.getChildren().add(relleno3);
                }

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

        Polygon fondo1Exterior = new Polygon();
        fondo1Exterior.getPoints().addAll(
                origenX - 210, origenY - 290,
                origenX + 590, origenY - 290,
                origenX + 580, origenY - 280,
                origenX - 220, origenY - 280
        );
        fondo1Exterior.setFill(Color.GRAY);
        nodo.getChildren().add(fondo1Exterior);

        Polygon fondo2Exterior = new Polygon();
        fondo2Exterior.getPoints().addAll(
                origenX + 580, origenY - 280,
                origenX + 590, origenY - 290,
                origenX + 590, origenY + 270,
                origenX + 580, origenY + 280
        );
        fondo2Exterior.setFill(Color.GRAY);
        nodo.getChildren().add(fondo2Exterior);

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

        // Lineas Guia
        lineaGuia1.setStroke(Color.BLUE);
        lineaGuia2.setStroke(Color.RED);
        lineaGuia3.setStroke(Color.BLUE);
        lineaGuia4.setStroke(Color.RED);

        nodo.getChildren().addAll(lineaGuia1, lineaGuia2, lineaGuia3, lineaGuia4);

        nodo.getChildren().addAll(
                lineaExterior,lineaExterior1, lineaExterior2, lineaExterior3,
                lineaExterior4, lineaExterior5, lineaExterior6, lineaExterior7,
                lineaExterior8
        );

        getChildren().add(nodo);
    }


}
