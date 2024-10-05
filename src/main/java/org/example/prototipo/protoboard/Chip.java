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

public class Chip extends Pane {
    private Group nodo = new Group();

    private Cuadrados fin1, fin2, fin3, fin4, fin5, fin6, fin7, fin8;
    private Line pata1, pata2, pata3, pata4, pata5, pata6, pata7, pata8;

    // Posición del mouse
    private double mouseX;
    private double mouseY;
    private boolean line_en_arrastre = false;

    double origenX = Main.origenX;
    double origenY = Main.origenY;

    Prototipo_Protoboard protoboard;
    int celda;


   public Chip(){


           // Cuadrado exterior usando líneas
           Line lineaSuperiorCE = crearLinea(origenX - 485 , origenY - 105, origenX - 570 , origenY - 105 );
           Line lineaInferiorCE = crearLinea(origenX - 485, origenY - 35 , origenX - 570 , origenY - 35 );
           Line lineaIzquierdaCE = crearLinea(origenX - 485 , origenY - 105 , origenX - 485 , origenY - 35 );
           Line lineaDerechaCE = crearLinea(origenX - 570 , origenY - 105 , origenX - 570 , origenY - 35 );


           // Patas
           pata1 = crearLinea(origenX - 565 , origenY - 105 , origenX -565 , origenY - 115 );
           pata2 = crearLinea(origenX - 541, origenY - 105, origenX - 541 , origenY - 115 );
           pata3 = crearLinea(origenX - 515, origenY - 105 , origenX - 515 , origenY - 115 );
           pata4 = crearLinea(origenX - 490 , origenY - 105 , origenX - 490, origenY - 115 );

           pata5 = crearLinea(origenX -565, origenY - 35 , origenX - 565, origenY - 25 );
           pata6 = crearLinea(origenX-541, origenY - 35 , origenX - 541, origenY - 25 );
           pata7 = crearLinea(origenX-515, origenY - 35 , origenX - 515, origenY - 25 );
           pata8= crearLinea(origenX-490, origenY - 35 , origenX - 490, origenY - 25 );

           fin1= Esquina_Estirable(pata1);
           fin2= Esquina_Estirable(pata2);
           fin3= Esquina_Estirable(pata3);
           fin4= Esquina_Estirable(pata4);
           fin5= Esquina_Estirable(pata5);
           fin6= Esquina_Estirable(pata6);
           fin7= Esquina_Estirable(pata7);
           fin8= Esquina_Estirable(pata8);

           configurarArrastre(fin1,pata1);
           configurarArrastre(fin2,pata2);
           configurarArrastre(fin3,pata3);
           configurarArrastre(fin4,pata4);
           configurarArrastre(fin5,pata5);
           configurarArrastre(fin6,pata6);
           configurarArrastre(fin7,pata7);
           configurarArrastre(fin8,pata8);

           // Fondo del cuadrado exterior
           Polygon fondoCuadradoE = crearFondo(origenX - 485 , origenY - 105, origenX - 570 , origenY - 35 , Color.BLACK);

           // Mover el nodo completo
           configurarArrastreNodo();

           // Agregar los elementos al grupo
           nodo.getChildren().addAll(fondoCuadradoE, lineaSuperiorCE, lineaInferiorCE, lineaIzquierdaCE, lineaDerechaCE, pata1, pata2, pata3, pata4,pata5, pata6, pata7, pata8, fin1, fin2, fin3, fin4, fin5, fin6, fin7, fin8);

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
        actualizarEstirable(fin5, pata5);
        actualizarEstirable(fin6, pata6);
        actualizarEstirable(fin7, pata7);
        actualizarEstirable(fin8, pata8);
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

    private void configurarArrastre(Cuadrados estirable, Line pata) {
       estirable.setOnMousePressed(e-> {
           empezarArrastre(e, pata);
           estirable.toFront();
       });

        estirable.setOnMouseDragged(e -> arrastrePata(e, pata, estirable));
    }

    public Prototipo_Protoboard getProtoboard() {
        return protoboard;
    }

    public void setProtoboard(Prototipo_Protoboard protoboard) {
        this.protoboard = protoboard;
    }

}


