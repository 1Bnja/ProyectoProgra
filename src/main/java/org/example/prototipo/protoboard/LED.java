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

    private Color colorOriginal;

    private boolean quemado = false;

    // Constructor de la clase LED
    public LED(Color colorLED) {
        this.colorOriginal = colorLED;

        // Crear la línea horizontal que representa el LED
        Line led1 = crearLinea(origenX - 550, origenY - 150, origenX - 515, origenY - 150);

        // Crear la curva cúbica que representa el cuerpo del LED
        curva = crearCurva(origenX - 550, origenY - 150,
                origenX - 549.25, origenY - 200,
                origenX - 515.75, origenY - 200,
                origenX - 515, origenY - 150, colorLED);

        // Crear las patas del LED
        pata1 = crearLinea(origenX - 545, origenY - 150, origenX - 545, origenY - 135);
        pata2 = crearLinea(origenX - 520, origenY - 150, origenX - 520, origenY - 135);

        // Pata positiva (ánodo)
        fin1 = crearEstirable(pata1, Color.RED);
        fin1.setSigno(1);
        fin1.setVoltaje(2);
        // Pata negativa (cátodo)
        fin2 = crearEstirable(pata2, Color.BLUE);
        fin2.setSigno(-1);
        fin2.setVoltaje(2);

        // Configurar eventos de arrastre para las patas
        configurarArrastre(fin1, pata1);
        configurarArrastre(fin2, pata2);

        // Configurar arrastre para el LED completo
        configurarArrastreNodo();

        // Añadir todos los elementos al nodo
        nodo.getChildren().addAll(led1, curva, pata1, pata2, fin1, fin2);
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

    // Método para crear una curva cúbica (el cuerpo del LED)
    private CubicCurve crearCurva(double startX, double startY, double controlX1, double controlY1, double controlX2, double controlY2, double endX, double endY, Color colorLeD) {
        CubicCurve curva = new CubicCurve(startX, startY, controlX1, controlY1, controlX2, controlY2, endX, endY);
        curva.setStroke(Color.BLACK);
        curva.setFill(colorLeD);
        return curva;
    }

    // Configurar eventos de arrastre para los extremos de las patas
    private void configurarArrastre(Cuadrados estirable, Line pata) {
        estirable.setOnMousePressed(e -> {
            if (!quemado) {
                empezarArrastre(e);
                estirable.toFront();
            }
        });
        estirable.setOnMouseDragged(e -> {
            if (!quemado) {
                arrastrePata(e, pata, estirable);
            }
        });
        estirable.setOnMouseReleased(event -> {
            if (!quemado) {
                updateFinConnection(estirable);
                checkLedState();
            }
        });
    }


    // Configurar arrastre para el nodo completo (LED)
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

        // Verificar conexiones al soltar el nodo
        nodo.setOnMouseReleased(e -> {
            checkFinConnections();
        });
    }

    // Iniciar el arrastre de una pata
    private void empezarArrastre(MouseEvent event) {
        line_en_arrastre = true;
        mouseX = event.getSceneX();
        mouseY = event.getSceneY();
    }

    // Manejar el arrastre de una pata y actualizar su posición
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

    // Crear un cuadrado estirable (extremo de la pata)
    private Cuadrados crearEstirable(Line pata, Color color) {
        Cuadrados esquina = new Cuadrados(11, 2);
        esquina.setFill(color);
        esquina.setX(pata.getEndX() - 5);
        esquina.setY(pata.getEndY() - 5);
        return esquina;
    }

    // Actualizar la posición del cuadrado estirable según la pata
    private void actualizarEstirable(Cuadrados esquina, Line pata) {
        esquina.setX(pata.getEndX() - 5);
        esquina.setY(pata.getEndY() - 5);
    }

    // Actualizar las posiciones de los extremos al mover el LED
    private void actualizarPosiciones() {
        actualizarEstirable(fin1, pata1);
        actualizarEstirable(fin2, pata2);
    }

    // Verificar las conexiones de los extremos y actualizar el estado del LED
    public void checkFinConnections() {
        updateFinConnection(fin1);
        updateFinConnection(fin2);
        checkLedState();
    }

    // Actualizar el estado de conexión de un extremo
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

            // Verificar si el extremo está sobre alguna celda
            for (GridPane gridPane : gridPanes) {
                celdaEncontrada = verificarSiEstaEnCelda(sceneX, sceneY, gridPane);
                if (celdaEncontrada != null) {
                    int col = GridPane.getColumnIndex(celdaEncontrada) - 1;
                    int row = GridPane.getRowIndex(celdaEncontrada);

                    // Obtener el signo de la celda
                    if (gridPane == gridPanes[0]) {
                        signoCelda = protoboard.getCelda1().getSigno(row, col);
                        estirable.setLugar(1);
                        estirable.setFila(row);
                        estirable.setCol(col);
                    } else if (gridPane == gridPanes[1]) {
                        signoCelda = protoboard.getCelda2().getSigno(row, col);
                        estirable.setLugar(2);
                        estirable.setFila(row);
                        estirable.setCol(col);
                    } else if (gridPane == gridPanes[2]) {
                        signoCelda = protoboard.getBus1().getSigno(row, col);
                        estirable.setLugar(0);
                        estirable.setFila(row);
                        estirable.setCol(col);
                    } else if (gridPane == gridPanes[3]) {
                        signoCelda = protoboard.getBus2().getSigno(row, col);
                        estirable.setLugar(3);
                        estirable.setFila(row);
                        estirable.setCol(col);
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

    // Verificar si las coordenadas están sobre una celda específica
    private Node verificarSiEstaEnCelda(double x, double y, GridPane gridPane) {
        for (Node child : gridPane.getChildren()) {
            Bounds boundsInScene = child.localToScene(child.getBoundsInLocal());
            if (boundsInScene.contains(x, y)) {
                return child;
            }
        }
        return null;
    }

    // Verificar el estado del LED y actualizar su apariencia
    private void checkLedState() {
        if (quemado) {
            curva.setFill(Color.BLACK);
            return;
        }

        boolean bandera = false;
        if (fin1Conectada && fin2Conectada) {
            if (signoFin1 == 0 || signoFin2 == 0) {
                // Si alguno de los extremos no tiene signo, el LED está apagado

                    curva.setFill(colorOriginal);
                // Mantener el color original si el LED está apagado
            } else if (signoFin1 == fin1.getSigno() && signoFin2 == fin2.getSigno()) {
                // El LED está correctamente polarizado y enciende

                bandera = verificar();
                if(bandera==false){
                    curva.setFill(obtenerColorEncendido(colorOriginal));  // Convertir el color "apagado" a "encendido"
                }

            } else if (signoFin1 == signoFin2) {
                // Si los signos son iguales, el LED se quema
                curva.setFill(Color.BLACK);  // Cambiar a negro cuando el LED se quema
                quemado = true;
                deshabilitarExtremos();
                mostrarAlertaLedQuemado();
            } else if (signoFin1 == fin2.getSigno() && signoFin2 == fin1.getSigno()) {
                // Polarización inversa, el LED se quema
                curva.setFill(Color.BLACK);  // Cambiar a negro cuando el LED se quema
                quemado = true;
                deshabilitarExtremos();
                mostrarAlertaLedQuemado();
            } else {
                // Cualquier otra condición, el LED está apagado
                curva.setFill(colorOriginal);  // Mantener el color original si el LED está apagado
            }
        } else {
            // Si no están conectados ambos extremos, el LED está apagado
            curva.setFill(colorOriginal);  // Mantener el color original si el LED está apagado
        }
    }

    private Color obtenerColorEncendido(Color colorOriginal) {
        if (colorOriginal.equals(Color.LIGHTBLUE)) {
            return Color.BLUE;
        } else if (colorOriginal.equals(Color.LIGHTGREEN)) {
            return Color.GREEN;
        } else if (colorOriginal.equals(Color.LIGHTCORAL)) {
            return Color.RED;
        } else if (colorOriginal.equals(Color.LIGHTYELLOW)) {
            return Color.YELLOW;
        }
        return colorOriginal;
    }

    private boolean verificar(){
        boolean verificado = false;
        if(fin1.getLugar()==1){
        if(protoboard.getCelda1().getVoltaje(fin1.getFila(),fin1.getCol())>fin1.getVoltaje()){
            curva.setFill(Color.BLACK);
            quemado = true;
            deshabilitarExtremos();
            mostrarAlertaLedQuemado();
            System.out.println("El led se quemo por sobrepasar su voltaje.");
            verificado = true;
            return verificado;}
        } else if (fin1.getLugar()==2) {
            if(protoboard.getCelda1().getVoltaje(fin1.getFila(),fin1.getCol())>fin1.getVoltaje()){
                curva.setFill(Color.BLACK);
                quemado = true;
                deshabilitarExtremos();
                mostrarAlertaLedQuemado();
                System.out.println("El led se quemo por sobrepasar su voltaje.");
                verificado = true;
                return verificado;
            }
        }
        return verificado;
    }
    // Mostrar una alerta cuando el LED se quema
    private void mostrarAlertaLedQuemado() {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("ALERTA");
        alert.setHeaderText(null);
        alert.setContentText("OH NO!! EL LED SE QUEMÓ AAAAAAAA");

        alert.showAndWait();
    }

    // Deshabilitar los extremos del LED cuando se quema
    private void deshabilitarExtremos() {
        fin1.setDisable(true);
        fin2.setDisable(true);
    }

    // Getters para los extremos del LED
    public Cuadrados getFin1() {
        return fin1;
    }

    public Cuadrados getFin2() {
        return fin2;
    }

    // Getters y setters para el protoboard
    public Prototipo_Protoboard getProtoboard() {
        return protoboard;
    }

    public void setProtoboard(Prototipo_Protoboard protoboard) {
        this.protoboard = protoboard;
    }
}
