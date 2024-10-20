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

public class Chip extends Pane {
    private Group nodo = new Group();

    // Definición de los puntos finales (patas) y líneas del chip
    private Cuadrados fin1, fin2, fin3, fin4, fin5, fin6, fin7, fin8;
    private Line pata1, pata2, pata3, pata4, pata5, pata6, pata7, pata8;

    // Posición del mouse para manejar el arrastre
    private double mouseX;
    private double mouseY;
    private boolean line_en_arrastre = false;

    // Coordenadas de origen
    double origenX = Main.origenX;
    double origenY = Main.origenY;

    Prototipo_Protoboard protoboard;

    // Variables para controlar si las patas están conectadas
    private boolean fin1Conectada = false;
    private boolean fin2Conectada = false;
    private boolean fin3Conectada = false;
    private boolean fin4Conectada = false;
    private boolean fin5Conectada = false;
    private boolean fin6Conectada = false;
    private boolean fin7Conectada = false;
    private boolean fin8Conectada = false;

    // Signos de las conexiones de las patas
    private int signoFin1 = 0;
    private int signoFin2 = 0;
    private int signoFin3 = 0;
    private int signoFin4 = 0;
    private int signoFin5 = 0;
    private int signoFin6 = 0;
    private int signoFin7 = 0;
    private int signoFin8 = 0;

    // Constructor de la clase Chip
    public Chip() {

        // Crear el cuadrado exterior usando líneas
        Line lineaSuperiorCE = crearLinea(origenX - 485, origenY - 105, origenX - 570, origenY - 105);
        Line lineaInferiorCE = crearLinea(origenX - 485, origenY - 35, origenX - 570, origenY - 35);
        Line lineaIzquierdaCE = crearLinea(origenX - 485, origenY - 105, origenX - 485, origenY - 35);
        Line lineaDerechaCE = crearLinea(origenX - 570, origenY - 105, origenX - 570, origenY - 35);

        // Crear las patas del chip
        pata1 = crearLinea(origenX - 565, origenY - 105, origenX - 565, origenY - 125);
        pata2 = crearLinea(origenX - 541, origenY - 105, origenX - 541, origenY - 125);
        pata3 = crearLinea(origenX - 517, origenY - 105, origenX - 517, origenY - 125);
        pata4 = crearLinea(origenX - 493, origenY - 105, origenX - 493, origenY - 125);

        pata5 = crearLinea(origenX - 565, origenY - 35, origenX - 565, origenY - 15);
        pata6 = crearLinea(origenX - 541, origenY - 35, origenX - 541, origenY - 15);
        pata7 = crearLinea(origenX - 517, origenY - 35, origenX - 517, origenY - 15);
        pata8 = crearLinea(origenX - 493, origenY - 35, origenX - 493, origenY - 15);

        // Crear el fondo del cuadrado exterior
        Polygon fondoCuadradoE = crearFondo(origenX - 485, origenY - 105, origenX - 570, origenY - 35, Color.BLACK);

        // **Agregar el texto "CHIP" en el centro**
        Text textoChip = new Text("CHIP");
        textoChip.setFill(Color.WHITE);
        textoChip.setFont(Font.font("Arial", 18));
        // Posicionar el texto en el centro del chip
        double centerX = (origenX - 485 + origenX - 570) / 2 - textoChip.getLayoutBounds().getWidth() / 2;
        double centerY = (origenY - 105 + origenY - 35) / 2 + textoChip.getLayoutBounds().getHeight() / 4;
        textoChip.setX(centerX);
        textoChip.setY(centerY);

        // Crear los puntos estirables en las patas
        fin1 = Esquina_Estirable(pata1);
        fin2 = Esquina_Estirable(pata2);
        fin3 = Esquina_Estirable(pata3);
        fin4 = Esquina_Estirable(pata4);
        fin5 = Esquina_Estirable(pata5);
        fin6 = Esquina_Estirable(pata6);
        fin7 = Esquina_Estirable(pata7);
        fin8 = Esquina_Estirable(pata8);

        // Configurar el arrastre para cada pata y su punto estirable
        configurarArrastre(fin1, pata1);
        configurarArrastre(fin2, pata2);
        configurarArrastre(fin3, pata3);
        configurarArrastre(fin4, pata4);
        configurarArrastre(fin5, pata5);
        configurarArrastre(fin6, pata6);
        configurarArrastre(fin7, pata7);
        configurarArrastre(fin8, pata8);

        // Configurar el arrastre del nodo completo (chip)
        configurarArrastreNodo();

        // Añadir todos los elementos gráficos al grupo nodo
        nodo.getChildren().addAll(
                fondoCuadradoE, lineaSuperiorCE, lineaInferiorCE, lineaIzquierdaCE, lineaDerechaCE,
                pata1, pata2, pata3, pata4, pata5, pata6, pata7, pata8,
                fin1, fin2, fin3, fin4, fin5, fin6, fin7, fin8,
                textoChip // Añadir el texto al grupo
        );
        this.getChildren().add(nodo);
        this.setPickOnBounds(false);
    }

    // Método para crear una línea entre dos puntos
    private Line crearLinea(double startX, double startY, double endX, double endY) {
        Line linea = new Line(startX, startY, endX, endY);
        linea.setStroke(Color.BLACK);
        return linea;
    }

    // Método para crear el fondo del cuadrado exterior
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

