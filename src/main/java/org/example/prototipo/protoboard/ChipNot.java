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

public class ChipNot extends Pane{
    private Group nodo = new Group();

    // Definición de los puntos finales (patas) y líneas del chip
    private Cuadrados fin1, fin2, fin3, fin4, fin5, fin6, fin7, fin8, fin9, fin10, fin11, fin12, fin13, fin14;
    private Line pata1, pata2, pata3, pata4, pata5, pata6, pata7, pata8, pata9, pata10, pata11, pata12, pata13, pata14;

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
    private boolean fin9Conectada = false;
    private boolean fin10Conectada = false;
    private boolean fin11Conectada = false;
    private boolean fin12Conectada = false;
    private boolean fin13Conectada = false;
    private boolean fin14Conectada = false;

    // Signos de las conexiones de las patas
    private int signoFin1 = 0;
    private int signoFin2 = 0;
    private int signoFin3 = 0;
    private int signoFin4 = 0;
    private int signoFin5 = 0;
    private int signoFin6 = 0;
    private int signoFin7 = 0;
    private int signoFin8 = 0;
    private int signoFin9 = 0;
    private int signoFin10 = 0;
    private int signoFin11 = 0;
    private int signoFin12 = 0;
    private int signoFin13 = 0;
    private int signoFin14 = 0;

    // Entradas y salidas del chip
    private Cuadrados inA1, inB1, out1;
    private Cuadrados inA2, inB2, out2;
    private Cuadrados inA3, inB3, out3;
    private Cuadrados inA4, inB4, out4;

    // Constructor de la clase Chip
    public ChipNot() {

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
        Text textoChip = new Text("CHIP AND");
        textoChip.setFill(Color.WHITE);
        textoChip.setFont(Font.font("Arial", 15));
        // Posicionar el texto en el centro del chip

        double centerX = chip.getX() + chip.getWidth() / 2 - textoChip.getLayoutBounds().getWidth() / 2;
        double centerY = chip.getY() + chip.getHeight() / 2 + textoChip.getLayoutBounds().getHeight() / 4;

        textoChip.setX(centerX);
        textoChip.setY(centerY);

        fin1 = Esquina_Estirable(pata1);
        fin2 = Esquina_Estirable(pata2);
        fin3 = Esquina_Estirable(pata3);
        fin4 = Esquina_Estirable(pata4);
        fin5 = Esquina_Estirable(pata5);
        fin6 = Esquina_Estirable(pata6);
        fin7 = Esquina_Estirable(pata7);
        fin8 = Esquina_Estirable(pata8);
        fin9 = Esquina_Estirable(pata9);
        fin10 = Esquina_Estirable(pata10);
        fin11 = Esquina_Estirable(pata11);
        fin12 = Esquina_Estirable(pata12);
        fin13 = Esquina_Estirable(pata13);
        fin14 = Esquina_Estirable(pata14);


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
                        estirable.setCeldaConectada(celdaEncontrada);
                        break;
                    }
                }
            }

            if (!connected) {
                signoCelda = 0;
                estirable.setCeldaConectada(null);
            }

            estirable.setSigno(signoCelda);

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

    private void calcularSalida(Cuadrados entrada1, Cuadrados entrada2, Cuadrados salida) {

        System.out.println("Signo de entrada1: " + entrada1.getSigno() + ", Signo de entrada2: " + entrada2.getSigno());

        int signoSalida;
        Color colorSalida;

        if (entrada1.getSigno() == 1 ) {

            signoSalida = 0;
            colorSalida = Color.WHITE;
        } else {

            signoSalida = 1;
            colorSalida = Color.RED;
        }


        salida.setSigno(signoSalida);
        salida.setFill(colorSalida);


        Node salidaCellNode = salida.getCeldaConectada();
        if (salidaCellNode != null && salidaCellNode instanceof Cuadrados) {
            Cuadrados salidaCell = (Cuadrados) salidaCellNode;


            Celdas celdasCorrespondientes = null;
            if (protoboard.getCelda1().getChildren().contains(salidaCell.getParent())) {
                celdasCorrespondientes = protoboard.getCelda1();
            } else if (protoboard.getCelda2().getChildren().contains(salidaCell.getParent())) {
                celdasCorrespondientes = protoboard.getCelda2();
            }

            if (celdasCorrespondientes != null) {

                Integer colIndex = GridPane.getColumnIndex(salidaCell);
                if (colIndex != null) {
                    int columna = colIndex - 1; // Ajustar si es necesario

                    celdasCorrespondientes.alternarColumna(columna, signoSalida, signoSalida == 1 ? 5.0 : 0.0);
                }
            }
        }
    }

    public void calcularSalidas() {
        // Calcular la salida para cada puerta AND usando directamente las conexiones de fin1 a fin14
        calcularSalida(fin1, fin2, fin3);
        calcularSalida(fin4, fin5, fin6);
        calcularSalida(fin9, fin10, fin8);
        calcularSalida(fin12, fin13, fin11);
    }
    
    // Getters y setters para el protoboard
    public Prototipo_Protoboard getProtoboard() {
        return protoboard;
    }

    public void setProtoboard(Prototipo_Protoboard protoboard) {
        this.protoboard = protoboard;
    }
}
