package org.example.prototipo.protoboard;

import javafx.geometry.Bounds;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;

public class Display extends Pane {
    private Group nodo = new Group();

    private Cuadrados fin1, fin2, fin3, fin4, fin5, fin6, fin7, fin8, fin9, fin10;
    private Line linea1, linea2, linea3, linea4, linea5, linea6, linea7, linea8, linea9, linea10;

    public Line a, b, c, d, e, f, g;

    private double mouseX;
    private double mouseY;
    private boolean line_en_arrastre = false;

    private double origenX = Main.origenX;
    private double origenY = Main.origenY;

    private Prototipo_Protoboard protoboard;
    Switch_8 switch8;

    public Display(){
        double posX = origenX - 200;
        double posY = origenY - 200;
        int ancho = 110;
        int alto = 130;

        Cuadrados display = new Cuadrados(ancho, alto, posX, posY, Color.BLACK);

        // Patas del display
        linea1 = crearLinea(origenX - 193, origenY - 200, origenX - 193, origenY - 216);
        linea2 = crearLinea(origenX - 169, origenY - 200, origenX - 169, origenY - 216);
        linea3 = crearLinea(origenX - 145, origenY - 200, origenX - 145, origenY - 216);
        linea4 = crearLinea(origenX - 121, origenY - 200, origenX - 121, origenY - 216);
        linea5 = crearLinea(origenX - 97, origenY - 200, origenX - 97, origenY - 216);

        linea6 = crearLinea(origenX - 193, origenY - 70, origenX - 193, origenY - 54);
        linea7 = crearLinea(origenX - 169, origenY - 70, origenX - 169, origenY - 54);
        linea8 = crearLinea(origenX - 145, origenY - 70, origenX - 145, origenY - 54);
        linea9 = crearLinea(origenX - 121, origenY - 70, origenX - 121, origenY - 54);
        linea10 = crearLinea(origenX - 97, origenY - 70, origenX - 97, origenY - 54);

        //Segmentos del display
        //Horizontales
        a = crearLinea(origenX - 172, origenY - 187, origenX - 118, origenY - 187);
        g = crearLinea(origenX - 172, origenY - 135, origenX - 118, origenY - 135);
        d = crearLinea(origenX - 172, origenY - 83, origenX - 118, origenY - 83);

        // Verticales
        b = crearLinea(origenX - 108, origenY - 180, origenX - 108, origenY - 142); // Superior derecha
        f = crearLinea(origenX - 182, origenY - 180, origenX - 182, origenY - 142); // Superior izquierda
        c = crearLinea(origenX - 108, origenY - 128, origenX - 108, origenY - 90); // Inferior derecha
        e = crearLinea(origenX - 182, origenY - 128, origenX - 182, origenY - 90); // Inferior izquierda

        a.setStroke(Color.GRAY);
        a.setStrokeWidth(7);
        g.setStroke(Color.GRAY);
        g.setStrokeWidth(7);
        d.setStroke(Color.GRAY);
        d.setStrokeWidth(7);

        b.setStroke(Color.GRAY);
        b.setStrokeWidth(7);
        f.setStroke(Color.GRAY);
        f.setStrokeWidth(7);
        c.setStroke(Color.GRAY);
        c.setStrokeWidth(7);
        e.setStroke(Color.GRAY);
        e.setStrokeWidth(7);


        fin1 = Esquina_Estirable(linea1); // con g
        fin1.setLetra(7);
        fin2 = Esquina_Estirable(linea2); // con f
        fin2.setLetra(6);
        fin3 = Esquina_Estirable(linea3); // Vcc
        fin4 = Esquina_Estirable(linea4); // con a
        fin4.setLetra(1);
        fin5 = Esquina_Estirable(linea5); // con b
        fin5.setLetra(2);
        fin6 = Esquina_Estirable(linea6); // con e
        fin6.setLetra(5);
        fin7 = Esquina_Estirable(linea7); // con d
        fin7.setLetra(4);
        fin8 = Esquina_Estirable(linea8); // con Vcc
        fin8.setLetra(5);
        fin9 = Esquina_Estirable(linea9); // con c
        fin9.setLetra(3);
        fin10 = Esquina_Estirable(linea10);

        configurarArrastre(fin1, linea1);
        configurarArrastre(fin2, linea2);
        configurarArrastre(fin3, linea3);
        configurarArrastre(fin4, linea4);
        configurarArrastre(fin5, linea5);
        configurarArrastre(fin6, linea6);
        configurarArrastre(fin7, linea7);
        configurarArrastre(fin8, linea8);
        configurarArrastre(fin9, linea9);
        configurarArrastre(fin10, linea10);


        configurarArrastreNodo();

        nodo.getChildren().addAll(
                display, linea1, linea2, linea3, linea4, linea5, linea6, linea7, linea8, linea9, linea10, a, d, g, b, f, c, e,
                fin1, fin2, fin3, fin4, fin5, fin6, fin7, fin8, fin9, fin10
        );

        this.getChildren().add(nodo);
        this.setPickOnBounds(false);
    }

    // Configura el comportamiento del nodo completo (Group) para que pueda ser arrastrado dentro de la interfaz.
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

    // Actualiza las posiciones de los puntos estirables (Cuadrados) asociados a las patas del display al mover el nodo.
    protected void actualizarPosiciones() {
        actualizarEstirable(fin1, linea1);
        actualizarEstirable(fin2, linea2);
        actualizarEstirable(fin3, linea3);
        actualizarEstirable(fin4, linea4);
        actualizarEstirable(fin5, linea5);
        actualizarEstirable(fin6, linea6);
        actualizarEstirable(fin7, linea7);
        actualizarEstirable(fin8, linea8);
        actualizarEstirable(fin9, linea9);
        actualizarEstirable(fin10, linea10);
    }

