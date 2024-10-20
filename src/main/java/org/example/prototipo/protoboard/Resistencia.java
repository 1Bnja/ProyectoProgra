package org.example.prototipo.protoboard;

import javafx.geometry.Bounds;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.scene.shape.Polygon;

public class Resistencia extends Pane {
    private Group nodo = new Group();
    private Line pata1, pata2;
    private Line lineaSu, lineaIn, lineaIzq, lineaDer;
    private Line franja1, franja2;
    private Cuadrados fin1, fin2;
    private Polygon fondo;

    // Variables para manejar la posición del mouse durante el arrastre
    private double mouseX, mouseY;
    private boolean line_en_arrastre = false;

    // Coordenadas de origen para posicionar la resistencia
    private double origenX = Main.origenX;
    private double origenY = Main.origenY;
    Prototipo_Protoboard protoboard;

    // Variables para verificar si los extremos están conectados
    private boolean fin1Conectada = false;
    private boolean fin2Conectada = false;

    // Valor de la resistencia en ohmios
    private double valorResistencia;

    // Signos de las conexiones en los extremos
    private int signoFin1 = 0;
    private int signoFin2 = 0;

    // Constructor de la clase Resistencia que recibe el valor de la resistencia
    public Resistencia(double valorResistencia) {
        this.valorResistencia = valorResistencia;

        // Crear las líneas que forman el contorno de la resistencia
        lineaSu = crearLinea(origenX - 550, origenY - 150, origenX - 532, origenY - 150, Color.BLACK);
        lineaIn = crearLinea(origenX - 550, origenY - 126, origenX - 532, origenY - 126, Color.BLACK);
        lineaIzq = crearLinea(origenX - 550, origenY - 150, origenX - 550, origenY - 126, Color.BLACK);
        lineaDer = crearLinea(origenX - 532, origenY - 150, origenX - 532, origenY - 126, Color.BLACK);

        // Crear las patas de la resistencia
        pata1 = crearLinea(origenX - 541, origenY - 150, origenX - 541, origenY - 160, Color.BLACK);
        pata2 = crearLinea(origenX - 541, origenY - 126, origenX - 541, origenY - 116, Color.BLACK);

        // Crear el fondo de la resistencia
        fondo = crearFondo(origenX - 550, origenY - 150, origenX - 532, origenY - 126, Color.LIGHTGRAY);

        // Crear las franjas de colores que representan el valor de la resistencia
        franja1 = crearLinea(origenX - 549, origenY - 142, origenX - 533, origenY - 142, Color.RED);
        franja2 = crearLinea(origenX - 549, origenY - 134, origenX - 533, origenY - 134, Color.GREEN);

        // Crear los puntos estirables en los extremos de las patas
        fin1 = crearEstirable(pata1, Color.RED);
        fin2 = crearEstirable(pata2, Color.RED);

        // Configurar los eventos de arrastre para los extremos
        configurarArrastre(fin1, pata1);
        configurarArrastre(fin2, pata2);

        // Configurar el arrastre para la resistencia completa
        configurarArrastreNodo();

        // Añadir todos los elementos al nodo
        nodo.getChildren().addAll(fondo, lineaSu, lineaIn, lineaIzq, lineaDer, pata1, pata2, franja1, franja2, fin1, fin2);

        // Añadir el nodo al panel
        this.getChildren().add(nodo);
        this.setPickOnBounds(false);
    }

    // Método para crear una línea entre dos puntos con un color específico
    private Line crearLinea(double startX, double startY, double endX, double endY, Color color) {
        Line linea = new Line(startX, startY, endX, endY);
        linea.setStroke(color);
        return linea;
    }

    // Método para crear un rectángulo (fondo de la resistencia)
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
    private Cuadrados crearEstirable(Line pata, Color color) {
        Cuadrados pin = new Cuadrados(11, 2);
        pin.setFill(color);
        pin.setX(pata.getEndX() - 5);
        pin.setY(pata.getEndY() - 5);
        return pin;
    }

