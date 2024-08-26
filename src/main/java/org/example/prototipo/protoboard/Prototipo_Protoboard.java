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

        Polygon fondoTotal = crearFondo(origenX - 220, origenY - 280, origenX + 580, origenY + 280, Color.LIGHTGRAY);
        Polygon fondoCarrilCentral = crearFondo(origenX - 220, origenY - 20, origenX + 580, origenY + 20, Color.GRAY);

        nodo.getChildren().addAll(fondoTotal, fondoCarrilCentral);

        Polygon fondo1Exterior = crearFondo2(new double[]{origenX - 210, origenY - 290,origenX + 590, origenY - 290,origenX + 580, origenY - 280,origenX - 220, origenY - 280}, Color.GRAY);
        Polygon fondo2Exterior = crearFondo2(new double[]{origenX + 580, origenY - 280,origenX + 590, origenY - 290,origenX + 590, origenY + 270, origenX + 580, origenY + 280}, Color.GRAY);

        nodo.getChildren().addAll(fondo1Exterior, fondo2Exterior);

        // Cuadrado Principal
        Line lineaArriba = crearLinea(origenX - 220,origenY - 280, origenX + 580, origenY - 280, Color.BLACK);
        Line lineaAbajo = crearLinea(origenX - 220, origenY + 280, origenX + 580, origenY + 280, Color.BLACK);
        Line lineaIzquierda = crearLinea(origenX + 580, origenY - 280, origenX + 580, origenY + 280, Color.BLACK);
        Line lineaDerecha = crearLinea(origenX - 220, origenY - 280, origenX - 220, origenY + 280, Color.BLACK);

        // Lineas Internas
        Line lineaInterna1 = crearLinea(origenX - 220, origenY - 210, origenX + 580, origenY - 210, Color.BLACK);
        Line lineaInterna2 = crearLinea(origenX - 220, origenY - 20, origenX + 580, origenY - 20, Color.BLACK);
        Line lineaInterna3 = crearLinea(origenX - 220, origenY + 20, origenX + 580, origenY + 20, Color.BLACK);
        Line lineaInterna4 = crearLinea(origenX - 220, origenY + 210, origenX + 580, origenY + 210, Color.BLACK);

        nodo.getChildren().addAll(lineaArriba, lineaAbajo, lineaIzquierda, lineaDerecha, lineaInterna1, lineaInterna2, lineaInterna3, lineaInterna4);

        Line lineaGuia1 = crearLinea(origenX - 175,origenY - 273, origenX + 539, origenY - 273, Color.BLUE);
        Line lineaGuia2 = crearLinea(origenX - 175, origenY - 217, origenX + 539, origenY - 217, Color.RED);
        Line lineaGuia3 = crearLinea(origenX - 175, origenY + 217, origenX + 539, origenY + 217, Color.BLUE);
        Line lineaGuia4 = crearLinea(origenX - 175, origenY + 273, origenX + 539, origenY + 273, Color.RED);

        nodo.getChildren().addAll(lineaGuia1, lineaGuia2, lineaGuia3, lineaGuia4);

        crearCeldas1(origenX - 190, origenY - 175, new char[]{'j', 'i', 'h', 'g', 'f'});
        crearCeldas1(origenX - 190, origenY + 60, new char[]{'e', 'd', 'c', 'b', 'a'});
        crearCeldas2(origenX - 175, origenY - 265, new char[]{'-', '+'});
        crearCeldas2(origenX - 175, origenY + 225, new char[]{'-', '+'});

        // Lineas Exteriores
        Line lineaExterior = crearLinea(origenX - 220, origenY - 280, origenX - 210, origenY - 290, Color.BLACK);
        Line lineaExterior1 = crearLinea(origenX - 210, origenY - 290, origenX + 590, origenY - 290, Color.BLACK);
        Line lineaExterior2 = crearLinea(origenX + 590, origenY - 290, origenX + 590, origenY + 270, Color.BLACK);
        Line lineaExterior3 = crearLinea(origenX + 580, origenY - 280, origenX + 590, origenY - 290, Color.BLACK);
        Line lineaExterior4 = crearLinea(origenX + 580, origenY - 210, origenX + 590, origenY - 220, Color.BLACK);
        Line lineaExterior5 = crearLinea(origenX + 580, origenY - 20, origenX + 590, origenY - 30, Color.BLACK);
        Line lineaExterior6 = crearLinea(origenX + 580, origenY + 20, origenX + 590, origenY + 10, Color.BLACK);
        Line lineaExterior7 = crearLinea(origenX + 580, origenY + 210, origenX + 590, origenY + 200, Color.BLACK);
        Line lineaExterior8 = crearLinea(origenX + 580, origenY + 280, origenX + 590, origenY + 270, Color.BLACK);

        nodo.getChildren().addAll(
                lineaExterior,lineaExterior1, lineaExterior2, lineaExterior3, lineaExterior4, lineaExterior5,
                lineaExterior6, lineaExterior7, lineaExterior8
        );

        this.getChildren().add(nodo);

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

    private void crearCeldas1(double desplazamientoX, double desplazamientoY, char[] letras1){

        double tamanioCeldas = 13;
        double espacioCeldas = 12;

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
    }

    private void crearCeldas2(double desplazamientoX, double desplazamientoY, char[] simbolos){
        double tamanioCeldas = 13;
        double espacioCeldas = 12;
        int espacioExtra = 25;

        for (int j = 0; j < simbolos.length; j++){
            Text simbolo = new Text(desplazamientoX - 20, desplazamientoY + j * (tamanioCeldas + espacioCeldas) + 10, String.valueOf(simbolos[j]));
            simbolo.setStroke(Color.BLACK);
            simbolo.setRotate(270);
            nodo.getChildren().add(simbolo);
        }

        for (int j = 0; j < simbolos.length; j++){
            Text simboloDerecha = new Text(desplazamientoX + 722, desplazamientoY + j * (tamanioCeldas + espacioCeldas) + 10, String.valueOf(simbolos[j]));
            simboloDerecha.setStroke(Color.BLACK);
            simboloDerecha.setRotate(270);
            nodo.getChildren().add(simboloDerecha);
        }

        for(int i = 0; i < 25 ; i++) {
            for(int j = 0; j < 2; j++) {
                double x2 = i * (tamanioCeldas + espacioCeldas) + (i / 5) * espacioExtra + desplazamientoX;
                double y2 = j * (tamanioCeldas + espacioCeldas) + desplazamientoY;

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
    }

    private Line crearLinea(double startX, double startY, double endX, double endY, Color color) {
        Line linea = new Line(startX, startY, endX, endY);
        linea.setStroke(color);
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

    private Polygon crearFondo2(double[] puntos, Color color) {
        Polygon fondo = new Polygon();

        Double[] puntosConvertidos = new Double[puntos.length];
        for (int i = 0; i < puntos.length; i++) {
            puntosConvertidos[i] = puntos[i];
        }
        fondo.getPoints().addAll(puntosConvertidos);
        fondo.setFill(color);
        return fondo;
    }
}
