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
        int signoSalida;
        Color colorSalida;

        // Lógica de la compuerta AND
        if (entrada1.getSigno() == 1) {
            signoSalida = 0;
            colorSalida = Color.WHITE;
        } else {
            signoSalida = 1;
            colorSalida = Color.RED;
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
        if (entradaHaCambiado(fin1, fin2)) {
            calcularSalida(fin1, fin2);
            fin2.actualizarSigno2(fin2.getSigno(), fin1.getSigno());
        }
        if (entradaHaCambiado(fin3, fin4)) {
            calcularSalida(fin3, fin4);
            fin4.actualizarSigno2(fin4.getSigno(), fin3.getSigno());
        }
        if (entradaHaCambiado(fin5, fin6)){
            calcularSalida(fin5, fin6);
            fin6.actualizarSigno2(fin5.getSigno(), fin6.getSigno());
        }
        if (entradaHaCambiado(fin7, fin8)) {
            calcularSalida(fin9, fin8);
            fin8.actualizarSigno2(fin8.getSigno(), fin9.getSigno());
        }
        if (entradaHaCambiado(fin11, fin10)) {
            calcularSalida(fin11, fin10);
            fin10.actualizarSigno2(fin10.getSigno(), fin11.getSigno());
        }
        if (entradaHaCambiado(fin13, fin12)) {
            calcularSalida(fin13, fin12);
            fin12.actualizarSigno2(fin12.getSigno(), fin13.getSigno());
        }
    }

    private boolean entradaHaCambiado(Cuadrados entrada1, Cuadrados salida) {
        boolean cambio = entrada1.getSigno() != entrada1.getPrevioSignoEntrada1();

        if (cambio) {
            entrada1.setPrevioSignoEntrada1(entrada1.getSigno());
        }

        return cambio;
    }

    protected void desactivarSalidas(){
        resetearSalida(fin2);
        resetearSalida(fin4);
        resetearSalida(fin6);
        resetearSalida(fin8);
        resetearSalida(fin10);
        resetearSalida(fin12);
    }

    public Prototipo_Protoboard getProtoboard() {
        return protoboard;
    }

    public void setProtoboard(Prototipo_Protoboard protoboard) {
        this.protoboard = protoboard;
    }
}
