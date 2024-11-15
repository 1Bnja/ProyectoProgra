package org.example.prototipo.protoboard;

import javafx.scene.Group;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;

public class Display extends Pane {
    private Group nodo = new Group();

    private Cuadrados fin1, fin2, fin3, fin4, fin5, fin6, fin7, fin8, fin9, fin10;
    private Line linea1, linea2, linea3, linea4, linea5, linea6, linea7, linea8, linea9, linea10;

    private Line a, b, c, d, e, f, g;

    private double mouseX;
    private double mouseY;
    private boolean line_en_arrastre = false;

    private double origenX = Main.origenX;
    private double origenY = Main.origenY;

    private Prototipo_Protoboard protoboard;

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
        fin2 = Esquina_Estirable(linea2); // con f
        fin3 = Esquina_Estirable(linea3); // Vcc
        fin4 = Esquina_Estirable(linea4); // con a
        fin5 = Esquina_Estirable(linea5); // con b
        fin6 = Esquina_Estirable(linea6); // con e
        fin7 = Esquina_Estirable(linea7); // con d
        fin8 = Esquina_Estirable(linea8); // con Vcc
        fin9 = Esquina_Estirable(linea9); // con c
        fin10 = Esquina_Estirable(linea10);


        configurarArrastreNodo();

        nodo.getChildren().addAll(
                display, linea1, linea2, linea3, linea4, linea5, linea6, linea7, linea8, linea9, linea10, a, d, g, b, f, c, e,
                fin1, fin2, fin3, fin4, fin5, fin6, fin7, fin8, fin9, fin10
        );

        this.getChildren().add(nodo);
        this.setPickOnBounds(false);
    }

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

            }
        });

        // Actualizar y verificar las conexiones al soltar el nodo
        nodo.setOnMouseReleased(e -> {
        });
    }

    private Line crearLinea(double startX, double startY, double endX, double endY) {
        Line linea = new Line(startX, startY, endX, endY);
        linea.setStroke(Color.BLACK);
        return linea;
    }

    private Cuadrados Esquina_Estirable(Line pata) {
        Cuadrados point = new Cuadrados(11, 2);
        point.setX(pata.getEndX() - 5);
        point.setY(pata.getEndY() - 5);
        point.setFill(Color.ORANGE);
        return point;
    }


    public void setProtoboard(Prototipo_Protoboard protoboard) {
        this.protoboard = protoboard;
    }
}
