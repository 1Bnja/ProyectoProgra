package org.example.prototipo.protoboard;

import javafx.scene.Group;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;

import java.util.List;

public class Cable extends Pane {

    Line line;
    static Cuadrados inicio;
    Cuadrados fin;
    private Group nodo = new Group();
    private Bateria bateria= new Bateria();
    private Color positivo= bateria.conectorPositivo.getFillColor();
    private Color negativo= bateria.conectorNegativo.getFillColor();
    private Celdas celdas;


    private double mouseX, mouseY;



    public Cable(Celdas celdas) {
        this.celdas = celdas; // Inicializa la referencia
    }

    public Cable() {
        // Constructor vacío, no realiza ninguna inicialización específica
    }
    public void Crear_linea() {

        // Crear línea
        line = new Line(50, 50, 100, 100);
        line.setStroke(Color.BURLYWOOD);
        line.setStrokeWidth(7);

        // Crear puntos de arrastre
        inicio = Esquina_Estirable(line.getStartX(), line.getStartY());
        fin = Esquina_Estirable(line.getEndX(), line.getEndY());

        // Añadir manejadores de eventos de arrastre
        inicio.setOnMouseDragged(e -> Arrastre(e, line, true));
        inicio.setFill(Color.RED);
        fin.setOnMouseDragged(e -> Arrastre(e, line, false));

        // Mover linea completa
        line.setOnMousePressed(this::IniciarMovimientoLinea);
        line.setOnMouseDragged(this::MoverLineaCompleta);


        line.setOnMousePressed(e -> {
            nodo.toFront(); // Mover al frente al iniciar el movimiento
            IniciarMovimientoLinea(e);
        });


        inicio.setOnMouseReleased(event ->{
            if(inicio !=null){
                if(bateria.conectorPositivo.getBoundsInParent().intersects(inicio.getBoundsInParent())){
                    inicio.setFill(bateria.conectorPositivo.getFill());
                    fin.setFill(bateria.conectorPositivo.getFill());
                    line.setStroke(bateria.conectorPositivo.getFillColor());
                } else if (bateria.conectorNegativo.getBoundsInParent().intersects(inicio.getBoundsInParent())){
                    inicio.setFill(bateria.conectorNegativo.getFill());
                    fin.setFill(bateria.conectorNegativo.getFill());
                    line.setStroke(bateria.conectorNegativo.getFillColor());
                }else{
                    System.out.println("no se pudo :p");}
                }});



        // Añadir línea y puntos al panel
        nodo.getChildren().addAll(line, inicio, fin);
        this.getChildren().add(nodo);


        this.setPickOnBounds(false);
    }

    private void IniciarMovimientoLinea(MouseEvent event) {
        mouseX = event.getX();
        mouseY = event.getY();
    }

    private void MoverLineaCompleta(MouseEvent event) {
        double offsetX = event.getX() - mouseX;
        double offsetY = event.getY() - mouseY;

        line.setStartX(line.getStartX() + offsetX);
        line.setStartY(line.getStartY() + offsetY);
        line.setEndX(line.getEndX() + offsetX);
        line.setEndY(line.getEndY() + offsetY);

        Actual_arrastrePuntos();

        mouseX = event.getX();
        mouseY = event.getY();
    }

    private void Arrastre(MouseEvent event, Line line, boolean isStartPoint) {
        double offsetX = event.getX();
        double offsetY = event.getY();

        if (isStartPoint) {
            line.setStartX(offsetX);
            line.setStartY(offsetY);
        } else {
            line.setEndX(offsetX);
            line.setEndY(offsetY);
        }

        Actual_arrastrePuntos();
    }

    private Cuadrados Esquina_Estirable(double x, double y) {
        Cuadrados square = new Cuadrados(12, 2); // Tamaño del cuadrado
        square.setX(x - square.getWidth() / 2);  // Centrar el cuadrado en la posición x
        square.setY(y - square.getHeight() / 2); // Centrar el cuadrado en la posición y
        square.setFill(Color.BROWN);  // Color del cuadrado

        square.setOnMousePressed(e -> nodo.toFront());
        return square;
    }

    private void Actual_arrastrePuntos() {
        inicio.setX(line.getStartX() - inicio.getWidth() / 2); // Centrar en X
        inicio.setY(line.getStartY() - inicio.getHeight() / 2); // Centrar en Y

        fin.setX(line.getEndX() - fin.getWidth() / 2); // Centrar en X
        fin.setY(line.getEndY() - fin.getHeight() / 2);
    }






}