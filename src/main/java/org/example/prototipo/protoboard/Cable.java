package org.example.prototipo.protoboard;

import javafx.geometry.Bounds;
import javafx.scene.Node;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;

public class Cable extends Pane {

    public Line line;
    private Cuadrados inicio;
    private Cuadrados fin;
    private double mouseX, mouseY;
    Bateria bateria;
    LED led;
    Swich boton;
    Prototipo_Protoboard protoboard;

    public Cable(double startX, double startY, double endX, double endY) {

        line = new Line(startX, startY, endX, endY);
        line.setStroke(Color.GREENYELLOW);
        line.setStrokeWidth(7);

        // Crear los extremos del cable con la clase Cuadrados
        inicio = new Cuadrados(12, 2);
        fin = new Cuadrados(12, 2);
        inicio.setFillColor(Color.RED);
        inicio.setSigno(0);
        fin.setSigno(0);
        fin.setFillColor(Color.RED);


        inicio.setX(startX - inicio.getWidth() / 2);
        inicio.setY(startY - inicio.getHeight() / 2);
        fin.setX(endX - fin.getWidth() / 2);
        fin.setY(endY - fin.getHeight() / 2);


        this.setPickOnBounds(false);

        inicio.setOnMouseDragged(event -> arrastrarExtremo(event, true));
        fin.setOnMouseDragged(event -> arrastrarExtremo(event, false));

        line.setOnMousePressed(this::iniciarMovimientoLinea);
        line.setOnMouseDragged(this::moverLineaCompleta);

        inicio.setOnMouseReleased(event ->{

            double mouseX = event.getSceneX();
            double mouseY = event.getSceneY();

            if(protoboard != null){
                Node arriba = verificarSiEstaEnCelda(mouseX,mouseY,(GridPane) protoboard.getCelda1().getChildren().getFirst());
                Node abajo = verificarSiEstaEnCelda(mouseX,mouseY,(GridPane) protoboard.getCelda2().getChildren().getFirst());
                Node bus_arriba = verificarSiEstaEnCelda(mouseX,mouseY,(GridPane) protoboard.getBus1().getChildren().getFirst());
                Node bus_abajo = verificarSiEstaEnCelda(mouseX,mouseY,(GridPane) protoboard.getBus2().getChildren().getFirst());
                int col = 0;
                int row = 0;
                if(arriba==null && abajo==null && bus_arriba==null && bus_abajo==null){ //Para despintar, todos tienen que estar en null
                    //se pregunta el lugar, 0= bus1 , 1=celda1, 2=celda2, 3= bus2
                    if(inicio.getLugar()==1) {
                        protoboard.getCelda1().alternarColumna(inicio.getCol(), 0);
                    } if(inicio.getLugar()==2) {
                        protoboard.getCelda2().alternarColumna(inicio.getCol(), 0);
                    } if(inicio.getLugar()==3) {
                        protoboard.getBus2().toggleFilaBus(inicio.getFila(), 0);
                    } if(inicio.getLugar()==0) {
                        protoboard.getBus1().toggleFilaBus(inicio.getFila(),0);
                    }
                }
                if(arriba != null) {
                    col =  ((GridPane) protoboard.getCelda1().getChildren().getFirst()).getColumnIndex(arriba)-1;
                    row =  ((GridPane) protoboard.getCelda1().getChildren().getFirst()).getRowIndex(arriba);
                    //
                    if(inicio.getSigno() != 0){ //si el inicio tiene carga
                        protoboard.getCelda1().alternarColumna(col,inicio.getSigno()); //se pinta la col del color del signo de inicio
                    }
                    else{ //si el signo de inicio es 0
                        setSignoColor(row,col,protoboard.getCelda1().getSigno(row,col)); //se coloca el signo y se pinta
                    }
                    inicio.setLugar(1);
                    inicio.setFila(row);
                    inicio.setCol(col);
                }
                if(abajo != null) {
                    col = ((GridPane) protoboard.getCelda2().getChildren().getFirst()).getColumnIndex(abajo)-1;
                    row =  ((GridPane) protoboard.getCelda2().getChildren().getFirst()).getRowIndex(abajo);
                    if(inicio.getSigno() != 0){//si el inicio tiene carga
                        protoboard.getCelda2().alternarColumna(col,inicio.getSigno()); //se pinta la col del color del signo de inicio
                    }
                    else{ //si el signo de inicio es 0
                        setSignoColor(row,col,protoboard.getCelda2().getSigno(row,col)); //se coloca el signo y se pinta
                    }
                    inicio.setLugar(2);
                    inicio.setFila(row);
                    inicio.setCol(col);

                }
                if(bus_abajo != null) {
                    row = ((GridPane) protoboard.getBus2().getChildren().getFirst()).getRowIndex(bus_abajo);
                    col = ((GridPane) protoboard.getBus2().getChildren().getFirst()).getColumnIndex(bus_abajo)-1;
                    if(inicio.getSigno() != 0){ //si el inicio tiene carga
                        protoboard.getBus2().setSigno(row,col,inicio.getSigno()); //se coloca el  signo de inicio
                        protoboard.getBus2().toggleFilaBus(row,inicio.getSigno() );   //se pinta la col del color del signo de inicio
                    }
                    else{ //si el signo de inicio es 0
                        setSignoColor(row,col,protoboard.getBus2().getSigno(row,col)); //se coloca el signo y se pinta
                    }
                    inicio.setLugar(3);
                    inicio.setFila(row);
                    inicio.setCol(col);

                }
                if(bus_arriba != null) {
                    row = ((GridPane) protoboard.getBus1().getChildren().getFirst()).getRowIndex(bus_arriba);
                    col = ((GridPane) protoboard.getBus1().getChildren().getFirst()).getColumnIndex(bus_arriba)-1;
                    if(inicio.getSigno() != 0){ //si el inicio tiene carga
                        protoboard.getBus1().setSigno(row,col,inicio.getSigno()); //se coloca el  signo de inicio
                        protoboard.getBus1().toggleFilaBus(row,inicio.getSigno() ); //se pinta la col del color del signo de inicio
                    }
                    else{ //si el signo de inicio es 0
                        setSignoColor(row,col,protoboard.getBus1().getSigno(row,col)); //se coloca el signo y se pinta
                    }
                    inicio.setLugar(0);
                    inicio.setFila(row);
                    inicio.setCol(col);
                }
            }

            if(bateria != null) {  //pinta el cable del signo del conector de la bateria.
                if(verificarSiEstaEnTerminalNegativo(mouseX,mouseY,bateria)){
                    inicio.setSigno(bateria.getConectorNegativo().getSigno());
                    inicio.setFill(bateria.conectorNegativo.getFill());
                    fin.setFill(bateria.conectorNegativo.getFill());
                    line.setStroke(bateria.conectorNegativo.getFillColor());
                    fin.setSigno(bateria.getConectorNegativo().getSigno());
                }
                if(verificarSiEstaEnTerminalPositivo(mouseX,mouseY,bateria)){
                    inicio.setSigno(bateria.getConectorPositivo().getSigno());
                    inicio.setFill(bateria.conectorPositivo.getFill());
                    fin.setFill(bateria.conectorPositivo.getFill());
                    line.setStroke(bateria.conectorPositivo.getFillColor());
                    fin.setSigno(bateria.getConectorPositivo().getSigno());
                }
            }

            //if(led != null){
              //  if(verificarSiEstaEnfin1(mouseX,mouseY,led)){

             //   }
           // }

            /*if(inicio !=null){
                if(bateria.conectorPositivo.getBoundsInParent().intersects(inicio.getBoundsInParent())){
                    inicio.setFill(bateria.conectorPositivo.getFill());
                    fin.setFill(bateria.conectorPositivo.getFill());
                    line.setStroke(bateria.conectorPositivo.getFillColor());
                } else if (bateria.conectorNegativo.getBoundsInParent().intersects(inicio.getBoundsInParent())){
                    inicio.setFill(bateria.conectorNegativo.getFill());
                    fin.setFill(bateria.conectorNegativo.getFill());
                    line.setStroke(bateria.conectorNegativo.getFillColor());
                }else{
                    System.out.println("no se pudo :p");}
            }*/
        });


        fin.setOnMouseReleased(event ->{

            double mouseX = event.getSceneX();
            double mouseY = event.getSceneY();
            if(protoboard != null){
                Node arriba = verificarSiEstaEnCelda(mouseX,mouseY,(GridPane) protoboard.getCelda1().getChildren().getFirst());
                Node abajo = verificarSiEstaEnCelda(mouseX,mouseY,(GridPane) protoboard.getCelda2().getChildren().getFirst());
                Node bus_arriba = verificarSiEstaEnCelda(mouseX,mouseY,(GridPane) protoboard.getBus1().getChildren().getFirst());
                Node bus_abajo = verificarSiEstaEnCelda(mouseX,mouseY,(GridPane) protoboard.getBus2().getChildren().getFirst());
                int col = 0;
                int row = 0;

                if(arriba==null && abajo==null && bus_arriba==null && bus_abajo==null){ //Para despintar, todos tienen que estar en null
                   //se pregunta el lugar, 0= bus1 , 1=celda1, 2=celda2, 3= bus2
                    if(fin.getLugar()==1) {
                        protoboard.getCelda1().alternarColumna(fin.getCol(), 0);
                    } if(fin.getLugar()==2) {
                        protoboard.getCelda2().alternarColumna(fin.getCol(), 0);
                    } if(fin.getLugar()==3) {
                        protoboard.getBus2().toggleFilaBus(fin.getFila(),0);
                    } if(fin.getLugar()==0) {
                        protoboard.getBus1().toggleFilaBus(fin.getFila(),0);
                    }
                }
                if(arriba != null) {
                    col =  ((GridPane) protoboard.getCelda1().getChildren().getFirst()).getColumnIndex(arriba)-1;
                    row =  ((GridPane) protoboard.getCelda1().getChildren().getFirst()).getRowIndex(arriba);

                    if(fin.getSigno() != 0){ //si el fin tiene carga
                        protoboard.getCelda1().alternarColumna(col,fin.getSigno()); //se coloca en la col el signo y se pinta
                    }
                    else{ //si el fin no tiene carga
                        setSignoColor(row,col,protoboard.getCelda1().getSigno(row,col)); //se coloca en el cable el signo de la col y se pinta
                    }
                    fin.setLugar(1);
                    fin.setFila(row);
                    fin.setCol(col);
                }
                if(abajo != null) { //si el fin tiene carga
                    col = ((GridPane) protoboard.getCelda2().getChildren().getFirst()).getColumnIndex(abajo)-1;
                    row =  ((GridPane) protoboard.getCelda2().getChildren().getFirst()).getRowIndex(abajo);
                    if(fin.getSigno() != 0){
                        protoboard.getCelda2().alternarColumna(col,fin.getSigno()); //se coloca en la col el signo y se pinta
                    }
                    else{//si el fin no tiene carga
                        setSignoColor(row,col,protoboard.getCelda2().getSigno(row,col));//se coloca en el cable el signo de la col y se pinta
                    }
                    fin.setLugar(2);
                    fin.setFila(row);
                    fin.setCol(col);

                }
                if(bus_abajo != null) {
                    row = ((GridPane) protoboard.getBus2().getChildren().getFirst()).getRowIndex(bus_abajo);
                    col = ((GridPane) protoboard.getBus2().getChildren().getFirst()).getColumnIndex(bus_abajo)-1;
                    //fin.setSigno(protoboard.getBus2().getSigno(row,col));

                    if(fin.getSigno() != 0){//si el fin tiene carga
                        protoboard.getBus2().setSigno(row,col,fin.getSigno()); //se coloca el signo del fin
                        protoboard.getBus2().toggleFilaBus(row, fin.getSigno()); //se pinta del color del signo del fin
                    }
                    else{
                        setSignoColor(row,col,protoboard.getBus2().getSigno(row,col));
                    }
                    fin.setLugar(3);
                    fin.setFila(row);
                    fin.setCol(col);

                }
                if(bus_arriba != null) { //si el fin tiene carga
                    row = ((GridPane) protoboard.getBus1().getChildren().getFirst()).getRowIndex(bus_arriba);
                    col = ((GridPane) protoboard.getBus1().getChildren().getFirst()).getColumnIndex(bus_arriba)-1;
                    //fin.setSigno(protoboard.getBus1().getSigno(row,col));  //TODO hacer al reves, set el signo el cable en el cuadro del protoboard y setear los colores de los buses si es necesario

                    if(fin.getSigno() != 0){ //si el fin no tiene carga
                        protoboard.getBus1().setSigno(row,col,fin.getSigno());  //se coloca el signo del fin
                        protoboard.getBus1().toggleFilaBus(row, fin.getSigno()); //se pinta del color del signo del fin
                    }
                    else{//si el fin no tiene carga
                        setSignoColor(row,col,protoboard.getBus1().getSigno(row,col));
                    }
                    fin.setLugar(0);
                    fin.setFila(row);
                    fin.setCol(col);

                }
            }

            if(bateria != null) { //se pinta el cable del color del signo del conector de la bateria.
                if(verificarSiEstaEnTerminalNegativo(mouseX,mouseY,bateria)){
                    inicio.setSigno(bateria.getConectorNegativo().getSigno());
                    inicio.setFill(bateria.conectorNegativo.getFill());
                    fin.setFill(bateria.conectorNegativo.getFill());
                    line.setStroke(bateria.conectorNegativo.getFillColor());
                    fin.setSigno(bateria.getConectorNegativo().getSigno());
                }
                if(verificarSiEstaEnTerminalPositivo(mouseX,mouseY,bateria)){
                    inicio.setSigno(bateria.getConectorPositivo().getSigno());
                    inicio.setFill(bateria.conectorPositivo.getFill());
                    fin.setFill(bateria.conectorPositivo.getFill());
                    line.setStroke(bateria.conectorPositivo.getFillColor());
                    fin.setSigno(bateria.getConectorPositivo().getSigno());
                }
            }

            /*if(inicio !=null){
                if(bateria.conectorPositivo.getBoundsInParent().intersects(inicio.getBoundsInParent())){
                    inicio.setFill(bateria.conectorPositivo.getFill());
                    fin.setFill(bateria.conectorPositivo.getFill());
                    line.setStroke(bateria.conectorPositivo.getFillColor());
                } else if (bateria.conectorNegativo.getBoundsInParent().intersects(inicio.getBoundsInParent())){
                    inicio.setFill(bateria.conectorNegativo.getFill());
                    fin.setFill(bateria.conectorNegativo.getFill());
                    line.setStroke(bateria.conectorNegativo.getFillColor());
                }else{
                    System.out.println("no se pudo :p");}
            }*/
        });

        this.getChildren().addAll(line, inicio, fin);
    }

