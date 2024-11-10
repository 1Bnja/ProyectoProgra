package org.example.prototipo.protoboard;

import javafx.scene.Node;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;

public class ChipNot extends Chip{

    // Constructor de la clase Chip
    public ChipNot(){
        super("Chip NOT");
    }

    protected void calcularSalida(Cuadrados entrada1, Cuadrados salida) {
        // Verificar si ambas entradas están energizadas
        System.out.println("Signo de entrada1: " + entrada1.getSigno());

        int signoSalida;
        Color colorSalida;

        if (entrada1.getSigno() == 1 ) {

            signoSalida = 0;
            colorSalida = Color.WHITE;
        } else {

            signoSalida = 1;
            colorSalida = Color.RED;
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
        calcularSalida(fin1, fin2);
        calcularSalida(fin3, fin4);
        calcularSalida(fin5, fin6);
        calcularSalida(fin9, fin8);
        calcularSalida(fin11, fin10);
        calcularSalida(fin13, fin12);
    }

    // Getters y setters para el protoboard
    public Prototipo_Protoboard getProtoboard() {
        return protoboard;
    }

    public void setProtoboard(Prototipo_Protoboard protoboard) {
        this.protoboard = protoboard;
    }
}
