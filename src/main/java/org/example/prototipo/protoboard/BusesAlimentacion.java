package org.example.prototipo.protoboard;

import javafx.scene.Group;
import javafx.scene.control.Alert;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;

import java.util.ArrayList;
import java.util.List;

public class BusesAlimentacion extends Group {

    private List<List<Cuadrados>> buses;
    private Prototipo_Protoboard protoboard;

    // Constructor de la clase
    public BusesAlimentacion(double desplazamientoX, double desplazamientoY, char[] simbolos) {
        double tamanioCeldas = 13;
        double espacioCeldas = 11;
        buses = new ArrayList<>();


        GridPane gridPane = new GridPane();
        gridPane.setHgap(15);
        gridPane.setVgap(espacioCeldas);

        // Agrega los símbolos en las columnas extremas (izquierda y derecha).
        for (int j = 0; j < simbolos.length; j++) {
            Text simboloIzquierda = new Text(String.valueOf(simbolos[j]));
            simboloIzquierda.setStroke(Color.BLACK);
            simboloIzquierda.setRotate(270);
            GridPane.setConstraints(simboloIzquierda, 0, j);
            gridPane.getChildren().add(simboloIzquierda);

            Text simboloDerecha = new Text(String.valueOf(simbolos[j]));
            simboloDerecha.setStroke(Color.BLACK);
            simboloDerecha.setRotate(270);
            GridPane.setConstraints(simboloDerecha, 26, j);
            gridPane.getChildren().add(simboloDerecha);
        }

        // Crea dos filas de celdas para los buses.
        for (int j = 0; j < 2; j++) {
            List<Cuadrados> filaBus = new ArrayList<>();
            int columnaIndex = 1;

            // Crea las celdas (Cuadrados) en cada fila.
            for (int i = 0; i < 25; i++) {
                Cuadrados cuadrado = new Cuadrados(12, 2);
                cuadrado.setStroke(Color.BLACK);
                cuadrado.setFill(Color.WHITE);
                cuadrado.setSigno(0);

                GridPane.setConstraints(cuadrado, columnaIndex, j);
                cuadrado.setTranslateX(-10);
                gridPane.getChildren().add(cuadrado);

                filaBus.add(cuadrado);
                columnaIndex++;
            }

            buses.add(filaBus);
        }

        // Ajusta la posición del gridPane en el escenario.
        gridPane.setLayoutX(desplazamientoX);
        gridPane.setLayoutY(desplazamientoY);
        this.getChildren().add(gridPane);  // Añadir el gridPane al grupo de la clase.
    }

    // Método para cambiar el estado de una fila de celdas en el bus.
    public void toggleFilaBus(int filaIndex, int signo, double voltaje) {
        List<Cuadrados> filaBus = buses.get(filaIndex);  // Obtiene la fila específica.
        Color color;

        // Asigna el color dependiendo del valor de 'signo'.
        if (signo == -1) {
            color = Color.BLUE;  // Signo negativo: color azul.
        } else if (signo == 1) {
            color = Color.RED;  // Signo positivo: color rojo.
        } else {
            color = Color.WHITE;  // Signo 0: color blanco.
        }

        boolean bandera = false;  // Bandera para indicar si hay un conflicto de signos.

        // Itera sobre cada celda de la fila.
        for (Cuadrados c : filaBus) {
            if (c.getSigno() == 0) {  // Si la celda está apagada (signo 0), la enciende con el signo y color dados.
                c.setSigno(signo);
                c.setFill(color);
                c.setVoltaje(voltaje);
            } else if (signo == 3) {  // Si el signo es 3, apaga todas las celdas.
                System.out.println("Se apagó");
                c.setSigno(0);
                c.setFill(Color.WHITE);
                c.setVoltaje(voltaje);
            } else if (signo == 0) {  // Si el signo es 0, también apaga las celdas.
                System.out.println("Se apagó la columna");
                c.setSigno(0);
                c.setFill(Color.WHITE);
                c.setVoltaje(voltaje);
            } else if (c.getSigno() == signo) {  // Si la celda ya tiene el mismo signo, solo ajusta el color.
                c.setFill(color);
                c.setSigno(signo);
                c.setVoltaje(voltaje);
            } else {  // Si hay conflicto de signos, la bandera se activa y la celda se marca en verde oliva.
                bandera = true;
                c.setFill(Color.OLIVE);
                c.setSigno(2);
            }
            //System.out.println("El voltaje del bus es: "+ c.getVoltaje());
        }

        // Si la bandera está activada, muestra una alerta indicando que el bus se ha quemado.
        if (bandera) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("ALERTA");
            alert.setHeaderText(null);
            alert.setContentText("OH NO!! EL BUS SE QUEMÓ AAAAAAAA");
            alert.showAndWait();
        }

        System.out.println("Voltaje de es: "+filaBus.get(filaIndex).getVoltaje());

        // Notifica a los componentes conectados del cambio.
        protoboard.notificarLEDSConectados();
        protoboard.notificarChipsConectados();
    }

    // Método para obtener el signo de una celda en una fila y columna específica.
    public int getSigno(int fila, int col) {
        System.out.println(col + "| " + fila);
        List<Cuadrados> columna = buses.get(fila);  // Obtiene la fila específica.
        return columna.get(col).getSigno();  // Devuelve el signo de la celda en la columna especificada.
    }

    // Método para establecer el signo de todas las celdas de una fila.
    public void setSigno(int fila, int col, int signo) {
        for (int i = 0; i < buses.get(fila).size(); i++) {
            this.buses.get(fila).get(i).setSigno(signo);  // Cambia el signo de todas las celdas de la fila.
        }
    }

    public double getVoltaje(int fila, int col) {
        List<Cuadrados> columna = buses.get(fila);
        System.out.println("Voltaje: "+columna.get(col).getVoltaje());
        return columna.get(col).getVoltaje();
    }


    // Método para encender/apagar todas las celdas según el signo.
    public void onOff(int signo, boolean trueColor) {
        Color color = Color.WHITE;

        // Si 'trueColor' es falso, asigna el color según el signo dado.
        if (!trueColor) {
            if (signo == -1) {
                color = Color.BLUE;
            } else if (signo == 1) {
                color = Color.RED;
            } else {
                color = Color.WHITE;
            }
        }

        // Cambia el color de todas las celdas en todas las filas.
        for (List<Cuadrados> c : buses) {
            for (Cuadrados col : c) {
                // Si 'trueColor' es verdadero, ajusta el color según el signo actual de cada celda.
                if (trueColor) {
                    if (col.getSigno() == -1) {
                        color = Color.BLUE;

                    } else if (col.getSigno() == 1) {
                        color = Color.RED;
                    } else {
                        color = Color.WHITE;
                    }
                }
                col.setFill(color);  // Aplica el color.
            }
        }
    }
}