    // Método para crear un punto estirable en la punta de una pata
    private Cuadrados Esquina_Estirable(Line pata) {
        Cuadrados point = new Cuadrados(11, 2);
        point.setX(pata.getEndX() - 5);
        point.setY(pata.getEndY() - 5);
        point.setFill(Color.ORANGE);
        return point;
    }

    // Método que inicia el arrastre al presionar el mouse
    private void empezarArrastre(MouseEvent e) {
        line_en_arrastre = true;
        mouseX = e.getSceneX();
        mouseY = e.getSceneY();
    }

    // Método que maneja el arrastre de una pata
    private void arrastrePata(MouseEvent event, Line pata, Cuadrados estirable) {
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
    private void actualizarEstirable(Cuadrados esquina, Line pata) {
        esquina.setX(pata.getEndX() - 5);
        esquina.setY(pata.getEndY() - 5);
    }

    // Método para actualizar el estado de conexión de una pata
    private void updateFinConnection(Cuadrados estirable) {
        double sceneX = estirable.localToScene(estirable.getBoundsInLocal()).getMinX() + estirable.getWidth() / 2;
        double sceneY = estirable.localToScene(estirable.getBoundsInLocal()).getMinY() + estirable.getHeight() / 2;

        int signoCelda = 0;
        boolean connected = false;

        if (protoboard != null) {
            Node celdaEncontrada = null;
            GridPane[] gridPanes = {
                    (GridPane) protoboard.getCelda1().getChildren().get(0),
                    (GridPane) protoboard.getCelda2().getChildren().get(0),
                    (GridPane) protoboard.getBus1().getChildren().get(0),
                    (GridPane) protoboard.getBus2().getChildren().get(0)
            };

            // Verificar si el punto está sobre alguna celda del protoboard
            for (GridPane gridPane : gridPanes) {
                celdaEncontrada = verificarSiEstaEnCelda(sceneX, sceneY, gridPane);
                if (celdaEncontrada != null) {
                    Integer colIndex = GridPane.getColumnIndex(celdaEncontrada);
                    Integer rowIndex = GridPane.getRowIndex(celdaEncontrada);

                    if (colIndex != null && rowIndex != null) {
                        int col = colIndex - 1;
                        int row = rowIndex;

                        System.out.println("Chip conectado en fila: " + row + ", columna: " + col);

                        // Obtener el signo de la celda donde se conectó
                        if (gridPane == gridPanes[0]) {
                            signoCelda = protoboard.getCelda1().getSigno(row, col);
                        } else if (gridPane == gridPanes[1]) {
                            signoCelda = protoboard.getCelda2().getSigno(row, col);
                        } else if (gridPane == gridPanes[2]) {
                            signoCelda = protoboard.getBus1().getSigno(row, col);
                        } else if (gridPane == gridPanes[3]) {
                            signoCelda = protoboard.getBus2().getSigno(row, col);
                        }
                        connected = true;
                    } else {
                        System.out.println("Índices de columna o fila son nulos");
                    }
                    break;
                }
            }

            if (!connected) {
                signoCelda = 0;
            }

            // Actualizar el estado de conexión y signo de la pata correspondiente
            if (estirable == fin1) {
                fin1Conectada = connected;
                signoFin1 = signoCelda;
            } else if (estirable == fin2) {
                fin2Conectada = connected;
                signoFin2 = signoCelda;
            } else if (estirable == fin3) {
                fin3Conectada = connected;
                signoFin3 = signoCelda;
            } else if (estirable == fin4) {
                fin4Conectada = connected;
                signoFin4 = signoCelda;
            } else if (estirable == fin5) {
                fin5Conectada = connected;
                signoFin5 = signoCelda;
            } else if (estirable == fin6) {
                fin6Conectada = connected;
                signoFin6 = signoCelda;
            } else if (estirable == fin7) {
                fin7Conectada = connected;
                signoFin7 = signoCelda;
            } else if (estirable == fin8) {
                fin8Conectada = connected;
                signoFin8 = signoCelda;
            }
        }
    }

    // Método para verificar si un punto está sobre una celda específica
    private Node verificarSiEstaEnCelda(double x, double y, GridPane gridPane) {
        for (Node child : gridPane.getChildren()) {
            Bounds boundsInScene = child.localToScene(child.getBoundsInLocal());
            if (boundsInScene.contains(x, y)) {
                return child;
            }
        }
        return null;
    }

    // Método para configurar el arrastre de una pata y su punto estirable
    private void configurarArrastre(Cuadrados estirable, Line pata) {
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

        // Actualizar y verificar las conexiones al soltar el nodo
        nodo.setOnMouseReleased(e -> {
            checkFinConnections();
        });
    }

    // Método para actualizar las posiciones de los puntos estirables al mover el chip
    private void actualizarPosiciones() {
        actualizarEstirable(fin1, pata1);
        actualizarEstirable(fin2, pata2);
        actualizarEstirable(fin3, pata3);
        actualizarEstirable(fin4, pata4);
        actualizarEstirable(fin5, pata5);
        actualizarEstirable(fin6, pata6);
        actualizarEstirable(fin7, pata7);
        actualizarEstirable(fin8, pata8);
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
    }

    // Getters y setters para el protoboard
    public Prototipo_Protoboard getProtoboard() {
        return protoboard;
    }

    public void setProtoboard(Prototipo_Protoboard protoboard) {
        this.protoboard = protoboard;
    }

}
