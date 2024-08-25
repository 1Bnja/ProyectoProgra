package org.example.prototipo.protoboard;

import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.scene.Group;
import javafx.scene.shape.Polygon;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.scene.input.MouseEvent;
import java.util.ArrayList;
import java.util.List;

public class Prototipo_Protoboard extends Pane {

    private Group nodo = new Group();
    private GridPane gridPane;
    private List<Rectangle> celdasConectadas = new ArrayList<>();

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

        // Conexion de celdas
        gridPane = new GridPane();
        int fila = 14;
        int columna = 30;

        int cont = 0;

        for (int i = 0; i < fila; i++) {
            // Añadir espacios en las filas 2, 7 y 12
            if (i == 2 || i == 12) {
                Pane espacio = new Pane();
                espacio.setPrefHeight(30);
                gridPane.add(espacio, 0, cont, columna, 1);
                cont++;
            }

            if (i == 7) {
                Pane espacio = new Pane();
                espacio.setPrefHeight(100);
                gridPane.add(espacio, 0, cont, columna, 1);
                cont++;
            }

            for (int j = 0; j < columna; j++) {
                Rectangle c = new Rectangle(15, 15);
                c.setStroke(javafx.scene.paint.Color.BLACK);
                c.setFill(javafx.scene.paint.Color.WHITE);

                int fil = i;
                int col = j;
                c.setOnMouseClicked(event -> handleRectangleClick(event, fil, col));

                gridPane.add(c, j, cont);

                // Conectar bus de alimentación negativo
                if (i == 0 || i == 12) {
                    celdasConectadas.add(c);
                }

                // Conectar bus de alimentación positivo
                if (i == 1 || i == 13) {
                    celdasConectadas.add(c);
                }

                // Conectar celdas en la (2, 0) a (6, 0)
                if (i >= 2 && i <= 6 && j == 0) {
                    celdasConectadas.add(c);
                }

                // Conectar celdas en la (7, 0) a (11, 0)
                if (i >= 7 && i <= 11 && j == 0) {
                    celdasConectadas.add(c);
                }

                // Conectar celdas en la (2, 1) a (6, 1)
                if (i >= 2 && i <= 6 && j == 1) {
                    celdasConectadas.add(c);
                }

                // Conectar celdas en la (7, 1) a (11, 1)
                if (i >= 7 && i <= 11 && j == 1) {
                    celdasConectadas.add(c);
                }

                // Conectar celdas en la (2, 2) a (6, 2)
                if (i >= 2 && i <= 6 && j == 2) {
                    celdasConectadas.add(c);
                }

                // Conectar celdas en la (7, 2) a (11, 2)
                if (i >= 7 && i <= 11 && j == 2) {
                    celdasConectadas.add(c);
                }

                // Conectar celdas en la (2, 3) a (6, 3)
                if (i >= 2 && i <= 6 && j == 3) {
                    celdasConectadas.add(c);
                }

                // Conectar celdas en la (7, 3) a (11, 3)
                if (i >= 7 && i <= 11 && j == 3) {
                    celdasConectadas.add(c);
                }

                // Conectar celdas en la (2, 4) a (6, 4)
                if (i >= 2 && i <= 6 && j == 4) {
                    celdasConectadas.add(c);
                }

                // Conectar celdas en la (7, 4) a (11, 4)
                if (i >= 7 && i <= 11 && j == 4) {
                    celdasConectadas.add(c);
                }

                // Conectar celdas en la (2, 5) a (6, 5)
                if (i >= 2 && i <= 6 && j == 5) {
                    celdasConectadas.add(c);
                }

                // Conectar celdas en la (7, 5) a (11, 5)
                if (i >= 7 && i <= 11 && j == 5) {
                    celdasConectadas.add(c);
                }

                // Conectar celdas en la (2, 6) a (6, 6)
                if (i >= 2 && i <= 6 && j == 6) {
                    celdasConectadas.add(c);
                }

                // Conectar celdas en la (7, 6) a (11, 6)
                if (i >= 7 && i <= 11 && j == 6) {
                    celdasConectadas.add(c);
                }

                // Conectar celdas en la (2, 7) a (6, 7)
                if (i >= 2 && i <= 6 && j == 7) {
                    celdasConectadas.add(c);
                }

                // Conectar celdas en la (7, 7) a (11, 7)
                if (i >= 7 && i <= 11 && j == 7) {
                    celdasConectadas.add(c);
                }

                // Conectar celdas en la (2, 8) a (6, 8)
                if (i >= 2 && i <= 6 && j == 8) {
                    celdasConectadas.add(c);
                }

                // Conectar celdas en la (7, 8) a (11, 8)
                if (i >= 7 && i <= 11 && j == 8) {
                    celdasConectadas.add(c);
                }

                // Conectar celdas en la (2, 9) a (6, 9)
                if (i >= 2 && i <= 6 && j == 9) {
                    celdasConectadas.add(c);
                }

                // Conectar celdas en la (7, 9) a (11, 9)
                if (i >= 7 && i <= 11 && j == 9) {
                    celdasConectadas.add(c);
                }

                // Conectar celdas en la (2, 10) a (6, 10)
                if (i >= 2 && i <= 6 && j == 10) {
                    celdasConectadas.add(c);
                }

                // Conectar celdas en la (7, 10) a (11, 10)
                if (i >= 7 && i <= 11 && j == 10) {
                    celdasConectadas.add(c);
                }

                // Conectar celdas en la (2, 11) a (6, 11)
                if (i >= 2 && i <= 6 && j == 11) {
                    celdasConectadas.add(c);
                }

                // Conectar celdas en la (7, 11) a (11, 11)
                if (i >= 7 && i <= 11 && j == 11) {
                    celdasConectadas.add(c);
                }

                // Conectar celdas en la (2, 12) a (6, 12)
                if (i >= 2 && i <= 6 && j == 12) {
                    celdasConectadas.add(c);
                }

                // Conectar celdas en la (7, 12) a (11, 12)
                if (i >= 7 && i <= 11 && j == 12) {
                    celdasConectadas.add(c);
                }

                // Conectar celdas en la (2, 13) a (6, 13)
                if (i >= 2 && i <= 6 && j == 13) {
                    celdasConectadas.add(c);
                }

                // Conectar celdas en la (7, 13) a (11, 13)
                if (i >= 7 && i <= 11 && j == 13) {
                    celdasConectadas.add(c);
                }

                // Conectar celdas en la (2, 14) a (6, 14)
                if (i >= 2 && i <= 6 && j == 14) {
                    celdasConectadas.add(c);
                }

                // Conectar celdas en la (7, 14) a (11, 14)
                if (i >= 7 && i <= 11 && j == 14) {
                    celdasConectadas.add(c);
                }

                // Conectar celdas en la (2, 15) a (6, 15)
                if (i >= 2 && i <= 6 && j == 15) {
                    celdasConectadas.add(c);
                }

                // Conectar celdas en la (7, 15) a (11, 15)
                if (i >= 7 && i <= 11 && j == 15) {
                    celdasConectadas.add(c);
                }

                // Conectar celdas en la (2, 16) a (6, 16)
                if (i >= 2 && i <= 6 && j == 16) {
                    celdasConectadas.add(c);
                }

                // Conectar celdas en la (7, 16) a (11, 16)
                if (i >= 7 && i <= 11 && j == 16) {
                    celdasConectadas.add(c);
                }

                // Conectar celdas en la (2, 17) a (6, 17)
                if (i >= 2 && i <= 6 && j == 17) {
                    celdasConectadas.add(c);
                }

                // Conectar celdas en la (7, 17) a (11, 17)
                if (i >= 7 && i <= 11 && j == 17) {
                    celdasConectadas.add(c);
                }

                // Conectar celdas en la (2, 18) a (6, 18)
                if (i >= 2 && i <= 6 && j == 18) {
                    celdasConectadas.add(c);
                }

                // Conectar celdas en la (7, 18) a (11, 18)
                if (i >= 7 && i <= 11 && j == 18) {
                    celdasConectadas.add(c);
                }

                // Conectar celdas en la (2, 19) a (6, 19)
                if (i >= 2 && i <= 6 && j == 19) {
                    celdasConectadas.add(c);
                }

                // Conectar celdas en la (7, 19) a (11, 19)
                if (i >= 7 && i <= 11 && j == 19) {
                    celdasConectadas.add(c);
                }

                // Conectar celdas en la (2, 20) a (6, 20)
                if (i >= 2 && i <= 6 && j == 20) {
                    celdasConectadas.add(c);
                }

                // Conectar celdas en la (7, 20) a (11, 20)
                if (i >= 7 && i <= 11 && j == 20) {
                    celdasConectadas.add(c);
                }

                // Conectar celdas en la (2, 21) a (6, 21)
                if (i >= 2 && i <= 6 && j == 21) {
                    celdasConectadas.add(c);
                }

                // Conectar celdas en la (7, 21) a (11, 21)
                if (i >= 7 && i <= 11 && j == 21) {
                    celdasConectadas.add(c);
                }

                // Conectar celdas en la (2, 22) a (6, 22)
                if (i >= 2 && i <= 6 && j == 22) {
                    celdasConectadas.add(c);
                }

                // Conectar celdas en la (7, 22) a (11, 22)
                if (i >= 7 && i <= 11 && j == 22) {
                    celdasConectadas.add(c);
                }

                // Conectar celdas en la (2, 23) a (6, 23)
                if (i >= 2 && i <= 6 && j == 23) {
                    celdasConectadas.add(c);
                }

                // Conectar celdas en la (7, 23) a (11, 23)
                if (i >= 7 && i <= 11 && j == 23) {
                    celdasConectadas.add(c);
                }

                // Conectar celdas en la (2, 24) a (6, 24)
                if (i >= 2 && i <= 6 && j == 24) {
                    celdasConectadas.add(c);
                }

                // Conectar celdas en la (7, 24) a (11, 24)
                if (i >= 7 && i <= 11 && j == 24) {
                    celdasConectadas.add(c);
                }

                // Conectar celdas en la (2, 25) a (6, 25)
                if (i >= 2 && i <= 6 && j == 25) {
                    celdasConectadas.add(c);
                }

                // Conectar celdas en la (7, 25) a (11, 25)
                if (i >= 7 && i <= 11 && j == 25) {
                    celdasConectadas.add(c);
                }

                // Conectar celdas en la (2, 26) a (6, 26)
                if (i >= 2 && i <= 6 && j == 26) {
                    celdasConectadas.add(c);
                }

                // Conectar celdas en la (7, 26) a (11, 26)
                if (i >= 7 && i <= 11 && j == 26) {
                    celdasConectadas.add(c);
                }

                // Conectar celdas en la (2, 27) a (6, 27)
                if (i >= 2 && i <= 6 && j == 27) {
                    celdasConectadas.add(c);
                }

                // Conectar celdas en la (7, 27) a (11, 27)
                if (i >= 7 && i <= 11 && j == 27) {
                    celdasConectadas.add(c);
                }

                // Conectar celdas en la (2, 28) a (6, 28)
                if (i >= 2 && i <= 6 && j == 28) {
                    celdasConectadas.add(c);
                }

                // Conectar celdas en la (7, 28) a (11, 28)
                if (i >= 7 && i <= 11 && j == 28) {
                    celdasConectadas.add(c);
                }

                // Conectar celdas en la (2, 29) a (6, 29)
                if (i >= 2 && i <= 6 && j == 29) {
                    celdasConectadas.add(c);
                }

                // Conectar celdas en la (7, 29) a (11, 29)
                if (i >= 7 && i <= 11 && j == 29) {
                    celdasConectadas.add(c);
                }
            }
            cont++;
        }
    }

    public GridPane getGridPane() {
        return gridPane;
    }

    private void handleRectangleClick(MouseEvent event, int fila, int columna) {
        System.out.println("(" + fila + ", " + columna + ")");
    }
}
