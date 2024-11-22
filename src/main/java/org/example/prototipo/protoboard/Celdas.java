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
    private GridPane gridPane;
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

        gridPane = new GridPane();
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

    public void asignarLetra(int col, int letra){
        List<Cuadrados> columna = grid.get(col);
        for (Cuadrados cuadrado : columna) {
            if(letra!=0){
                cuadrado.setLetra(letra);
            }
        }
    }

    // Método para alternar una columna entre diferentes estados según el signo
    public void alternarColumna(int columnaIndex, int signo, double voltaje) {
        List<Cuadrados> columna = grid.get(columnaIndex);
        Color color = obtenerColor(signo);

        boolean columnaQuemada = false;
        boolean columnaCambiada = false;

        for (Cuadrados c : columna) {
            if (c.getSigno() != signo) {
                columnaCambiada = true;
                if (signo == 3 || signo == 0) {
                    c.setSigno(0);
                    c.setFill(Color.WHITE);
                    c.setVoltaje(0);
                    System.out.println("Se apagó la columna");
                } else if (signo == 2) {
                    columnaQuemada = true;
                    c.setSigno(2);
                    c.setFill(Color.OLIVE);
                    c.setVoltaje(voltaje);
                } else {
                    // Actualizar la celda con el nuevo signo y color si no está apagada ni quemada
                    c.setSigno(signo);
                    c.setFill(color);
                    c.setVoltaje(voltaje);
                }
                System.out.println("El voltaje de la celda es: " + c.getVoltaje());
            }
        }

        // Mostrar la alerta si alguna celda de la columna está quemada
        if (columnaQuemada) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("ALERTA");
            alert.setHeaderText(null);
            alert.setContentText("¡OH NO! LA COLUMNA SE QUEMÓ");
            alert.showAndWait();
        }

        // Solo notificamos al protoboard si realmente hubo cambios
        if (columnaCambiada) {
            protoboard.notificarLEDSConectados();
            protoboard.notificarChipsConectados();
        }
    }

    private Color obtenerColor(int signo) {
        switch (signo) {
            case -1: return Color.BLUE;
            case 1: return Color.RED;
            case 2: return Color.OLIVE;
            default: return Color.WHITE;
        }
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
        return columna.get(fila).getVoltaje();
    }
    public double getVoltaje2(int col){
        List<Cuadrados> columna = grid.get(col);
        double voltaje= columna.get(2).getVoltaje();
        return voltaje;
    }

    public void asignarLetra(int letra){
        
        for(List<Cuadrados> c : grid)
            for (Cuadrados col : c) {
                col.setSigno(letra);
                    }
        if(letra==1){
            System.out.println("letra en celda es A");
        } else if (letra==2) {
            System.out.println("letra en celda es B");
        }else if (letra==3) {
            System.out.println("letra en celda es C");
        }else if (letra==4) {
            System.out.println("letra en celda es D");
        }else if (letra==5) {
            System.out.println("letra en celda es E");
        }else if (letra==6) {
            System.out.println("letra en celda es F");
        }else if (letra==7) {
            System.out.println("letra en celda es G");
        }
    }

    public int getAsignarLetra(int row, int col){
        List<Cuadrados> columna = grid.get(col);
        return columna.get(row).getLetra();
    }
    public void setController(Controller_Builder controller){
        this.controller= controller;
    }

    public GridPane getGridPane() {
        return gridPane;
    }
}
