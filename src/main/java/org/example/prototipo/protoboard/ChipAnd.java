package org.example.prototipo.protoboard;

import javafx.scene.Node;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;

public class ChipAnd extends Chip{

    // Constructor de la clase Chip
    public ChipAnd(){
        super("Chip AND");
    }

    // Implementa la lógica de la compuerta AND para determinar el signo y color de la salida basándose en las entradas.
    @Override
    protected void calcularSalida(Cuadrados entrada1, Cuadrados entrada2, Cuadrados salida) {
        int signoSalida;
        Color colorSalida;

        // Lógica de la compuerta AND
        if (entrada1.getSigno() == 1 && entrada2.getSigno() == 1) {
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

    // Actualiza el estado de una celda conectada a la salida del chip, interactuando con el GridPane correspondiente del protoboard.
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

    // Evalúa las combinaciones de entradas del chip y calcula las salidas, verificando si las entradas han cambiado antes de realizar el cálculo.
    protected void calcularSalidas() {

        if (entradaHaCambiado(fin1, fin2, fin3)) {
            calcularSalida(fin1, fin2, fin3);
            fin3.actualizarSigno(fin3.getSigno(), fin1.getSigno(), fin2.getSigno());
        }
        if (entradaHaCambiado(fin4, fin5, fin6)) {
            calcularSalida(fin4, fin5, fin6);
            fin6.actualizarSigno(fin6.getSigno(), fin4.getSigno(), fin5.getSigno());
        }
        if (entradaHaCambiado(fin9, fin10, fin8)) {
            calcularSalida(fin9, fin10, fin8);
            fin8.actualizarSigno(fin8.getSigno(), fin9.getSigno(), fin10.getSigno());
        }
        if (entradaHaCambiado(fin12, fin13, fin11)) {
            calcularSalida(fin12, fin13, fin11);
            fin11.actualizarSigno(fin11.getSigno(), fin12.getSigno(), fin13.getSigno());
        }
    }

    // Comprueba si las entradas han cambiado desde la última evaluación, actualizando los valores previos si detecta cambios.
    private boolean entradaHaCambiado(Cuadrados entrada1, Cuadrados entrada2, Cuadrados salida) {
        boolean cambio = entrada1.getSigno() != entrada1.getPrevioSignoEntrada1() || entrada2.getSigno() != entrada2.getPrevioSignoEntrada2();

        if (cambio) {
            entrada1.setPrevioSignoEntrada1(entrada1.getSigno());
            entrada2.setPrevioSignoEntrada2(entrada2.getSigno());
        }

        return cambio;
    }

    // Resetea los valores y estados de las salidas del chip.
    protected void desactivarSalidas(){
        resetearSalida(fin3);
        resetearSalida(fin6);
        resetearSalida(fin8);
        resetearSalida(fin11);
    }

    // Getters y setters para el protoboard
    public Prototipo_Protoboard getProtoboard() {
        return protoboard;
    }

    public void setProtoboard(Prototipo_Protoboard protoboard) {
        this.protoboard = protoboard;
    }
}