    private void setSignoColor(int row, int col, int signo) {
       //pinta el cable del color del signo, si es -1 es azul (negativo) si es 1 es rojo (positivo)
        inicio.setSigno(signo);
        fin.setSigno(signo);
        if(inicio.getSigno() ==-1){
            inicio.setFill(Color.BLUE);
            fin.setFill(Color.BLUE);
            line.setStroke(Color.BLUE);
        }else {
            inicio.setFill(Color.RED);
            fin.setFill(Color.RED);
            line.setStroke(Color.RED);
        }
    }


    private void arrastrarExtremo(MouseEvent event, boolean esInicio) {
        event.consume();  // Consumir el evento para evitar que se propague

        double offsetX = event.getX();
        double offsetY = event.getY();

        if (esInicio) {
            line.setStartX(offsetX);
            line.setStartY(offsetY);
        } else {
            line.setEndX(offsetX);
            line.setEndY(offsetY);
        }


        actualizarPosiciones();
    }


    private void iniciarMovimientoLinea(MouseEvent event) {
        mouseX = event.getX();
        mouseY = event.getY();
    }

    private void moverLineaCompleta(MouseEvent event) {
        event.consume();

        double offsetX = event.getX() - mouseX;
        double offsetY = event.getY() - mouseY;


        line.setStartX(line.getStartX() + offsetX);
        line.setStartY(line.getStartY() + offsetY);
        line.setEndX(line.getEndX() + offsetX);
        line.setEndY(line.getEndY() + offsetY);


        actualizarPosiciones();

        mouseX = event.getX();
        mouseY = event.getY();
    }

