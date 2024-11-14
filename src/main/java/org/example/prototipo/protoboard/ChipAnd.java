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

    // En ChipAnd.java
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

    private boolean entradaHaCambiado(Cuadrados entrada1, Cuadrados entrada2, Cuadrados salida) {
        boolean cambio = entrada1.getSigno() != entrada1.getPrevioSignoEntrada1() || entrada2.getSigno() != entrada2.getPrevioSignoEntrada2();

        if (cambio) {
            entrada1.setPrevioSignoEntrada1(entrada1.getSigno());
            entrada2.setPrevioSignoEntrada2(entrada2.getSigno());
        }

        return cambio;
    }

    protected void verificarEncendido(){
        boolean encendido = false;

        double voltajeFin14 = fin14.getVoltaje();
        double voltajeFin7 = fin7.getVoltaje();
        int signoFin14 = fin14.getSigno();
        int signoFin7 = fin7.getSigno();

        if (voltajeFin14 > 0 && signoFin14 == 1 && voltajeFin7 > 0 && signoFin7 == -1){
            encendido = true;
        }

        if (encendido){
            calcularSalidas();
        } else {
            desactivarSalidas();
        }
    }

    protected void desactivarSalidas(){
        resetearSalida(fin3);
        resetearSalida(fin6);
        resetearSalida(fin8);
        resetearSalida(fin11);
    }

    @Override
    protected void checkFinConnections() {
        updateFinConnection(fin1);
        updateFinConnection(fin2);
        updateFinConnection(fin3);
        updateFinConnection(fin4);
        updateFinConnection(fin5);
        updateFinConnection(fin6);
        updateFinConnection(fin7);
        updateFinConnection(fin8);
        updateFinConnection(fin9);
        updateFinConnection(fin10);
        updateFinConnection(fin11);
        updateFinConnection(fin12);
        updateFinConnection(fin13);
        updateFinConnection(fin14);
        verificarEncendido();
    }
    private void resetearSalida(Cuadrados salida) {
        salida.setSigno(0);
        salida.setFill(Color.WHITE);

        // Actualizar la columna de la protoboard conectada a la salida, si existe
        if (salida.getCeldaConectada() != null) {
            Node celdaConectada = salida.getCeldaConectada();
            GridPane gridPane = (GridPane) celdaConectada.getParent();

            // Obtener los índices de fila y columna
            Integer colIndex = GridPane.getColumnIndex(celdaConectada);
            Integer rowIndex = GridPane.getRowIndex(celdaConectada);

            if (colIndex != null && rowIndex != null) {
                int columna = colIndex - 1;
                int fila = rowIndex;

                // Actualizar la celda en el protoboard a un estado apagado
                if (gridPane == protoboard.getCelda1().getGridPane()) {
                    protoboard.getCelda1().alternarColumna(columna, 0, 0);
                } else if (gridPane == protoboard.getCelda2().getGridPane()) {
                    protoboard.getCelda2().alternarColumna(columna, 0, 0);
                }
            }
        }
    }

    // Getters y setters para el protoboard
    public Prototipo_Protoboard getProtoboard() {
        return protoboard;
    }

    public void setProtoboard(Prototipo_Protoboard protoboard) {
        this.protoboard = protoboard;
    }
}
