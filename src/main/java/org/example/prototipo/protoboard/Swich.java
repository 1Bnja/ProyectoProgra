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

public class Swich extends Pane {

    private Group nodo = new Group();

    // Definición de las esquinas estirables y las patas del interruptor
    private Cuadrados fin1, fin2, fin3, fin4;
    private Line pata1, pata2, pata3, pata4;

    // Posición del mouse para manejar el arrastre
    private double mouseX;
    private double mouseY;
    private boolean line_en_arrastre = false;

    // Coordenadas de origen
    double origenX = Main.origenX;
    double origenY = Main.origenY;

    // Referencias al protoboard y al LED
    Prototipo_Protoboard protoboard;
    LED led;
    int celda;
    int colum_1, colum_2;

    // Estado del interruptor
    boolean encendido;

    // Variables para almacenar la posición de entrada y salida
    int filaEntrada, columnaEntrada;
    int filaSalida, columnaSalida;

    // Constructor de la clase Swich
    public Swich() {
        double achicar = 0.7;
        double achicar2 = 0.5;
        int tamanioCuadradoInterno = (int) (37 * achicar2);
        this.encendido = false;

        // Crear el cuadrado exterior usando líneas
        Line lineaSuperiorCE = crearLinea(
                origenX - 500 * achicar, origenY - 100 * achicar,
                origenX - 560 * achicar, origenY - 100 * achicar);
        Line lineaInferiorCE = crearLinea(
                origenX - 500 * achicar, origenY - 40 * achicar,
                origenX - 560 * achicar, origenY - 40 * achicar);
        Line lineaIzquierdaCE = crearLinea(
                origenX - 500 * achicar, origenY - 100 * achicar,
                origenX - 500 * achicar, origenY - 40 * achicar);
        Line lineaDerechaCE = crearLinea(
                origenX - 560 * achicar, origenY - 100 * achicar,
                origenX - 560 * achicar, origenY - 40 * achicar);

        // Crear el cuadrado interno que funciona como botón
        Cuadrados cuadradoInterno = new Cuadrados(tamanioCuadradoInterno, 0);
        cuadradoInterno.setTranslateX(origenX - 543 * achicar);
        cuadradoInterno.setTranslateY(origenY - 84 * achicar);
        cuadradoInterno.setFill(Color.BLACK);

        // Evento al hacer clic en el cuadrado interno (botón)
        cuadradoInterno.setOnMouseClicked(event -> {
            if (encendido) {
                // Si está encendido, apagar el interruptor
                if (celda == 1) {
                    protoboard.getCelda1().alternarColumna(columnaSalida, 3); // Cortar energía
                } else if (celda == 2) {
                    protoboard.getCelda2().alternarColumna(columnaSalida, 3);
                }
                cuadradoInterno.setFill(Color.BLACK);
            } else {
                // Si está apagado, encender el interruptor
                int signoEntrada;
                if (celda == 1) {
                    signoEntrada = protoboard.getCelda1().getSigno(filaEntrada, columnaEntrada);
                    protoboard.getCelda1().alternarColumna(columnaSalida, signoEntrada); // Transferir energía
                } else if (celda == 2) {
                    signoEntrada = protoboard.getCelda2().getSigno(filaEntrada, columnaEntrada);
                    protoboard.getCelda2().alternarColumna(columnaSalida, signoEntrada);
                }
                cuadradoInterno.setFill(Color.YELLOW); // Cambiar color a encendido
            }
            encendido = !encendido; // Cambiar estado

            // Notificar a los componentes conectados para actualizar su estado
            Prototipo_Protoboard.notificarComponentesConectados();
        });

        // Crear las patas del interruptor
        pata1 = crearLinea(
                origenX - 495 * achicar, origenY - 100 * achicar,
                origenX - 496 * achicar, origenY - 110 * achicar);
        pata2 = crearLinea(
                origenX - 565 * achicar, origenY - 100 * achicar,
                origenX - 564 * achicar, origenY - 110 * achicar);
        pata3 = crearLinea(
                origenX - 495 * achicar, origenY - 40 * achicar,
                origenX - 496 * achicar, origenY - 32.5 * achicar);
        pata4 = crearLinea(
                origenX - 565 * achicar, origenY - 40 * achicar,
                origenX - 564 * achicar, origenY - 32.5 * achicar);

        // Crear el fondo del cuadrado exterior
        Polygon fondoCuadradoE = crearFondo(
                origenX - 500 * achicar, origenY - 100 * achicar,
                origenX - 560 * achicar, origenY - 40 * achicar, Color.LIGHTGRAY);

        // Crear las esquinas estirables en las patas
        fin1 = Esquina_Estirable(pata1);
        fin2 = Esquina_Estirable(pata2);
        fin3 = Esquina_Estirable(pata3);
        fin4 = Esquina_Estirable(pata4);

        // Configurar el arrastre para cada esquina estirable
        configurarArrastre(fin1, pata1, 2);
        configurarArrastre(fin2, pata2, 1);
        configurarArrastre(fin3, pata3, 2);
        configurarArrastre(fin4, pata4, 1);

        // Configurar el arrastre para el nodo completo (interruptor)
        configurarArrastreNodo();

        // Añadir todos los elementos al grupo nodo
        nodo.getChildren().addAll(
                fondoCuadradoE, cuadradoInterno, lineaSuperiorCE, lineaInferiorCE,
                lineaIzquierdaCE, lineaDerechaCE, pata1, pata2, pata3, pata4, fin1, fin2, fin3, fin4
        );

        // Añadir el nodo al pane
        this.getChildren().add(nodo);

        // Desactivar la detección de eventos en los límites del pane
        this.setPickOnBounds(false);
    }

