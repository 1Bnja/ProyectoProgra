package org.example.prototipo.protoboard;

import javafx.scene.Group;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import java.util.ArrayList;
import java.util.List;

public class Celdas extends Group {

    private List<List<Cuadrados>> grid;

    public Celdas(double desplazamientoX, double desplazamientoY, char[] letras, boolean esParteInferior) {
        double tamanioCeldas = 13;
        double espacioCeldas = 11;
        grid = new ArrayList<>();

        GridPane gridPane = new GridPane();
        gridPane.setHgap(espacioCeldas);
        gridPane.setVgap(espacioCeldas);
        
        for (int j = 0; j < letras.length; j++) {
            Text letraIzquierda = new Text(String.valueOf(letras[j]));
            letraIzquierda.setStroke(Color.BLACK);
            letraIzquierda.setRotate(270);
            GridPane.setConstraints(letraIzquierda, 0, j + 1);
            gridPane.getChildren().add(letraIzquierda);

            Text letraDerecha = new Text(String.valueOf(letras[j]));
            letraDerecha.setStroke(Color.BLACK);
            letraDerecha.setRotate(270);
            GridPane.setConstraints(letraDerecha, 31, j + 1);
            gridPane.getChildren().add(letraDerecha);
        }

        // Crear la cuadrícula
        for (int i = 0; i < 30; i++) {
            List<Cuadrados> columna = new ArrayList<>();

            Text numero = new Text(String.valueOf(i + 1));
            numero.setStroke(Color.BLACK);
            numero.setRotate(270);

            if (esParteInferior) {
                GridPane.setConstraints(numero, i + 1, 6);
            } else {
                GridPane.setConstraints(numero, i + 1, 0);
            }

            gridPane.getChildren().add(numero);

            for (int j = 0; j < 5; j++) {
                Cuadrados cuadrado = new Cuadrados((int) tamanioCeldas, (int) espacioCeldas);
                cuadrado.setStroke(Color.BLACK);
                cuadrado.setFill(Color.WHITE);

                final int columnaIndex = i;
                cuadrado.setOnMouseClicked(event -> alternarColumna(columnaIndex));

                GridPane.setConstraints(cuadrado, i + 1, j + 1);
                gridPane.getChildren().add(cuadrado);

                columna.add(cuadrado);
            }

            grid.add(columna);
        }

        gridPane.setLayoutX(desplazamientoX);
        gridPane.setLayoutY(desplazamientoY);
        this.getChildren().add(gridPane);
    }

    private void alternarColumna(int columnaIndex) {
        List<Cuadrados> columna = grid.get(columnaIndex);
        boolean columnaEncendida = columna.get(0).getFill() == Color.RED;

        for (Cuadrados cuadrado : columna) {
            if (columnaEncendida) {
                cuadrado.setFill(Color.WHITE);
            } else {
                cuadrado.setFill(Color.RED);
            }
        }
    }
}
