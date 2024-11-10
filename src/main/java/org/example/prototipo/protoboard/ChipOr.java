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
        // Verificar si ambas entradas están energizadas
        System.out.println("Signo de entrada1: " + entrada1.getSigno() + ", Signo de entrada2: " + entrada2.getSigno());

        int signoSalida;
        Color colorSalida;

        if (entrada1.getSigno() == 1 || entrada2.getSigno() == 1) {
            // Energizar la salida
            signoSalida = 1;
            colorSalida = Color.RED;
        } else {
            // Desenergizar la salida
            signoSalida = 0;
            colorSalida = Color.WHITE;
        }

        // Actualizar la pata de salida
        salida.setSigno(signoSalida);
        salida.setFill(colorSalida);

        // Actualizar la celda conectada a la salida y toda la columna
        Node salidaCellNode = salida.getCeldaConectada();
        if (salidaCellNode != null && salidaCellNode instanceof Cuadrados) {
            Cuadrados salidaCell = (Cuadrados) salidaCellNode;

            // Obtener la referencia al objeto Celdas correspondiente
            Celdas celdasCorrespondientes = null;
            if (protoboard.getCelda1().getChildren().contains(salidaCell.getParent())) {
                celdasCorrespondientes = protoboard.getCelda1();
            } else if (protoboard.getCelda2().getChildren().contains(salidaCell.getParent())) {
                celdasCorrespondientes = protoboard.getCelda2();
            }

            if (celdasCorrespondientes != null) {
                // Obtener el índice de columna
                Integer colIndex = GridPane.getColumnIndex(salidaCell);
                if (colIndex != null) {
                    int columna = colIndex - 1; // Ajustar si es necesario
                    // Actualizar toda la columna
                    celdasCorrespondientes.alternarColumna(columna, signoSalida, signoSalida == 1 ? 5.0 : 0.0);
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
