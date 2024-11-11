package org.example.prototipo.protoboard;

import javafx.scene.Node;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;

public class ChipOr extends Chip{

    // Constructor de la clase Chip
    public ChipOr(){
        super("Chip OR");
    }

    @Override
    protected void calcularSalida(Cuadrados entrada1, Cuadrados entrada2, Cuadrados salida) {
        int signoSalida;
        Color colorSalida;

        // Lógica de la compuerta AND
        if (entrada1.getSigno() == 1 || entrada2.getSigno() == 1) {
            signoSalida = 1;
            colorSalida = Color.RED;
        } else {
            signoSalida = 0;
            colorSalida = Color.WHITE;
        }

        // Actualizar la pata de salida
        salida.setSigno(signoSalida);
        salida.setFill(colorSalida);

        // Actualizar el protoboard si la salida está conectada
        if (salida.getCeldaConectada() != null) {
            actualizarCeldaSalida(salida);
        }
    }


    private void actualizarCeldaSalida(Cuadrados salida) {
        Node celdaConectada = salida.getCeldaConectada();
        if (celdaConectada != null) {
            // Determinar a qué gridPane pertenece la celda
            GridPane gridPane = (GridPane) celdaConectada.getParent();

            // Obtener índices de fila y columna
            Integer colIndex = GridPane.getColumnIndex(celdaConectada);
            Integer rowIndex = GridPane.getRowIndex(celdaConectada);

            if (colIndex != null && rowIndex != null) {
                int columna = colIndex - 1;
                int fila = rowIndex;

                // Actualizar la columna correspondiente en el protoboard
                if (gridPane == protoboard.getCelda1().getGridPane()) {
                    protoboard.getCelda1().alternarColumna(columna, salida.getSigno(), salida.getVoltaje());
                } else if (gridPane == protoboard.getCelda2().getGridPane()) {
                    protoboard.getCelda2().alternarColumna(columna, salida.getSigno(), salida.getVoltaje());
                }
            }
        }
    }


    protected void calcularSalidas() {
        calcularSalida(fin1, fin2, fin3);
        calcularSalida(fin4, fin5, fin6);
        calcularSalida(fin9, fin10, fin8);
        calcularSalida(fin12, fin13, fin11);
    }

    // Getters y setters para el protoboard
    public Prototipo_Protoboard getProtoboard() {
        return protoboard;
    }

    public void setProtoboard(Prototipo_Protoboard protoboard) {
        this.protoboard = protoboard;
    }
}