    // Método para crear una línea entre dos puntos
    private Line crearLinea(double startX, double startY, double endX, double endY) {
        Line linea = new Line(startX, startY, endX, endY);
        linea.setStroke(Color.BLACK);
        return linea;
    }

    // Método para crear un fondo rectangular usando polígonos
    private Polygon crearFondo(double startX, double startY, double endX, double endY, Color color) {
        Polygon fondo = new Polygon();
        fondo.getPoints().addAll(
                startX, startY,
                endX, startY,
                endX, endY,
                startX, endY
        );
        fondo.setFill(color);
        return fondo;
    }

    // Método para crear una esquina estirable en la punta de una pata
    private Cuadrados Esquina_Estirable(Line pata) {
        Cuadrados point = new Cuadrados(11, 2);
        point.setX(pata.getEndX() - 5);
        point.setY(pata.getEndY() - 5);
        point.setFill(Color.RED);
        return point;
    }

    // Método para configurar el arrastre de las esquinas estirables
    private void configurarArrastre(Cuadrados estirable, Line pata, int lado) {
        estirable.setOnMousePressed(e -> {
            empezarArrastre(e, pata);
            nodo.toFront();
        });
        estirable.setOnMouseDragged(e -> Arrastre(e, pata, estirable));

        estirable.setOnMouseReleased(event -> {
            verificarConexionPata(estirable, pata, lado);
        });
    }

    // Método para verificar si una pata está conectada a una celda del protoboard
    private void verificarConexionPata(Cuadrados estirable, Line pata, int lado) {
        double mouseX = estirable.localToScene(estirable.getBoundsInLocal()).getCenterX();
        double mouseY = estirable.localToScene(estirable.getBoundsInLocal()).getCenterY();

        if (protoboard != null) {
            Node celdaEncontrada = null;
            int col = 0;
            int row = 0;
            int signoCelda = 0;

            // Verificar si está sobre celda1
            celdaEncontrada = verificarSiEstaEnCelda(mouseX, mouseY, (GridPane) protoboard.getCelda1().getChildren().get(0));
            if (celdaEncontrada != null) {
                col = GridPane.getColumnIndex(celdaEncontrada) - 1;
                row = GridPane.getRowIndex(celdaEncontrada);
                signoCelda = protoboard.getCelda1().getSigno(row, col);
                celda = 1;
            } else {
                // Verificar si está sobre celda2
                celdaEncontrada = verificarSiEstaEnCelda(mouseX, mouseY, (GridPane) protoboard.getCelda2().getChildren().get(0));
                if (celdaEncontrada != null) {
                    col = GridPane.getColumnIndex(celdaEncontrada) - 1;
                    row = GridPane.getRowIndex(celdaEncontrada) - 1;
                    signoCelda = protoboard.getCelda2().getSigno(row, col);
                    celda = 2;
                } else {
                    // No está conectado a ninguna celda
                    estirable.setSigno(0);
                    return;
                }
            }

            // Establecer el signo de la pata según la celda conectada
            estirable.setSigno(signoCelda);

            if (lado == 1) {
                // Lado de entrada
                filaEntrada = row;
                columnaEntrada = col;
            } else if (lado == 2) {
                // Lado de salida
                filaSalida = row;
                columnaSalida = col;
            }
        }
    }

