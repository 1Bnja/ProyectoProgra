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