    private void actualizarPosiciones() {
        inicio.setX(line.getStartX() - inicio.getWidth() / 2);
        inicio.setY(line.getStartY() - inicio.getHeight() / 2);

        fin.setX(line.getEndX() - fin.getWidth() / 2);
        fin.setY(line.getEndY() - fin.getHeight() / 2);
    }

    public Prototipo_Protoboard getProtoboard() {
        return protoboard;
    }

    public void setProtoboard(Prototipo_Protoboard protoboard) {
        this.protoboard = protoboard;
    }

    public Swich getBoton() {
        return boton;
    }

    public void setBoton(Swich boton) {
        this.boton = boton;
    }

    public LED getLed() {
        return led;
    }

    public void setLed(LED led) {
        this.led = led;
    }

    public Bateria getBateria() {
        return bateria;
    }

    public void setBateria(Bateria bateria) {
        this.bateria = bateria;
    }

    private boolean verificarSiEstaEnfin1(double mouseX, double mouseY, LED led){
        if(mouseX >=  led.getFin1().getX() &&
                mouseX <= led.getFin1().getX() -5 &&
                mouseY >= led.getFin1().getY() && mouseY <= led.getFin1().getY()-5){
            return true;
        }
        return false;
    }
    private boolean verificarSiEstaEnfin2(double mouseX, double mouseY, LED led){
        if(mouseX >=  led.getFin2().getX() &&
                mouseX <= led.getFin2().getX() -5 &&
                mouseY >= led.getFin2().getY() && mouseY <= led.getFin2().getY()-5){
            return true;
        }
        return false;
    }

    private boolean verificarSiEstaEnTerminalPositivo(double mouseX, double mouseY, Bateria bateria) {
        if(mouseX >=  bateria.getConectorPositivo().getX() &&
                mouseX <= bateria.getConectorPositivo().getX() +20 &&
        mouseY >= bateria.getConectorPositivo().getY() && mouseY <= bateria.getConectorPositivo().getY()+20){
            return true;
        }
        return false;
    }
    private boolean verificarSiEstaEnTerminalNegativo(double mouseX, double mouseY, Bateria bateria) {
        if(mouseX >=  bateria.getConectorNegativo().getX() &&
                mouseX <= bateria.getConectorNegativo().getX() +20 &&
                mouseY >= bateria.getConectorNegativo().getY() && mouseY <= bateria.getConectorNegativo().getY()+20){
            return true;
        }
        return false;
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
}