    // Método para verificar si un punto está sobre una celda específica
    private Node verificarSiEstaEnCelda(double mouseX, double mouseY, GridPane gridPane) {
        for (Node child : gridPane.getChildren()) {
            Bounds boundsInScene = child.localToScene(child.getBoundsInLocal());

            if (boundsInScene.contains(mouseX, mouseY)) {
                Integer row = GridPane.getRowIndex(child);
                Integer col = GridPane.getColumnIndex(child);
                System.out.println("El nodo está sobre la celda en fila: " + row + ", columna: " + col);
                return child;
            }
        }
        return null;
    }

    // Método para configurar el arrastre del nodo completo (interruptor)
    private void configurarArrastreNodo() {
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

                // Actualizar la posición del nodo
                nodo.setLayoutX(nodo.getLayoutX() + dX);
                nodo.setLayoutY(nodo.getLayoutY() + dY);

                mouseX = e.getSceneX();
                mouseY = e.getSceneY();

                actualizarPosiciones();
            }
        });

        nodo.setOnMouseReleased(event -> {
            // Actualizar las conexiones de las patas al soltar el nodo
            actualizarConexionesPatas();
        });
    }

    // Método que inicia el arrastre al presionar el mouse
    private void empezarArrastre(MouseEvent event, Line pata) {
        line_en_arrastre = true;
        mouseX = event.getSceneX();
        mouseY = event.getSceneY();
    }

    // Método que maneja el arrastre de una pata
    private void Arrastre(MouseEvent event, Line line, Cuadrados estirable) {
        double offsetX = event.getSceneX() - mouseX;
        double offsetY = event.getSceneY() - mouseY;

        // Actualizar la posición de la línea (pata)
        line.setEndX(line.getEndX() + offsetX);
        line.setEndY(line.getEndY() + offsetY);

        actualizarEstirable(estirable, line);

        mouseX = event.getSceneX();
        mouseY = event.getSceneY();

        line_en_arrastre = false;
    }

    // Método para actualizar la posición de la esquina estirable según la pata
    private void actualizarEstirable(Cuadrados estirable, Line pata) {
        estirable.setX(pata.getEndX() - 5);
        estirable.setY(pata.getEndY() - 5);
    }

    // Método para actualizar las posiciones de las esquinas estirables al mover el nodo
    private void actualizarPosiciones() {
        actualizarEstirable(fin1, pata1);
        actualizarEstirable(fin2, pata2);
        actualizarEstirable(fin3, pata3);
        actualizarEstirable(fin4, pata4);
    }

    // Método para actualizar las conexiones de todas las patas
    private void actualizarConexionesPatas() {
        verificarConexionPata(fin1, pata1, 2);
        verificarConexionPata(fin2, pata2, 1);
        verificarConexionPata(fin3, pata3, 2);
        verificarConexionPata(fin4, pata4, 1);
    }

    // Getters y setters para el protoboard y el LED
    public Prototipo_Protoboard getProtoboard() {
        return protoboard;
    }

    public void setProtoboard(Prototipo_Protoboard protoboard) {
        this.protoboard = protoboard;
    }

    public LED getLed() {
        return led;
    }

    public void setLed(LED led) {
        this.led = led;
    }

    // Getters para las esquinas estirables
    public Cuadrados getFin1() {
        return fin1;
    }

    public Cuadrados getFin2() {
        return fin2;
    }

    public Cuadrados getFin3() {
        return fin3;
    }

    public Cuadrados getFin4() {
        return fin4;
    }
}