    // Verifica y actualiza las conexiones de todas las patas del display con el protoboard.
    protected void checkFinConnections() {
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
        onoff();

    }

    // Crea una línea con las coordenadas especificadas y un color negro predeterminado.
    private Line crearLinea(double startX, double startY, double endX, double endY) {
        Line linea = new Line(startX, startY, endX, endY);
        linea.setStroke(Color.BLACK);
        return linea;
    }

    // Crea un punto estirable (Cuadrados) asociado al extremo de una línea.
    private Cuadrados Esquina_Estirable(Line pata) {
        Cuadrados point = new Cuadrados(11, 2);
        point.setX(pata.getEndX() - 5);
        point.setY(pata.getEndY() - 5);
        point.setFill(Color.ORANGE);
        return point;
    }

    // Inicia el proceso de arrastre para una pata del display.
    private void empezarArrastre(MouseEvent e) {
        line_en_arrastre = true;
        mouseX = e.getSceneX();
        mouseY = e.getSceneY();
    }

    // Permite arrastrar y mover una pata del display, actualizando su posición.
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

    // Verifica si un punto estirable está conectado a una celda del protoboard y actualiza su estado (signo, voltaje, y conexión).
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
                    int colIndex = GridPane.getColumnIndex(celdaEncontrada) - 1;
                    Integer rowIndex = GridPane.getRowIndex(celdaEncontrada);

                    // Obtener el signo y voltaje de la celda
                    if (gridPane == gridPanes[0]) {
                        signoCelda = protoboard.getCelda1().getSigno(rowIndex, colIndex);
                        estirable.setVoltaje(protoboard.getCelda1().getVoltaje(rowIndex, colIndex));
                        estirable.setSigno(signoCelda);
                        estirable.setLetra2(protoboard.getCelda1().getAsignarLetra(rowIndex,colIndex));
                    } else if (gridPane == gridPanes[1]) {
                        signoCelda = protoboard.getCelda2().getSigno(rowIndex, colIndex);
                        estirable.setVoltaje(protoboard.getCelda2().getVoltaje(rowIndex, colIndex));
                        estirable.setSigno(signoCelda);
                        estirable.setLetra2(protoboard.getCelda2().getAsignarLetra(rowIndex,colIndex));
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
            if(estirable==fin1){
                fin1.setSigno(signoCelda);
                fin1.setLetra2(estirable.getLetra2());
            }  if(estirable==fin2){
                fin2.setSigno(signoCelda);
                fin2.setLetra2(estirable.getLetra2());
            } if(estirable==fin3){
                fin3.setSigno(signoCelda);
                fin3.setLetra2(estirable.getLetra2());
            } if(estirable==fin4){
                fin4.setSigno(signoCelda);
                fin4.setLetra2(estirable.getLetra2());
            } if(estirable==fin5){
                fin5.setSigno(signoCelda);
                fin5.setLetra2(estirable.getLetra2());
            } if(estirable==fin6){
                fin6.setSigno(signoCelda);
                fin6.setLetra2(estirable.getLetra2());
            } if(estirable==fin7){
                fin7.setSigno(signoCelda);
                fin7.setLetra2(estirable.getLetra2());
            } if(estirable==fin8){
                fin8.setSigno(signoCelda);
                fin8.setLetra2(estirable.getLetra2());
            }

        }

    }

    // Comprueba si un punto dado (coordenadas X e Y) está dentro de los límites de una celda en el GridPane.
    protected Node verificarSiEstaEnCelda(double x, double y, GridPane gridPane) {
        for (Node child : gridPane.getChildren()) {
            Bounds boundsInScene = child.localToScene(child.getBoundsInLocal());
            if (boundsInScene.contains(x, y)) {
                return child;
            }
        }
        return null;
    }

    // Configura el comportamiento de arrastre para un punto estirable y su pata asociada.
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

    // Actualiza la posición de un punto estirable en función del extremo de su línea asociada.
    private void actualizarEstirable(Cuadrados esquina, Line pata) {
        double nuevoX = pata.getEndX() - 5;
        double nuevoY = pata.getEndY() - 5;

        if (esquina.getX() != nuevoX || esquina.getY() != nuevoY) {
            esquina.setX(nuevoX);
            esquina.setY(nuevoY);
        }
    }

    // Asigna un protoboard al display, para gestionar sus interacciones y conexiones.
    public void setProtoboard(Prototipo_Protoboard protoboard) {
        this.protoboard = protoboard;
    }

    public Switch_8 getSwitch8() {
        return switch8;
    }

    public void setSwitch8(Switch_8 switch8) {
        this.switch8 = switch8;
    }


    public int coneccion(){
        if(verificacion(fin1) && verificacion(fin2) && verificacion(fin4) && verificacion(fin5) && verificacion(fin6) && verificacion(fin7) && verificacion(fin9)){
            if(fin3.getSigno()==1 && fin8.getSigno()==1){
                if(fin3.getVoltaje()<=2 && fin8.getVoltaje()<=2){
                    return 1;
                }else{
                    System.out.println("Mucha corriente");
                    return 0;
                }
            }

        }
        return 0;
    }

    public boolean verificacion(Cuadrados fin){
        if(fin.getLetra() == fin.getLetra2()){
            return true;
        }else{
            return false;
        }

    }
    public void onoff(){
        if(coneccion()==0){
            apagado();
        }
    }
    public void apagado(){
        a.setStroke(Color.GRAY);
        b.setStroke(Color.GRAY);
        c.setStroke(Color.GRAY);
        d.setStroke(Color.GRAY);
        e.setStroke(Color.GRAY);
        f.setStroke(Color.GRAY);
        g.setStroke(Color.GRAY);
    }

}
