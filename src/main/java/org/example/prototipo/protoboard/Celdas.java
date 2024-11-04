package org.example.prototipo.protoboard;

import javafx.geometry.Bounds;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.input.TransferMode;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.text.Text;
import java.util.ArrayList;
import java.util.List;

public class Celdas extends Group {

    public List<List<Cuadrados>> grid;
    Bateria bateria = new Bateria();
    public Cuadrados cuadradoSeleccionado;
    public Cuadrados cuadradoSeleccionado2;
    private Prototipo_Protoboard protoboard;
    Controller_Builder controller;

    // Constructor de la clase Celdas
    public Celdas(double desplazamientoX, double desplazamientoY, char[] letras, boolean esParteInferior, Prototipo_Protoboard protoboard) {

        this.protoboard = protoboard;

        double espacioCeldas = 11;
        grid = new ArrayList<>();

        GridPane gridPane = new GridPane();
        gridPane.setHgap(espacioCeldas);
        gridPane.setVgap(espacioCeldas);

        // Agregar letras en los lados del GridPane
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

        // Crear la cuadrícula de celdas
        for (int i = 0; i < 30; i++) {
            List<Cuadrados> columna = new ArrayList<>();

            // Crear el número de columna y agregarlo al GridPane
            Text numero = new Text(String.valueOf(i + 1));
            numero.setStroke(Color.BLACK);
            numero.setRotate(270);

            if (esParteInferior) {
                GridPane.setConstraints(numero, i + 1, 6);
            } else {
                GridPane.setConstraints(numero, i + 1, 0);
            }

            gridPane.getChildren().add(numero);

            // Crear las celdas de la columna
            for (int j = 0; j < 5; j++) {
                Cuadrados cuadrado = new Cuadrados(12, 2);
                cuadrado.setStroke(Color.BLACK);
                cuadrado.setFill(Color.WHITE);

                GridPane.setConstraints(cuadrado, i + 1, j );
                cuadrado.setTranslateY(20);
                gridPane.getChildren().add(cuadrado);

                columna.add(cuadrado);
            }

            grid.add(columna);
        }
        // Establecer la posición del GridPane
        gridPane.setLayoutX(desplazamientoX);
        gridPane.setLayoutY(desplazamientoY);
        this.getChildren().add(gridPane);

        // Imprimir las posiciones de cada elemento en el GridPane (para depuración)
        for(int i= 0; i < gridPane.getChildren().size(); i++ ){
            Bounds boundsInScene = gridPane.getChildren().get(i).localToScene(gridPane.getChildren().get(i).getBoundsInLocal());

            double posX = boundsInScene.getMinX();
            double posY = boundsInScene.getMinY();

            //System.out.println("Posición X relativa al GridPane: " + posX + ", Posición Y relativa al GridPane: " + posY);
        }
    }


    // Método para alternar una columna entre diferentes estados según el signo
    public void alternarColumna(int columnaIndex, int signo, double voltaje) {
        List<Cuadrados> columna = grid.get(columnaIndex);
        Color color;


        // Determinar el color basado en el signo
        if(signo == -1) {
            color = Color.BLUE;
        } else if(signo == 1) {
            color = Color.RED;
        } else {
            color = Color.WHITE;
        }

        boolean bandera= false;

        for (Cuadrados c : columna) {
            if(c.getSigno()==0 ) {
                // Si el cuadrado está apagado, establecer el nuevo signo y color
                c.setSigno(signo);
                c.setFill(color);
                c.setVoltaje(voltaje);
            } else if (signo==3) {
                // Apagar el cuadrado
                System.out.println("Se apagó");
                c.setSigno(0);
                c.setFill(Color.WHITE);
                c.setVoltaje(voltaje);
            } else if (signo==0) {
                // Apagar la columna completa
                System.out.println("Se apagó la columna");
                c.setSigno(0);
                c.setFill(Color.WHITE);
                c.setVoltaje(voltaje);
            } else if (c.getSigno() == signo) {
                // Mantener el mismo signo y color
                c.setSigno(signo);
                c.setFill(color);
                c.setVoltaje(voltaje);
            } else if (signo==2) {
                // Indicar que ocurrió una sobrecarga o corto circuito
                bandera = true;
                c.setFill(Color.OLIVE);
                c.setSigno(signo);
                c.setVoltaje(voltaje);
            } else {
                // Indicar que ocurrió una sobrecarga o corto circuito
                bandera = true;
                c.setFill(Color.OLIVE);
                c.setSigno(2);
                c.setVoltaje(voltaje);
            }
            System.out.println("El voltaje la celda es: "+ c.getVoltaje());
        }
        if(bandera==true) {
            // Mostrar una alerta indicando que la columna se quemó
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("ALERTA");
            alert.setHeaderText(null);
            alert.setContentText("¡OH NO! LA COLUMNA SE QUEMÓ");

            alert.showAndWait();
        }

        // Notificar a los componentes conectados en el protoboard
        Prototipo_Protoboard.notificarComponentesConectados();
    }

    // Método para encender o apagar las celdas según el signo y un indicador de color real
    public void onOff(int signo, boolean trueColor) {
        Color color= Color.WHITE;

        if(!trueColor) {
            // Determinar el color basado en el signo
            if (signo == -1) {
                color = Color.BLUE;
            } else if (signo == 1) {
                color = Color.RED;
            } else {
                color = Color.WHITE;
            }
        }
        // Recorrer todas las celdas y actualizar su color
        for(List<Cuadrados> c : grid)
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
                col.setSigno(signo);
                col.setFill(color);
            }
    }

    // Método para obtener el signo de una celda específica
    public int getSigno(int fila, int col){
        System.out.println(col +"|"+ fila);
        List<Cuadrados> columna = grid.get(col);
        return columna.get(fila).getSigno();
    }

    public double getVoltaje(int fila, int col){
        List<Cuadrados> columna = grid.get(col);
        System.out.println("Voltaje de "+ fila+"|"+col+" es: "+columna.get(fila).getVoltaje());
        return columna.get(col).getVoltaje();
    }

    public void setController(Controller_Builder controller){
        this.controller= controller;
    }
}
