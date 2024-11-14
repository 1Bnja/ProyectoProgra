package org.example.prototipo.protoboard;

import javafx.geometry.Bounds;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.scene.shape.Polygon;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

public abstract class Chip extends Pane {
    protected Group nodo = new Group();

    // Definición de los puntos finales (patas) y líneas del chip
    protected Cuadrados fin1, fin2, fin3, fin4, fin5, fin6, fin7, fin8, fin9, fin10, fin11, fin12, fin13, fin14;
    protected Line pata1, pata2, pata3, pata4, pata5, pata6, pata7, pata8, pata9, pata10, pata11, pata12, pata13, pata14;

    // Posición del mouse para manejar el arrastre
    protected double mouseX;
    protected double mouseY;
    protected boolean line_en_arrastre = false;

    // Coordenadas de origen
    protected double origenX = Main.origenX;
    protected double origenY = Main.origenY;

    protected Prototipo_Protoboard protoboard;

    // Constructor de la clase Chip
    public Chip(String nombre) {

        double posX = origenX - 570;
        double posY = origenY - 105;
        int ancho = 155;
        int alto = 70;

        Cuadrados chip = new Cuadrados(ancho, alto, posX, posY, Color.BLACK);

        // Crear las patas del chip
        pata14 = crearLinea(origenX - 565, origenY - 105, origenX - 565, origenY - 125);
        pata13 = crearLinea(origenX - 541, origenY - 105, origenX - 541, origenY - 125);
        pata12 = crearLinea(origenX - 517, origenY - 105, origenX - 517, origenY - 125);
        pata11 = crearLinea(origenX - 493, origenY - 105, origenX - 493, origenY - 125);
        pata10 = crearLinea(origenX - 469, origenY - 105, origenX - 469, origenY - 125);
        pata9 = crearLinea(origenX - 445, origenY - 105, origenX - 445, origenY - 125);
        pata8 = crearLinea(origenX - 421, origenY - 105, origenX - 421, origenY - 125);

        pata1 = crearLinea(origenX - 565, origenY - 35, origenX - 565, origenY - 15);
        pata2 = crearLinea(origenX - 541, origenY - 35, origenX - 541, origenY - 15);
        pata3 = crearLinea(origenX - 517, origenY - 35, origenX - 517, origenY - 15);
        pata4 = crearLinea(origenX - 493, origenY - 35, origenX - 493, origenY - 15);
        pata5 = crearLinea(origenX - 469, origenY - 35, origenX - 469, origenY - 15);
        pata6 = crearLinea(origenX - 445, origenY - 35, origenX - 445, origenY - 15);
        pata7 = crearLinea(origenX - 421, origenY - 35, origenX - 421, origenY - 15);


        // **Agregar el texto "CHIP" en el centro**
        Text textoChip = new Text(nombre);
        textoChip.setFill(Color.WHITE);
        textoChip.setFont(Font.font("Arial", 15));
        // Posicionar el texto en el centro del chip

        double centerX = chip.getX() + chip.getWidth() / 2 - textoChip.getLayoutBounds().getWidth() / 2;
        double centerY = chip.getY() + chip.getHeight() / 2 + textoChip.getLayoutBounds().getHeight() / 4;

        textoChip.setX(centerX);
        textoChip.setY(centerY);

        fin1 = Esquina_Estirable(pata1, Color.ORANGE);
        fin2 = Esquina_Estirable(pata2, Color.ORANGE);
        fin3 = Esquina_Estirable(pata3, Color.ORANGE);
        fin4 = Esquina_Estirable(pata4, Color.ORANGE);
        fin5 = Esquina_Estirable(pata5, Color.ORANGE);
        fin6 = Esquina_Estirable(pata6, Color.ORANGE);
        fin7 = Esquina_Estirable(pata7, Color.BLUE);
        fin8 = Esquina_Estirable(pata8, Color.ORANGE);
        fin9 = Esquina_Estirable(pata9, Color.ORANGE);
        fin10 = Esquina_Estirable(pata10, Color.ORANGE);
        fin11 = Esquina_Estirable(pata11, Color.ORANGE);
        fin12 = Esquina_Estirable(pata12, Color.ORANGE);
        fin13 = Esquina_Estirable(pata13, Color.ORANGE);
        fin14 = Esquina_Estirable(pata14, Color.RED);


        // Configurar el arrastre para cada pata y su punto estirable
        configurarArrastre(fin1, pata1);
        configurarArrastre(fin2, pata2);
        configurarArrastre(fin3, pata3);
        configurarArrastre(fin4, pata4);
        configurarArrastre(fin5, pata5);
        configurarArrastre(fin6, pata6);
        configurarArrastre(fin7, pata7);
        configurarArrastre(fin8, pata8);
        configurarArrastre(fin9, pata9);
        configurarArrastre(fin10, pata10);
        configurarArrastre(fin11, pata11);
        configurarArrastre(fin12, pata12);
        configurarArrastre(fin13, pata13);
        configurarArrastre(fin14, pata14);

        // Configurar el arrastre del nodo completo (chip)
        configurarArrastreNodo();

        // Añadir todos los elementos gráficos al grupo nodo
        nodo.getChildren().addAll(
                chip,
                pata1, pata2, pata3, pata4, pata5, pata6, pata7, pata8, pata9, pata10, pata11, pata12, pata13, pata14,
                fin1, fin2, fin3, fin4, fin5, fin6, fin7, fin8, fin9, fin10, fin11, fin12, fin13, fin14,
                textoChip
        );
        this.getChildren().add(nodo);
        this.setPickOnBounds(false);
    }

