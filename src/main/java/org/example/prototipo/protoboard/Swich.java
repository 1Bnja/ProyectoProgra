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

    private Cuadrados fin1, fin2, fin3, fin4;
    private Line pata1, pata2, pata3, pata4;

    // Posición del mouse
    private double mouseX;
    private double mouseY;
    private boolean line_en_arrastre = false;

    double origenX = Main.origenX;
    double origenY = Main.origenY;

    Prototipo_Protoboard protoboard;
    LED led;
    int celda;
    int colum_1, colum_2;

    boolean encendido;

    int filaEntrada, columnaEntrada;
    int filaSalida, columnaSalida;

    public Swich() {
        double achicar = 0.7;
        double achicar2 = 0.5;
        int tamanioCuadradoInterno = (int) (37 * achicar2); // Tamaño del cuadrado interno
        this.encendido = false;

        // Cuadrado exterior usando líneas
        Line lineaSuperiorCE = crearLinea(origenX - 500 * achicar, origenY - 100 * achicar, origenX - 560 * achicar, origenY - 100 * achicar);
        Line lineaInferiorCE = crearLinea(origenX - 500 * achicar, origenY - 40 * achicar, origenX - 560 * achicar, origenY - 40 * achicar);
        Line lineaIzquierdaCE = crearLinea(origenX - 500 * achicar, origenY - 100 * achicar, origenX - 500 * achicar, origenY - 40 * achicar);
        Line lineaDerechaCE = crearLinea(origenX - 560 * achicar, origenY - 100 * achicar, origenX - 560 * achicar, origenY - 40 * achicar);

        // Cuadrado Interno
        Cuadrados cuadradoInterno = new Cuadrados(tamanioCuadradoInterno, 0);
        cuadradoInterno.setTranslateX(origenX - 543 * achicar);
        cuadradoInterno.setTranslateY(origenY - 84 * achicar);
        cuadradoInterno.setFill(Color.BLACK); // Configurar el color del cuadrado

        cuadradoInterno.setOnMouseClicked(event -> {
            if (encendido) {
                // Apagar el switch
                if (celda == 1) {
                    protoboard.getCelda1().alternarColumna(columnaSalida, 3); // Cortar energía
                } else if (celda == 2) {
                    protoboard.getCelda2().alternarColumna(columnaSalida, 3);
                }
                cuadradoInterno.setFill(Color.BLACK); // Cambiar color a apagado
            } else {
                // Encender el switch
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

            // Notificar a los LEDs conectados para actualizar su estado
            Prototipo_Protoboard.notificarComponentesConectados();
        });


        // Patas
        pata1 = crearLinea(origenX - 505 * achicar, origenY - 100 * achicar, origenX - 505 * achicar, origenY - 107.5 * achicar);
        pata2 = crearLinea(origenX - 555 * achicar, origenY - 100 * achicar, origenX - 555 * achicar, origenY - 107.5 * achicar);
        pata3 = crearLinea(origenX - 505 * achicar, origenY - 40 * achicar, origenX - 505 * achicar, origenY - 32.5 * achicar);
        pata4 = crearLinea(origenX - 555 * achicar, origenY - 40 * achicar, origenX - 555 * achicar, origenY - 32.5 * achicar);

        // Fondo del cuadrado exterior
        Polygon fondoCuadradoE = crearFondo(origenX - 500 * achicar, origenY - 100 * achicar, origenX - 560 * achicar, origenY - 40 * achicar, Color.LIGHTGRAY);
        Polygon fondoCuadradoI = crearFondo(origenX - 510 * achicar, origenY - 90 * achicar, origenX - 550 * achicar, origenY - 50 * achicar, Color.BLACK);

        fin1 = Esquina_Estirable(pata1);
        fin2 = Esquina_Estirable(pata2);
        fin3 = Esquina_Estirable(pata3);
        fin4 = Esquina_Estirable(pata4);

        configurarArrastre(fin1, pata1, 2);
        configurarArrastre(fin2, pata2, 1);
        configurarArrastre(fin3, pata3, 2);
        configurarArrastre(fin4, pata4, 1);

        // Mover el nodo completo
        configurarArrastreNodo();

        // Agregar los elementos al grupo
        nodo.getChildren().addAll(
                fondoCuadradoE, cuadradoInterno, lineaSuperiorCE, lineaInferiorCE,
                lineaIzquierdaCE, lineaDerechaCE, pata1, pata2, pata3, pata4, fin1, fin2, fin3, fin4
        );

        this.getChildren().add(nodo);

        this.setPickOnBounds(false);
    }

    private Line crearLinea(double startX, double startY, double endX, double endY) {
        Line linea = new Line(startX, startY, endX, endY);
        linea.setStroke(Color.BLACK);
        return linea;
    }

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

    private Cuadrados Esquina_Estirable(Line pata) {
        Cuadrados point = new Cuadrados(11, 2);
        point.setX(pata.getEndX() - 5);
        point.setY(pata.getEndY() - 5);
        point.setFill(Color.RED);
        return point;
    }

    private void configurarArrastre(Cuadrados estirable, Line pata, int lado) {
        estirable.setOnMousePressed(e -> {
            empezarArrastre(e, pata);
            nodo.toFront();
        });
        estirable.setOnMouseDragged(e -> Arrastre(e, pata, estirable));

        estirable.setOnMouseReleased(event -> {
            double mouseX = event.getSceneX();
            double mouseY = event.getSceneY();
            if (protoboard != null) {
                Node celdaEncontrada = null;
                int col = 0;
                int row = 0;
                int signoCelda = 0;

                // Verificar si está sobre celda1
                celdaEncontrada = verificarSiEstaEnCelda(mouseX, mouseY, (GridPane) protoboard.getCelda1().getChildren().get(0));
                if (celdaEncontrada != null) {
                    col = GridPane.getColumnIndex(celdaEncontrada) - 1;
                    row = GridPane.getRowIndex(celdaEncontrada) - 1;
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

                estirable.setSigno(signoCelda);

                if (lado == 1) {
                    // Pata de entrada
                    filaEntrada = row;
                    columnaEntrada = col;
                } else if (lado == 2) {
                    // Pata de salida
                    filaSalida = row;
                    columnaSalida = col;
                }
            }
        });
    }

    private Node verificarSiEstaEnCelda(double mouseX, double mouseY, GridPane gridPane) {
        for (Node child : gridPane.getChildren()) {
            // Obtener los límites de la celda en coordenadas de la escena
            Bounds boundsInScene = child.localToScene(child.getBoundsInLocal());

            // Verificar si el mouse está dentro de los límites de la celda
            if (boundsInScene.contains(mouseX, mouseY)) {
                Integer row = GridPane.getRowIndex(child);
                Integer col = GridPane.getColumnIndex(child);
                System.out.println("El nodo está sobre la celda en fila: " + row + ", columna: " + col);
                return child;
            }
        }
        return null;
    }

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

                nodo.setLayoutX(nodo.getLayoutX() + dX);
                nodo.setLayoutY(nodo.getLayoutY() + dY);

                mouseX = e.getSceneX();
                mouseY = e.getSceneY();

                actualizarPosiciones();
            }
        });
    }

    private void empezarArrastre(MouseEvent event, Line pata) {
        line_en_arrastre = true;
        mouseX = event.getSceneX();
        mouseY = event.getSceneY();
    }

    private void Arrastre(MouseEvent event, Line line, Cuadrados estirable) {
        double offsetX = event.getSceneX() - mouseX;
        double offsetY = event.getSceneY() - mouseY;

        line.setEndX(line.getEndX() + offsetX);
        line.setEndY(line.getEndY() + offsetY);

        actualizarEstirable(estirable, line);

        mouseX = event.getSceneX();
        mouseY = event.getSceneY();

        line_en_arrastre = false;
    }

    private void actualizarEstirable(Cuadrados estirable, Line pata) {
        estirable.setX(pata.getEndX() - 5);
        estirable.setY(pata.getEndY() - 5);
    }

    private void actualizarPosiciones() {
        actualizarEstirable(fin1, pata1);
        actualizarEstirable(fin2, pata2);
        actualizarEstirable(fin3, pata3);
        actualizarEstirable(fin4, pata4);
    }

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
