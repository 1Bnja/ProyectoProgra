package org.example.prototipo.protoboard;

import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;

public class Cable extends Pane {

    public Line line;
    private Cuadrados inicio;
    private Cuadrados fin;
    private double mouseX, mouseY;
    Bateria bateria= new Bateria();

    public Cable(double startX, double startY, double endX, double endY) {

        line = new Line(startX, startY, endX, endY);
        line.setStroke(Color.GREENYELLOW);
        line.setStrokeWidth(7);

        // Crear los extremos del cable con la clase Cuadrados
        inicio = new Cuadrados(12, 2);
        fin = new Cuadrados(12, 2);
        inicio.setFillColor(Color.RED);
        fin.setFillColor(Color.RED);


        inicio.setX(startX - inicio.getWidth() / 2);
        inicio.setY(startY - inicio.getHeight() / 2);
        fin.setX(endX - fin.getWidth() / 2);
        fin.setY(endY - fin.getHeight() / 2);


        this.setPickOnBounds(false);

        inicio.setOnMouseDragged(event -> arrastrarExtremo(event, true));
        fin.setOnMouseDragged(event -> arrastrarExtremo(event, false));

        line.setOnMousePressed(this::iniciarMovimientoLinea);
        line.setOnMouseDragged(this::moverLineaCompleta);

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

        this.getChildren().addAll(line, inicio, fin);
    }

    private void arrastrarExtremo(MouseEvent event, boolean esInicio) {
        event.consume();  // Consumir el evento para evitar que se propague

        double offsetX = event.getX();
        double offsetY = event.getY();

        if (esInicio) {

            line.setStartX(offsetX);
            line.setStartY(offsetY);
        } else {

            line.setEndX(offsetX);
            line.setEndY(offsetY);
        }


        actualizarPosiciones();
    }


    private void iniciarMovimientoLinea(MouseEvent event) {
        mouseX = event.getX();
        mouseY = event.getY();
    }

    private void moverLineaCompleta(MouseEvent event) {
        event.consume();

        double offsetX = event.getX() - mouseX;
        double offsetY = event.getY() - mouseY;


        line.setStartX(line.getStartX() + offsetX);
        line.setStartY(line.getStartY() + offsetY);
        line.setEndX(line.getEndX() + offsetX);
        line.setEndY(line.getEndY() + offsetY);


        actualizarPosiciones();

        mouseX = event.getX();
        mouseY = event.getY();
    }

    private void actualizarPosiciones() {
        inicio.setX(line.getStartX() - inicio.getWidth() / 2);
        inicio.setY(line.getStartY() - inicio.getHeight() / 2);

        fin.setX(line.getEndX() - fin.getWidth() / 2);
        fin.setY(line.getEndY() - fin.getHeight() / 2);
    }
}
