package org.example.prototipo.protoboard;

import javafx.scene.Group;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;

public class Cable extends Pane {

    Line line;
    public Cuadrados inicio;
    Cuadrados fin;
    private Group nodo = new Group();
    private Bateria bateria = new Bateria();
    private Color positivo = bateria.conectorPositivo.getFillColor();
    private Color negativo = bateria.conectorNegativo.getFillColor();
    private Celdas celdas;

    double origenX = Main.origenX - 430;
    double origenY = Main.origenY - 210;

    private double mouseX, mouseY;

    // Definición de límites
    private double minXLimit = 0;  // Límite mínimo en X
    private double minYLimit = 80;  // Límite mínimo en Y
    private double maxXLimit = 1270;  // Límite máximo en X (ajústalo a tu contenedor)
    private double maxYLimit = 840;  // Límite máximo en Y (ajústalo a tu contenedor)

    public Cable(Celdas celdas) {
        this.celdas = celdas;
    }

    public Cable() {
    }

    public void Crear_linea() {

        // Crear línea
        line = new Line(origenX - 50, origenY - 50, origenX - 100, origenY - 100);
        line.setStroke(Color.BURLYWOOD);
        line.setStrokeWidth(7);

        // Crear puntos de arrastre
        inicio = Esquina_Estirable(line.getStartX(), line.getStartY());
        fin = Esquina_Estirable(line.getEndX(), line.getEndY());

        // Añadir manejadores de eventos de arrastre
        inicio.setOnMouseDragged(e -> Arrastre(e, line, true));

        inicio.setOnDragDetected(e -> Arrastre(e, line, true));//SI ESTA EN FALSE, LA LINEA SE MUEVE DESDE DONDE SE MOVIO LA ULTIMA VEZ??
        //ONDA SI MUEVES EL INICIO Y DEPUES QUIERES MOVER EL FIN, EL FIN PARTE DESDE DONDE DEJASTE EL INICIO

        inicio.setFill(Color.RED);
        fin.setOnMouseDragged(e -> Arrastre(e, line, false));

        // Mover linea completa
        line.setOnMousePressed(this::IniciarMovimientoLinea);
        line.setOnMouseDragged(this::MoverLineaCompleta);

        line.setOnMousePressed(e -> {
            nodo.toFront();
            IniciarMovimientoLinea(e);
        });

        inicio.setOnMouseReleased(event -> {
            if (inicio != null) {
                if (bateria.conectorPositivo.getBoundsInParent().intersects(inicio.getBoundsInParent())) {
                    inicio.setFill(bateria.conectorPositivo.getFill());
                    fin.setFill(bateria.conectorPositivo.getFill());
                    line.setStroke(bateria.conectorPositivo.getFillColor());
                } else if (bateria.conectorNegativo.getBoundsInParent().intersects(inicio.getBoundsInParent())) {
                    inicio.setFill(bateria.conectorNegativo.getFill());
                    fin.setFill(bateria.conectorNegativo.getFill());
                    line.setStroke(bateria.conectorNegativo.getFillColor());
                } else {
                    System.out.println("no se pudo :p");
                }
            }
        });

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

        // Calcular las nuevas posiciones de inicio y fin
        double newStartX = line.getStartX() + offsetX;
        double newStartY = line.getStartY() + offsetY;
        double newEndX = line.getEndX() + offsetX;
        double newEndY = line.getEndY() + offsetY;

        // Ajustar las posiciones para que se mantengan dentro de los límites
        double clampedStartX = clamp(newStartX, minXLimit, maxXLimit);
        double clampedStartY = clamp(newStartY, minYLimit, maxYLimit);
        double clampedEndX = clamp(newEndX, minXLimit, maxXLimit);
        double clampedEndY = clamp(newEndY, minYLimit, maxYLimit);

        // Mover la línea respetando los límites
        line.setStartX(clampedStartX);
        line.setStartY(clampedStartY);
        line.setEndX(clampedEndX);
        line.setEndY(clampedEndY);

        Actual_arrastrePuntos();

        mouseX = event.getX();
        mouseY = event.getY();
    }

    private void Arrastre(MouseEvent event, Line line, boolean isStartPoint) {
        double offsetX = event.getX();
        double offsetY = event.getY();

        // Aplicar los límites a los puntos de arrastre
        offsetX = clamp(offsetX, minXLimit, maxXLimit);
        offsetY = clamp(offsetY, minYLimit, maxYLimit);

        if (isStartPoint) {
            line.setStartX(offsetX);
            line.setStartY(offsetY);
        } else {
            line.setEndX(offsetX);
            line.setEndY(offsetY);
        }

        Actual_arrastrePuntos();


    }

    private double clamp(double value, double min, double max) {
        if (value < min) return min;
        if (value > max) return max;
        return value;
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
        inicio.setX(line.getStartX() - inicio.getWidth() / 2);
        inicio.setY(line.getStartY() - inicio.getHeight() / 2);

        fin.setX(line.getEndX() - fin.getWidth() / 2);
        fin.setY(line.getEndY() - fin.getHeight() / 2);
    }
}
