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

public class Switch_8 extends Pane{

    private Group nodo = new Group();

    private Cuadrados fin1, fin2, fin3, fin4, fin5, fin6, fin7, fin8;
    private Cuadrados fin12, fin22, fin32, fin42, fin52, fin62, fin72, fin82;
    private Line pata1, pata2, pata3, pata4, pata5, pata6, pata7, pata8;
    private Line pata12, pata22, pata32, pata42, pata52, pata62, pata72, pata82;

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

    public Switch_8() {

        this.encendido = false;

        // Cuadrado exterior usando líneas
        Line lineaSuperiorCE = crearLinea(origenX - 400 , origenY - 105 , origenX - 588 , origenY - 105 );
        Line lineaInferiorCE = crearLinea(origenX - 400 , origenY - 35 , origenX - 588 , origenY - 35 );
        Line lineaIzquierdaCE = crearLinea(origenX - 400 , origenY - 105 , origenX - 400 , origenY - 35 );
        Line lineaDerechaCE = crearLinea(origenX - 588 , origenY - 105 , origenX - 588 , origenY - 35 );

        //botones
        Cuadrados boton1 = new Cuadrados(7, 0);
        boton1.setheidht(50);
        boton1.setTranslateX(origenX - 410 );
        boton1.setTranslateY(origenY - 94);
        boton1.setFill(Color.BLACK);

        Cuadrados boton2 = new Cuadrados(7, 0);
        boton2.setheidht(50);
        boton2.setTranslateX(origenX - 433 );
        boton2.setTranslateY(origenY - 94);
        boton2.setFill(Color.BLACK);

        Cuadrados boton3 = new Cuadrados(7, 0);
        boton3.setheidht(50);
        boton3.setTranslateX(origenX - 458 );
        boton3.setTranslateY(origenY - 94);
        boton3.setFill(Color.BLACK);

        Cuadrados boton4 = new Cuadrados(7, 0);
        boton4.setheidht(50);
        boton4.setTranslateX(origenX - 483 );
        boton4.setTranslateY(origenY - 94);
        boton4.setFill(Color.BLACK);

        Cuadrados boton5 = new Cuadrados(7, 0);
        boton5.setheidht(50);
        boton5.setTranslateX(origenX - 508 );
        boton5.setTranslateY(origenY - 94);
        boton5.setFill(Color.BLACK);

        Cuadrados boton6 = new Cuadrados(7, 0);
        boton6.setheidht(50);
        boton6.setTranslateX(origenX - 533 );
        boton6.setTranslateY(origenY - 94);
        boton6.setFill(Color.BLACK);

        Cuadrados boton7 = new Cuadrados(7, 0);
        boton7.setheidht(50);
        boton7.setTranslateX(origenX - 560 );
        boton7.setTranslateY(origenY - 94);
        boton7.setFill(Color.BLACK);

        Cuadrados boton8 = new Cuadrados(7, 0);
        boton8.setheidht(50);
        boton8.setTranslateX(origenX - 583 );
        boton8.setTranslateY(origenY - 94);
        boton8.setFill(Color.BLACK);

        // Patas arriba (derecha a izquierda)
        pata1 = crearLinea(origenX - 405 , origenY - 105 , origenX - 405 , origenY - 125 );
        pata2 = crearLinea(origenX - 430 , origenY - 105 , origenX - 430, origenY - 125 );
        pata3 = crearLinea(origenX - 455 , origenY - 105 , origenX - 455, origenY - 125 );
        pata4 = crearLinea(origenX - 480 , origenY - 105, origenX - 480, origenY - 125 );
        pata5 = crearLinea(origenX - 505 , origenY - 105, origenX - 505, origenY - 125);
        pata6 = crearLinea(origenX - 530 , origenY - 105, origenX - 530, origenY - 125);
        pata7 = crearLinea(origenX - 555 , origenY - 105, origenX - 555, origenY - 125);
        pata8 = crearLinea(origenX - 579 , origenY - 105, origenX - 580, origenY - 125);

        //Patas abajo
        pata12 = crearLinea(origenX - 405 , origenY - 35 , origenX - 405 , origenY -15 );
        pata22 = crearLinea(origenX - 430 , origenY - 35 , origenX - 430, origenY - 15 );
        pata32 = crearLinea(origenX - 455 , origenY - 35 , origenX - 455, origenY - 15 );
        pata42 = crearLinea(origenX - 480 , origenY - 35, origenX - 480, origenY - 15 );
        pata52 = crearLinea(origenX - 505 , origenY - 35, origenX - 505, origenY - 15);
        pata62 = crearLinea(origenX - 530 , origenY - 35, origenX - 530, origenY - 15);
        pata72 = crearLinea(origenX - 555 , origenY - 35, origenX - 555, origenY - 15);
        pata82 = crearLinea(origenX - 579 , origenY - 35, origenX - 580, origenY - 15);

        // Fondo del cuadrado exterior
        Polygon fondoCuadradoE = crearFondo(origenX - 400 , origenY - 105 , origenX - 588 , origenY - 35 , Color.GAINSBORO);
        fin1 = Esquina_Estirable(pata1);
        fin2 = Esquina_Estirable(pata2);
        fin3 = Esquina_Estirable(pata3);
        fin4 = Esquina_Estirable(pata4);
        fin5 = Esquina_Estirable(pata5);
        fin6 = Esquina_Estirable(pata6);
        fin7 = Esquina_Estirable(pata7);
        fin8 = Esquina_Estirable(pata8);

        fin12= Esquina_Estirable(pata12);
        fin22= Esquina_Estirable(pata22);
        fin32= Esquina_Estirable(pata32);
        fin42= Esquina_Estirable(pata42);
        fin52= Esquina_Estirable(pata52);
        fin62= Esquina_Estirable(pata62);
        fin72= Esquina_Estirable(pata72);
        fin82= Esquina_Estirable(pata82);

        configurarArrastre(fin1, pata1, 1);
        configurarArrastre(fin2, pata2, 1);
        configurarArrastre(fin3, pata3, 1);
        configurarArrastre(fin4, pata4, 1);
        configurarArrastre(fin5, pata5, 1);
        configurarArrastre(fin6, pata6, 1);
        configurarArrastre(fin7, pata7, 1);
        configurarArrastre(fin8, pata8, 1);

        configurarArrastre(fin12,pata12,2);
        configurarArrastre(fin22,pata22,2);
        configurarArrastre(fin32,pata32,2);
        configurarArrastre(fin42,pata42,2);
        configurarArrastre(fin52,pata52,2);
        configurarArrastre(fin62,pata62,2);
        configurarArrastre(fin72,pata72,2);
        configurarArrastre(fin82,pata82,2);

        // Mover el nodo completo
        configurarArrastreNodo();

        // Agregar los elementos al grupo
        nodo.getChildren().addAll(
                fondoCuadradoE, boton1, boton2,boton3,boton4,boton5,boton6,boton7,boton8,lineaSuperiorCE, lineaInferiorCE,
                lineaIzquierdaCE, lineaDerechaCE, pata1, pata2, pata3, pata4,pata5,pata6,pata7,pata8,pata12,pata22,pata32,pata42,pata52,pata62,pata72,pata82,fin1,fin2,fin3,fin4,fin5,fin6,fin7,fin8,fin12,fin22,fin32,fin42,fin52,fin62,fin72,fin82);

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
            verificarConexionPata(estirable, pata, lado);
        });
    }

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

            estirable.setSigno(signoCelda);

            if (lado == 1) {

                filaEntrada = row;
                columnaEntrada = col;
            } else if (lado == 2) {

                filaSalida = row;
                columnaSalida = col;
            }
        }
    }

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

        nodo.setOnMouseReleased(event -> {
            actualizarConexionesPatas();
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

    private void actualizarConexionesPatas() {
        verificarConexionPata(fin1, pata1, 2);
        verificarConexionPata(fin2, pata2, 1);
        verificarConexionPata(fin3, pata3, 2);
        verificarConexionPata(fin4, pata4, 1);
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


