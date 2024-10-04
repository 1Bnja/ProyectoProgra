package org.example.prototipo.protoboard;

import javafx.geometry.Bounds;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.CubicCurve;
import javafx.scene.shape.Line;

public class LED extends Pane {
    private Group nodo = new Group();
    private Line pata1, pata2;
    private Cuadrados fin1, fin2;
    private CubicCurve curva;

    // Variables para la posición del mouse
    private double mouseX, mouseY;
    private boolean line_en_arrastre = false;

    double origenX = Main.origenX;
    double origenY = Main.origenY;
    Prototipo_Protoboard protoboard;

    private boolean fin1Conectada = false;
    private boolean fin2Conectada = false;
    private int signoFin1 = 0;
    private int signoFin2 = 0;

    public LED() {
        // LED
        Line led1 = crearLinea(origenX - 550, origenY - 150, origenX - 515, origenY - 150);
        curva = crearCurva(origenX - 550, origenY - 150,
                origenX - 549.25, origenY - 200,
                origenX - 515.75, origenY - 200,
                origenX - 515, origenY - 150);
        pata1 = crearLinea(origenX - 545, origenY - 150, origenX - 545, origenY - 135);
        pata2 = crearLinea(origenX - 520, origenY - 150, origenX - 520, origenY - 135);

        // Pata positiva
        fin1 = crearEstirable(pata1, Color.RED);
        fin1.setSigno(1);
        // Pata negativa
        fin2 = crearEstirable(pata2, Color.BLUE);
        fin2.setSigno(-1);

        configurarArrastre(fin1, pata1);
        configurarArrastre(fin2, pata2);

        configurarArrastreNodo();

        nodo.getChildren().addAll(led1, curva, pata1, pata2, fin1, fin2);
        this.getChildren().add(nodo);

        this.setPickOnBounds(false);
    }

    private Line crearLinea(double startX, double startY, double endX, double endY) {
        Line linea = new Line(startX, startY, endX, endY);
        linea.setStroke(Color.BLACK);
        return linea;
    }

    private CubicCurve crearCurva(double startX, double startY,
                                  double controlX1, double controlY1,
                                  double controlX2, double controlY2,
                                  double endX, double endY) {
        CubicCurve curva = new CubicCurve(startX, startY, controlX1, controlY1,
                controlX2, controlY2, endX, endY);
        curva.setStroke(Color.BLACK);
        curva.setFill(Color.LIGHTBLUE);
        return curva;
    }

    private void configurarArrastre(Cuadrados estirable, Line pata) {
        estirable.setOnMousePressed(e -> {
            empezarArrastre(e);
            estirable.toFront();
        });
        estirable.setOnMouseDragged(e -> arrastrePata(e, pata, estirable));

        estirable.setOnMouseReleased(event -> {
            updateFinConnection(estirable);
            checkLedState();
        });
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
                checkFinConnections();
            }
        });
    }

    private void empezarArrastre(MouseEvent event) {
        line_en_arrastre = true;
        mouseX = event.getSceneX();
        mouseY = event.getSceneY();
    }

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

    private Cuadrados crearEstirable(Line pata, Color color) {
        Cuadrados esquina = new Cuadrados(11, 2);
        esquina.setFill(color);
        esquina.setX(pata.getEndX() - 5);
        esquina.setY(pata.getEndY() - 5);
        return esquina;
    }

    private void actualizarEstirable(Cuadrados esquina, Line pata) {
        esquina.setX(pata.getEndX() - 5);
        esquina.setY(pata.getEndY() - 5);
    }

    private void actualizarPosiciones() {
        actualizarEstirable(fin1, pata1);
        actualizarEstirable(fin2, pata2);
    }

    public void checkFinConnections() {
        updateFinConnection(fin1);
        updateFinConnection(fin2);
        checkLedState();
    }

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
                    int col = GridPane.getColumnIndex(celdaEncontrada) - 1;
                    int row = GridPane.getRowIndex(celdaEncontrada);

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

            if (estirable == fin1) {
                fin1Conectada = connected;
                signoFin1 = signoCelda;
            } else if (estirable == fin2) {
                fin2Conectada = connected;
                signoFin2 = signoCelda;
            }
        }
    }

    private Node verificarSiEstaEnCelda(double x, double y, GridPane gridPane) {
        for (Node child : gridPane.getChildren()) {
            Bounds boundsInScene = child.localToScene(child.getBoundsInLocal());
            if (boundsInScene.contains(x, y)) {
                return child;
            }
        }
        return null;
    }

    private void checkLedState() {
        boolean quemado = false;

        // Si ambos terminales están conectados
        if (fin1Conectada && fin2Conectada) {
            // Si ambos terminales tienen signos diferentes (1 y -1)
            if (signoFin1 != 0 && signoFin2 != 0 && signoFin1 != signoFin2) {
                curva.setFill(Color.YELLOW);  // LED encendido
            } else {
                curva.setFill(Color.LIGHTBLUE);  // LED apagado
            }

            // Verificar si el LED está en una configuración incorrecta (ejemplo, ambos terminales con el mismo signo)
            if (signoFin1 == signoFin2 && signoFin1 != 0) {
                quemado = true;  // LED quemado si ambos terminales tienen el mismo signo
                curva.setFill(Color.RED);  // Color rojo para LED quemado
            }

        } else {
            // Apagar el LED si alguno de los terminales no está conectado
            curva.setFill(Color.LIGHTBLUE);  // LED apagado
        }

        // Mostrar una alerta si el LED está quemado
        if (quemado) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("ALERTA");
            alert.setHeaderText(null);
            alert.setContentText("OH NO!! EL LED SE QUEMÓ AAAAAAAA");
            alert.showAndWait();
        }
    }


    public Cuadrados getFin1() {
        return fin1;
    }

    public Cuadrados getFin2() {
        return fin2;
    }

    public Prototipo_Protoboard getProtoboard() {
        return protoboard;
    }

    public void setProtoboard(Prototipo_Protoboard protoboard) {
        this.protoboard = protoboard;
    }
}