    // Métodos para manejar el arrastre de las patas

    // Inicia el arrastre al presionar el mouse
    private void empezarArrastre(MouseEvent e) {
        line_en_arrastre = true;
        mouseX = e.getSceneX();
        mouseY = e.getSceneY();
    }

    // Maneja el arrastre de una pata y actualiza su posición
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

    // Actualiza la posición del punto estirable basado en la pata
    private void actualizarEstirable(Cuadrados esquina, Line pata) {
        esquina.setX(pata.getEndX() - 5);
        esquina.setY(pata.getEndY() - 5);
    }

    // Actualiza el estado de conexión de un extremo
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

            // Verificar si el extremo está sobre alguna celda del protoboard
            for (GridPane gridPane : gridPanes) {
                celdaEncontrada = verificarSiEstaEnCelda(sceneX, sceneY, gridPane);
                if (celdaEncontrada != null) {
                    int col = GridPane.getColumnIndex(celdaEncontrada) - 1;
                    int row = GridPane.getRowIndex(celdaEncontrada);

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
                    break;
                }
            }

            if (!connected) {
                signoCelda = 0;
            }

            // Actualizar el estado de conexión y el signo del extremo
            if (estirable == fin1) {
                fin1Conectada = connected;
                signoFin1 = signoCelda;
            } else if (estirable == fin2) {
                fin2Conectada = connected;
                signoFin2 = signoCelda;
            }
        }
    }

    // Verifica si las coordenadas están sobre una celda específica
    private Node verificarSiEstaEnCelda(double x, double y, GridPane gridPane) {
        for (Node child : gridPane.getChildren()) {
            Bounds boundsInScene = child.localToScene(child.getBoundsInLocal());
            if (boundsInScene.contains(x, y)) {
                return child;
            }
        }
        return null;
    }

    // Configura los eventos de arrastre para los extremos de las patas
    private void configurarArrastre(Cuadrados estirable, Line pata) {
        estirable.setOnMousePressed(e -> {
            empezarArrastre(e);
            estirable.toFront();
        });
        estirable.setOnMouseDragged(e -> arrastrePata(e, pata, estirable));

        estirable.setOnMouseReleased(event -> {
            updateFinConnection(estirable);
            checkResistorState();
        });
    }

    // Métodos para manejar el arrastre del nodo completo (resistencia)

    // Configura el arrastre para la resistencia completa
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
                // Desactivar chequeo en movimiento
            }
        });

        // Verificar conexiones al soltar el nodo
        nodo.setOnMouseReleased(e -> {
            checkFinConnections();
        });
    }

    // Actualiza las posiciones de los extremos al mover la resistencia
    private void actualizarPosiciones() {
        actualizarEstirable(fin1, pata1);
        actualizarEstirable(fin2, pata2);
    }

    // Verificar las conexiones de los extremos y actualizar el estado de la resistencia
    public void checkFinConnections() {
        updateFinConnection(fin1);
        updateFinConnection(fin2);
        checkResistorState();
    }

    // Verifica el estado de la resistencia y actualiza su apariencia
    public void checkResistorState() {
        boolean quemado = false;

        // Si ambos extremos tienen el mismo signo y no son cero, la resistencia se quema
        if (signoFin1 == signoFin2 && signoFin1 != 0) {
            quemado = true;
            fondo.setFill(Color.RED);
            franja1.setStroke(Color.RED);
        }

        if (quemado) {
            // Mostrar una alerta indicando que la resistencia se quemó
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("ALERTA");
            alert.setHeaderText(null);
            alert.setContentText("¡OH NO! LA RESISTENCIA SE QUEMÓ");
            alert.showAndWait();
        }
    }

    // Getters y setters para el protoboard

    public Prototipo_Protoboard getProtoboard() {
        return protoboard;
    }

    public void setProtoboard(Prototipo_Protoboard protoboard) {
        this.protoboard = protoboard;
    }
}