    // Método para crear una línea entre dos puntos
    protected Line crearLinea(double startX, double startY, double endX, double endY) {
        Line linea = new Line(startX, startY, endX, endY);
        linea.setStroke(Color.BLACK);
        return linea;
    }

    // Método para crear un punto estirable en la punta de una pata
    protected Cuadrados Esquina_Estirable(Line pata, Color color) {
        Cuadrados point = new Cuadrados(11, 2);
        point.setX(pata.getEndX() - 5);
        point.setY(pata.getEndY() - 5);
        point.setFill(color);
        return point;
    }

    // Método que inicia el arrastre al presionar el mouse
    protected void empezarArrastre(MouseEvent e) {
        line_en_arrastre = true;
        mouseX = e.getSceneX();
        mouseY = e.getSceneY();
    }

    // Método que maneja el arrastre de una pata
    protected void arrastrePata(MouseEvent event, Line pata, Cuadrados estirable) {
        double offsetX = event.getSceneX() - mouseX;
        double offsetY = event.getSceneY() - mouseY;

        pata.setEndX(pata.getEndX() + offsetX);
        pata.setEndY(pata.getEndY() + offsetY);

        actualizarEstirable(estirable, pata);

        mouseX = event.getSceneX();
        mouseY = event.getSceneY();

        line_en_arrastre = false;
    }

    // Método para actualizar la posición del punto estirable basado en la pata
    protected void actualizarEstirable(Cuadrados esquina, Line pata) {
        esquina.setX(pata.getEndX() - 5);
        esquina.setY(pata.getEndY() - 5);
    }

