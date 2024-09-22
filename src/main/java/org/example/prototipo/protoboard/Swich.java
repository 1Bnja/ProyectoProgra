package org.example.prototipo.protoboard;

import javafx.geometry.Bounds;
import javafx.scene.Node;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.scene.shape.Polygon;
import javafx.scene.Group;
import javafx.scene.input.MouseEvent;


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
    int colum_1,colum_2;

    boolean encendido;

    public Swich() {
        double achicar = 0.7;
        double achicar2= 0.5;
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
            if (encendido) { //cuando se apaga
                if(celda == 1){ //si es celda 1
                    protoboard.getCelda1().alternarColumna(colum_2, 0); //se pinta del signo
                }
                else{ //si es la celda 2
                    protoboard.getCelda2().alternarColumna(colum_2, 0); //se pinta del signo
                }
                cuadradoInterno.setFill(Color.BLACK); // Apagado
            } else { //cuando se prende
                //se pasa el signo de las patas a otras
                fin1.setSigno(fin2.getSigno());
                fin3.setSigno(fin4.getSigno());

                if(celda==1){
                    protoboard.getCelda1().alternarColumna(colum_2, fin1.getSigno()); //Se pinta del signo
                } else if(celda==2){
                    protoboard.getCelda2().alternarColumna(colum_2, fin1.getSigno()); //se pinta del signo
                }


                cuadradoInterno.setFill(Color.YELLOW); // Encendido
            }
            encendido = !encendido; // Cambiar estado
        });




        // Patas
        pata1 = crearLinea(origenX - 505 *achicar, origenY - 100 * achicar, origenX - 505 * achicar, origenY - 107.5 * achicar);
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
        configurarArrastre(fin2, pata2,1);
        configurarArrastre(fin3, pata3,2);
        configurarArrastre(fin4, pata4,1);

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
        Cuadrados point = new Cuadrados(11,2);
        point.setX(pata.getEndX()-5);
        point.setY(pata.getEndY()-5);
        point.setFill(Color.RED);
        return point;
    }

    private void configurarArrastre(Cuadrados estirable, Line pata, int lado) {
        estirable.setOnMousePressed(e -> {
            empezarArrastre(e, pata);
            nodo.toFront();
        });
        estirable.setOnMouseDragged(e -> Arrastre(e, pata, estirable));

        estirable.setOnMouseReleased(event ->{

            double mouseX = event.getSceneX();
            double mouseY = event.getSceneY();
            if(protoboard != null){
                Node arriba = verificarSiEstaEnCelda(mouseX,mouseY,(GridPane) protoboard.getCelda1().getChildren().getFirst());
                Node abajo = verificarSiEstaEnCelda(mouseX,mouseY,(GridPane) protoboard.getCelda2().getChildren().getFirst());
                Node bus_arriba = verificarSiEstaEnCelda(mouseX,mouseY,(GridPane) protoboard.getBus1().getChildren().getFirst());
                Node bus_abajo = verificarSiEstaEnCelda(mouseX,mouseY,(GridPane) protoboard.getBus2().getChildren().getFirst());
                int col = 0;
                int row = 0;
                if(arriba != null) {
                    col =  ((GridPane) protoboard.getCelda1().getChildren().getFirst()).getColumnIndex(arriba)-1;
                    row =  ((GridPane) protoboard.getCelda1().getChildren().getFirst()).getRowIndex(arriba)-1;
                    estirable.setSigno(protoboard.getCelda1().getSigno(row,col)); //se setea el signo a la punta del switch
                    if (lado == 1)  colum_1= col; //para saber el lado que tiene que pasarse o cortarse el signo (Se usa en el click)
                    if (lado == 2)  colum_2= col;//para saber el lado que tiene que pasarse o cortarse el signo (Se usa en el click)

                    celda=1;
                    //protoboard.getCelda1().alternarColumna(col,estirable.getSigno());
                }
                if(abajo != null) {
                    col = ((GridPane) protoboard.getCelda2().getChildren().getFirst()).getColumnIndex(abajo)-1;
                    row =  ((GridPane) protoboard.getCelda2().getChildren().getFirst()).getRowIndex(abajo)-1;
                    estirable.setSigno(protoboard.getCelda2().getSigno(row,col));
                    if (lado == 1)  colum_1= col;  //para saber el lado que tiene que pasarse o cortarse el signo (Se usa en el click)
                    if (lado == 2)  colum_2= col;  //para saber el lado que tiene que pasarse o cortarse el signo (Se usa en el click)
                    celda=2;
                    //protoboard.getCelda2().alternarColumna(col, estirable.getSigno());


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
        estirable.setY(pata.getEndY());
        estirable.setX(pata.getEndX() -5);
        estirable.setY(pata.getEndY());
    }

    private void actualizarPosiciones(){
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
        return fin1;
    }

    public Cuadrados getFin4() {
        return fin2;
    }
}
