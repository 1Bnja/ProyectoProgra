package org.example.prototipo.protoboard;

import javafx.geometry.Bounds;
import javafx.scene.Group;
import javafx.scene.Node;
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

    public Celdas(double desplazamientoX, double desplazamientoY, char[] letras, boolean esParteInferior) {
        double tamanioCeldas = 13;
        double espacioCeldas = 11;
        grid = new ArrayList<>();

        GridPane gridPane = new GridPane();
        gridPane.setHgap(espacioCeldas);
        gridPane.setVgap(espacioCeldas);

        // Agregar letras en los lados
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

            Text numero = new Text(String.valueOf(i + 1));
            numero.setStroke(Color.BLACK);
            numero.setRotate(270);

            if (esParteInferior) {
                GridPane.setConstraints(numero, i + 1, 6);
            } else {
                GridPane.setConstraints(numero, i + 1, 0);
            }

            gridPane.getChildren().add(numero);

            for (int j = 0; j < 5; j++) {
                Cuadrados cuadrado = new Cuadrados(12, 2);
                cuadrado.setStroke(Color.BLACK);
                cuadrado.setFill(Color.WHITE);

                final int columnaIndex = i;
                //cuadrado.setOnMouseClicked(event -> alternarColumna(columnaIndex));

                GridPane.setConstraints(cuadrado, i + 1, j + 1);
                gridPane.getChildren().add(cuadrado);

                columna.add(cuadrado);
            }

            grid.add(columna);
        }

        gridPane.setLayoutX(desplazamientoX);
        gridPane.setLayoutY(desplazamientoY);
        this.getChildren().add(gridPane);

        for(int i=0; i < gridPane.getChildren().size(); i++ ){
            Bounds boundsInScene = gridPane.getChildren().get(i).localToScene(gridPane.getChildren().get(i).getBoundsInLocal());

            double posX = boundsInScene.getMinX();
            double posY = boundsInScene.getMinY();

            System.out.println("Posición X relativa al GridPane: " + posX + ", Posición Y relativa al GridPane: " + posY);

        }



    }

    // Método para alternar una columna entre encendida y apagada
    public void alternarColumna(int columnaIndex, int signo) {
        List<Cuadrados> columna = grid.get(columnaIndex); // Obtener la columna seleccionada

        Color colorEncendido = Color.WHITE;

        if(signo ==-1){
            colorEncendido = Color.BLUE;
        } else if(signo == 1){
            colorEncendido = Color.RED;
        } else if (signo ==0) {
            colorEncendido = Color.WHITE;
        }


        for (Cuadrados c : columna) {
                c.setSigno(signo); //se setea el signo
                c.setFill(colorEncendido); // Apagar
            }

    }

    public int getSigno(int fila, int col){
        System.out.println(col +"|"+ fila);
        List<Cuadrados> columna = grid.get(col);
        return columna.get(fila).getSigno();
    }
}