    // Método para actualizar el estado de conexión de una pata
    protected void updateFinConnection(Cuadrados estirable) {
        double sceneX = estirable.localToScene(estirable.getBoundsInLocal()).getMinX() + estirable.getWidth() / 2;
        double sceneY = estirable.localToScene(estirable.getBoundsInLocal()).getMinY() + estirable.getHeight() / 2;

        int signoCelda = 0;
        boolean connected = false;

        if (protoboard != null) {
            Node celdaEncontrada = null;
            GridPane[] gridPanes = {
                    (GridPane) protoboard.getCelda1().getChildren().get(0),
                    (GridPane) protoboard.getCelda2().getChildren().get(0),
            };

            for (GridPane gridPane : gridPanes) {
                celdaEncontrada = verificarSiEstaEnCelda(sceneX, sceneY, gridPane);
                if (celdaEncontrada != null) {
                    Integer colIndex = GridPane.getColumnIndex(celdaEncontrada) - 1;
                    Integer rowIndex = GridPane.getRowIndex(celdaEncontrada);

                    // Obtener el signo y voltaje de la celda
                    if (gridPane == gridPanes[0]) {
                        signoCelda = protoboard.getCelda1().getSigno(rowIndex, colIndex);
                        estirable.setVoltaje(protoboard.getCelda1().getVoltaje(rowIndex, colIndex));
                    } else if (gridPane == gridPanes[1]) {
                        signoCelda = protoboard.getCelda2().getSigno(rowIndex, colIndex);
                        estirable.setVoltaje(protoboard.getCelda2().getVoltaje(rowIndex, colIndex));
                    }

                    connected = true;
                    estirable.setCeldaConectada(celdaEncontrada);
                    break;
                }
            }

            if (!connected) {
                signoCelda = 0;
                estirable.setCeldaConectada(null);
                estirable.setVoltaje(0);
            }

            estirable.setSigno(signoCelda);
        }
    }

    // Método para verificar si un punto está sobre una celda específica
    protected Node verificarSiEstaEnCelda(double x, double y, GridPane gridPane) {
        for (Node child : gridPane.getChildren()) {
            Bounds boundsInScene = child.localToScene(child.getBoundsInLocal());
            if (boundsInScene.contains(x, y)) {
                return child;
            }
        }
        return null;
    }

    // Método para configurar el arrastre de una pata y su punto estirable
    protected void configurarArrastre(Cuadrados estirable, Line pata) {
        estirable.setOnMousePressed(e -> {
            empezarArrastre(e);
            estirable.toFront();
        });
        estirable.setOnMouseDragged(e -> arrastrePata(e, pata, estirable));

        estirable.setOnMouseReleased(event -> {
            updateFinConnection(estirable);
        });
    }

    // Método para configurar el arrastre del chip completo
    protected void configurarArrastreNodo() {
        nodo.setOnMousePressed(e -> {
            if (!line_en_arrastre) {
                nodo.toFront();
                mouseX = e.getSceneX();
                mouseY = e.getSceneY();
            }
        });

        nodo.setOnMouseDragged(e -> {
            if (!line_en_arrastre) {
                double dX = e.getSceneX() - mouseX;
                double dY = e.getSceneY() - mouseY;

                nodo.setLayoutX(nodo.getLayoutX() + dX);
                nodo.setLayoutY(nodo.getLayoutY() + dY);

                mouseX = e.getSceneX();
                mouseY = e.getSceneY();

                actualizarPosiciones();
            }
        });

        // Actualizar y verificar las conexiones al soltar el nodo
        nodo.setOnMouseReleased(e -> {
            checkFinConnections();
        });
    }

    // Método para actualizar las posiciones de los puntos estirables al mover el chip
    protected void actualizarPosiciones() {
        actualizarEstirable(fin1, pata1);
        actualizarEstirable(fin2, pata2);
        actualizarEstirable(fin3, pata3);
        actualizarEstirable(fin4, pata4);
        actualizarEstirable(fin5, pata5);
        actualizarEstirable(fin6, pata6);
        actualizarEstirable(fin7, pata7);
        actualizarEstirable(fin8, pata8);
        actualizarEstirable(fin9, pata9);
        actualizarEstirable(fin10, pata10);
        actualizarEstirable(fin11, pata11);
        actualizarEstirable(fin12, pata12);
        actualizarEstirable(fin13, pata13);
        actualizarEstirable(fin14, pata14);
    }

    // Método para verificar y actualizar el estado de conexión de todas las patas
    public void checkFinConnections() {
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
        calcularSalidas();
    }

    protected abstract void calcularSalidas();

    protected void calcularSalida(Cuadrados entrada1, Cuadrados entrada2, Cuadrados salida) {
    }
    // Getters y setters para el protoboard
    public Prototipo_Protoboard getProtoboard() {
        return protoboard;
    }

    public void setProtoboard(Prototipo_Protoboard protoboard) {
        this.protoboard = protoboard;
    }

}