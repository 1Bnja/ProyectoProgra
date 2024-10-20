package org.example.prototipo.protoboard;

import javafx.scene.Group;
import javafx.scene.control.Alert;
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
        gridPane.setHgap(15);
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
            GridPane.setConstraints(simboloDerecha, 26, j);
            gridPane.getChildren().add(simboloDerecha);
        }


        for (int j = 0; j < 2; j++) {
            List<Cuadrados> filaBus = new ArrayList<>();
            int columnaIndex = 1;

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

        gridPane.setLayoutX(desplazamientoX);
        gridPane.setLayoutY(desplazamientoY);
        this.getChildren().add(gridPane);
    }


    public void toggleFilaBus(int filaIndex, int signo) {
        List<Cuadrados> filaBus = buses.get(filaIndex);
        Color color;

        if (signo == -1) {
            color = Color.BLUE;
        } else if (signo == 1) {
            color = Color.RED;
        } else {
            color = Color.WHITE;
        }

        boolean bandera = false;

        for (Cuadrados c : filaBus) {
            if (c.getSigno() == 0) {
                c.setSigno(signo);
                c.setFill(color);
            } else if (signo == 3) {
                System.out.println("Se apago");
                c.setSigno(0);
                c.setFill(Color.WHITE);
            } else if (signo == 0) {
                System.out.println("Se apago la columna");
                c.setSigno(0);
                c.setFill(Color.WHITE);

            } else if (c.getSigno()==signo) {
                c.setFill(color);
                c.setSigno(signo);
            }
            else {

                bandera = true;
                c.setFill(Color.OLIVE);
                c.setSigno(2);
            }

        }
        if (bandera == true) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("ALERTA");
            alert.setHeaderText(null);
            alert.setContentText("OH NO!! EL BUS SE QUEMÓ AAAAAAAA");

            alert.showAndWait();

        }

        Prototipo_Protoboard.notificarComponentesConectados();
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

    public void onOff( int signo, boolean trueColor) {
        Color color = Color.WHITE;
        if(!trueColor) {
            if (signo == -1) {
                color = Color.BLUE;
            } else if (signo == 1) {
                color = Color.RED;
            } else {
                color = Color.WHITE;
            }
        }
        for(List<Cuadrados> c : buses)
            for (Cuadrados col : c) {
                if(trueColor) {
                    if (col.getSigno() == -1) {
                        color = Color.BLUE;
                    } else if (col.getSigno() == 1) {
                        color = Color.RED;
                    } else {
                            color = Color.WHITE;
                    }
                }
                col.setFill(color);
            }
    }

}
