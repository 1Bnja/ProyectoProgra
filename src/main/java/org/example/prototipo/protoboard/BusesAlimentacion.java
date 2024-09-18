package org.example.prototipo.protoboard;

import javafx.scene.Group;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import jdk.swing.interop.SwingInterOpUtils;

import java.util.ArrayList;
import java.util.List;

public class BusesAlimentacion extends Group {

    private List<List<Cuadrados>> buses;
    private int espacioExtra = 2;

    // Constructor
    public BusesAlimentacion(double desplazamientoX, double desplazamientoY, char[] simbolos) {
        double tamanioCeldas = 13;
        double espacioCeldas = 11;
        buses = new ArrayList<>();

        GridPane gridPane = new GridPane();
        gridPane.setHgap(espacioCeldas);
        gridPane.setVgap(espacioCeldas);


        for (int j = 0; j < simbolos.length; j++) {
            Text simboloIzquierda = new Text(String.valueOf(simbolos[j]));
            simboloIzquierda.setStroke(Color.BLACK);
            simboloIzquierda.setRotate(270);
            GridPane.setConstraints(simboloIzquierda, 0, j);
            gridPane.getChildren().add(simboloIzquierda);

            Text simboloDerecha = new Text(String.valueOf(simbolos[j]));
            simboloDerecha.setStroke(Color.BLACK);
            simboloDerecha.setRotate(270);
            GridPane.setConstraints(simboloDerecha, 34, j);
            gridPane.getChildren().add(simboloDerecha);
        }


        for (int j = 0; j < 2; j++) {
            List<Cuadrados> filaBus = new ArrayList<>();
            int columnaIndex = 1;

            for (int i = 0; i < 25; i++) {
                if (i > 0 && i % 5 == 0) {
                    columnaIndex += espacioExtra;
                }

                Cuadrados cuadrado = new Cuadrados((int) tamanioCeldas, (int) espacioCeldas);
                cuadrado.setStroke(Color.BLACK);
                cuadrado.setFill(Color.WHITE);

                int finalJ = j;
                //cuadrado.setOnMouseClicked(event -> toggleFilaBus(finalJ));  // Manejador de eventos

                GridPane.setConstraints(cuadrado, columnaIndex, j);
                gridPane.getChildren().add(cuadrado);

                filaBus.add(cuadrado);
                columnaIndex++;
            }

            buses.add(filaBus);
        }

        gridPane.setLayoutX(desplazamientoX);
        gridPane.setLayoutY(desplazamientoY);
        this.getChildren().add(gridPane);
    }


    public void toggleFilaBus(int filaIndex, int signo) {
        List<Cuadrados> filaBus = buses.get(filaIndex);
        Color colorEncendido = Color.WHITE;

        if(signo ==-1){
            colorEncendido = Color.BLUE;
        } else if(signo == 1){
            colorEncendido = Color.RED;
        }


        for (Cuadrados cuadrado : filaBus) {

                cuadrado.setFill(colorEncendido);  // Encender

        }
    }

    public int getSigno(int fila, int col){
        System.out.println(col+ "| " +fila);
        List<Cuadrados> columna = buses.get(fila);

        return columna.get(col).getSigno();
    }

    public void setSigno(int fila, int col, int signo){
        for (int i =0; i < buses.get(fila).size(); i++) {

            this.buses.get(fila).get(i).setSigno(signo);  // Encender

        }

    }
}
