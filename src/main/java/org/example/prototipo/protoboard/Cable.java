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
    private Object componenteInicio;
    private Object componenteFin;


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

        inicio.setOnMouseReleased(event -> {

            double mouseX = event.getSceneX();
            double mouseY = event.getSceneY();

            // Restablecer la celda o bus anterior
            if (inicio.getLugar() == 1) {
                protoboard.getCelda1().alternarColumna(inicio.getCol(), 0);
            } else if (inicio.getLugar() == 2) {
                protoboard.getCelda2().alternarColumna(inicio.getCol(), 0);
            } else if (inicio.getLugar() == 3) {
               // protoboard.getBus2().toggleFilaBus(inicio.getFila(), 0);
            } else if (inicio.getLugar() == 0) {
                //protoboard.getBus1().toggleFilaBus(inicio.getFila(), 0);
            }

            if (protoboard != null) {
                Node arriba = verificarSiEstaEnCelda(mouseX, mouseY, (GridPane) protoboard.getCelda1().getChildren().getFirst());
                Node abajo = verificarSiEstaEnCelda(mouseX, mouseY, (GridPane) protoboard.getCelda2().getChildren().getFirst());
                Node bus_arriba = verificarSiEstaEnCelda(mouseX, mouseY, (GridPane) protoboard.getBus1().getChildren().getFirst());
                Node bus_abajo = verificarSiEstaEnCelda(mouseX, mouseY, (GridPane) protoboard.getBus2().getChildren().getFirst());
                int col = 0;
                int row = 0;

                if (arriba == null && abajo == null && bus_arriba == null && bus_abajo == null) {
                    // No está sobre ninguna celda o bus
                    // 0= bus1, 1= celda1, 2= celda2, 3= bus2
                    verifica_cables_protoboard();

                }
                if (arriba != null) {
                    col = ((GridPane) protoboard.getCelda1().getChildren().getFirst()).getColumnIndex(arriba) - 1;
                    row = ((GridPane) protoboard.getCelda1().getChildren().getFirst()).getRowIndex(arriba);
                    if (inicio.getSigno() != 0) {
                        protoboard.getCelda1().alternarColumna(col, inicio.getSigno());
                    } else {
                        setSignoColor(row, col, protoboard.getCelda1().getSigno(row, col));
                    }
                    inicio.setLugar(1);
                    inicio.setFila(row);
                    inicio.setCol(col);

                    protoboard.addCablesConctados(this);

                }
                if (abajo != null) {
                    col = ((GridPane) protoboard.getCelda2().getChildren().getFirst()).getColumnIndex(abajo) - 1;
                    row = ((GridPane) protoboard.getCelda2().getChildren().getFirst()).getRowIndex(abajo);
                    if (inicio.getSigno() != 0) {
                        protoboard.getCelda2().alternarColumna(col, inicio.getSigno());
                    } else {
                        setSignoColor(row, col, protoboard.getCelda2().getSigno(row, col));
                    }
                    inicio.setLugar(2);
                    inicio.setFila(row);
                    inicio.setCol(col);
                    protoboard.addCablesConctados(this);
                }
                if (bus_abajo != null) {
                    row = ((GridPane) protoboard.getBus2().getChildren().getFirst()).getRowIndex(bus_abajo);
                    col = ((GridPane) protoboard.getBus2().getChildren().getFirst()).getColumnIndex(bus_abajo) - 1;
                    if (inicio.getSigno() != 0) {
                        protoboard.getBus2().toggleFilaBus(row, inicio.getSigno());
                    } else {
                        setSignoColor(row, col, protoboard.getBus2().getSigno(row, col));
                    }
                    inicio.setLugar(3);
                    inicio.setFila(row);
                    inicio.setCol(col);
                    protoboard.addCablesConctados(this);
                }
                if (bus_arriba != null) {
                    row = ((GridPane) protoboard.getBus1().getChildren().getFirst()).getRowIndex(bus_arriba);
                    col = ((GridPane) protoboard.getBus1().getChildren().getFirst()).getColumnIndex(bus_arriba) - 1;
                    if (inicio.getSigno() != 0) {
                        protoboard.getBus1().toggleFilaBus(row, inicio.getSigno());
                    } else {
                        setSignoColor(row, col, protoboard.getBus1().getSigno(row, col));
                    }
                    inicio.setLugar(0);
                    inicio.setFila(row);
                    inicio.setCol(col);
                    protoboard.addCablesConctados(this);
                }
            }

            if (bateria != null) {
                if (verificarSiEstaEnTerminalNegativo(mouseX, mouseY, bateria)) {
                    inicio.setSigno(bateria.getConectorNegativo().getSigno());
                    fin.setSigno(bateria.getConectorNegativo().getSigno());
                    actualizarColorDesdeTerminal(bateria.getConectorNegativo());

                    bateria.conectarCableNegativo(this);

                    componenteInicio = bateria;
                }
                if (verificarSiEstaEnTerminalPositivo(mouseX, mouseY, bateria)) {
                    inicio.setSigno(bateria.getConectorPositivo().getSigno());
                    fin.setSigno(bateria.getConectorPositivo().getSigno());
                    actualizarColorDesdeTerminal(bateria.getConectorPositivo());

                    bateria.conectarCablePositivo(this);

                    componenteInicio = bateria;
                }
            }
        });

        inicio.setOnMouseDragged(event -> {
            arrastrarExtremo(event, true);

            double mouseX = event.getSceneX();
            double mouseY = event.getSceneY();

            // Si el cable estaba conectado a la batería y ahora no lo está, eliminarlo de la lista
            if (bateria != null && !verificarSiEstaEnTerminalNegativo(mouseX, mouseY, bateria) && !verificarSiEstaEnTerminalPositivo(mouseX, mouseY, bateria)) {
                bateria.desconectarCableNegativo(this);
                bateria.desconectarCablePositivo(this);
            }
        });

        fin.setOnMouseReleased(event -> {

            double mouseX = event.getSceneX();
            double mouseY = event.getSceneY();

            // Restablecer la celda o bus anterior
            if (fin.getLugar() == 1) {
                protoboard.getCelda1().alternarColumna(fin.getCol(), 0);
            } else if (fin.getLugar() == 2) {
                protoboard.getCelda2().alternarColumna(fin.getCol(), 0);
            } else if (fin.getLugar() == 3) {
               // protoboard.getBus2().toggleFilaBus(fin.getFila(), 0);
            } else if (fin.getLugar() == 0) {
               // protoboard.getBus1().toggleFilaBus(fin.getFila(), 0);
            }

            if (protoboard != null) {
                Node arriba = verificarSiEstaEnCelda(mouseX, mouseY, (GridPane) protoboard.getCelda1().getChildren().getFirst());
                Node abajo = verificarSiEstaEnCelda(mouseX, mouseY, (GridPane) protoboard.getCelda2().getChildren().getFirst());
                Node bus_arriba = verificarSiEstaEnCelda(mouseX, mouseY, (GridPane) protoboard.getBus1().getChildren().getFirst());
                Node bus_abajo = verificarSiEstaEnCelda(mouseX, mouseY, (GridPane) protoboard.getBus2().getChildren().getFirst());
                int col = 0;
                int row = 0;

                if (arriba == null && abajo == null && bus_arriba == null && bus_abajo == null) {
                    // No está sobre ninguna celda o bus
                     verifica_cables_protoboard();
                }
                if (arriba != null) {
                    col = ((GridPane) protoboard.getCelda1().getChildren().getFirst()).getColumnIndex(arriba) - 1;
                    row = ((GridPane) protoboard.getCelda1().getChildren().getFirst()).getRowIndex(arriba);

                    if (fin.getSigno() != 0) {
                        protoboard.getCelda1().alternarColumna(col, fin.getSigno());
                    } else {
                        setSignoColor(row, col, protoboard.getCelda1().getSigno(row, col));
                    }
                    fin.setLugar(1);
                    fin.setFila(row);
                    fin.setCol(col);
                    protoboard.addCablesConctados(this);
                }
                if (abajo != null) {
                    col = ((GridPane) protoboard.getCelda2().getChildren().getFirst()).getColumnIndex(abajo) - 1;
                    row = ((GridPane) protoboard.getCelda2().getChildren().getFirst()).getRowIndex(abajo);
                    if (fin.getSigno() != 0) {
                        protoboard.getCelda2().alternarColumna(col, fin.getSigno());
                    } else {
                        setSignoColor(row, col, protoboard.getCelda2().getSigno(row, col));
                    }
                    fin.setLugar(2);
                    fin.setFila(row);
                    fin.setCol(col);
                    protoboard.addCablesConctados(this);

                }
                if (bus_abajo != null) {
                    row = ((GridPane) protoboard.getBus2().getChildren().getFirst()).getRowIndex(bus_abajo);
                    col = ((GridPane) protoboard.getBus2().getChildren().getFirst()).getColumnIndex(bus_abajo) - 1;

                    if (fin.getSigno() != 0) {
                        protoboard.getBus2().toggleFilaBus(row, fin.getSigno());
                    } else {
                        setSignoColor(row, col, protoboard.getBus2().getSigno(row, col));
                    }
                    fin.setLugar(3);
                    fin.setFila(row);
                    fin.setCol(col);
                    protoboard.addCablesConctados(this);
                }
                if (bus_arriba != null) {
                    row = ((GridPane) protoboard.getBus1().getChildren().getFirst()).getRowIndex(bus_arriba);
                    col = ((GridPane) protoboard.getBus1().getChildren().getFirst()).getColumnIndex(bus_arriba) - 1;

                    if (fin.getSigno() != 0) {
                        protoboard.getBus1().toggleFilaBus(row, fin.getSigno());
                    } else {
                        setSignoColor(row, col, protoboard.getBus1().getSigno(row, col));
                    }
                    fin.setLugar(0);
                    fin.setFila(row);
                    fin.setCol(col);
                    protoboard.addCablesConctados(this);
                }
            }

            if (bateria != null) {
                if (verificarSiEstaEnTerminalNegativo(mouseX, mouseY, bateria)) {
                    inicio.setSigno(bateria.getConectorNegativo().getSigno());
                    fin.setSigno(bateria.getConectorNegativo().getSigno());
                    actualizarColorDesdeTerminal(bateria.getConectorNegativo());

                    // Registrar el cable en la batería
                    bateria.conectarCableNegativo(this);
                }
                if (verificarSiEstaEnTerminalPositivo(mouseX, mouseY, bateria)) {
                    inicio.setSigno(bateria.getConectorPositivo().getSigno());
                    fin.setSigno(bateria.getConectorPositivo().getSigno());
                    actualizarColorDesdeTerminal(bateria.getConectorPositivo());

                    // Registrar el cable en la batería
                    bateria.conectarCablePositivo(this);
                }
            }
        });

        fin.setOnMouseDragged(event -> {
            arrastrarExtremo(event, false);

            double mouseX = event.getSceneX();
            double mouseY = event.getSceneY();

            // Si el cable estaba conectado a la batería y ahora no lo está, eliminarlo de la lista
            if (bateria != null && !verificarSiEstaEnTerminalNegativo(mouseX, mouseY, bateria) && !verificarSiEstaEnTerminalPositivo(mouseX, mouseY, bateria)) {
                bateria.desconectarCableNegativo(this);
                bateria.desconectarCablePositivo(this);
            }
        });

        this.getChildren().addAll(line, inicio, fin);
    }

    private void verifica_cables_protoboard() {

        if(inicio.getLugar() == 0) {
            //protoboard.getBus1().toggleFilaBus(inicio.getFila(), 0); NO SE si tiene bateria conectada
            setSignoColor(inicio.getFila(), inicio.getCol(), 0);

        }
        if(inicio.getLugar() == 1) {
            protoboard.getCelda1().alternarColumna(inicio.getCol(), 0);
            setSignoColor(inicio.getFila(), inicio.getCol(), 0);
            valida_cables(1, protoboard.getCelda1(), this);
        }
        if(inicio.getLugar() == 2) {
            protoboard.getCelda2().alternarColumna(inicio.getCol(), 0);
            setSignoColor(inicio.getFila(), inicio.getCol(), 0);
            valida_cables(2, protoboard.getCelda2(), this);
        }
        if(inicio.getLugar() == 3) {
            //protoboard.getBus1().toggleFilaBus(inicio.getFila(), 0); NO SE si tiene bateria conectada
            setSignoColor(inicio.getFila(), inicio.getCol(), 0);

        }
        if(fin.getLugar() == 0){
            //protoboard.getBus1().toggleFilaBus(fin.getFila(), 0);
            setSignoColor(fin.getFila(), fin.getCol(), 0);
        }
        if(fin.getLugar() == 1){
            protoboard.getCelda1().alternarColumna(fin.getCol(), 0);
            setSignoColor(fin.getFila(), fin.getCol(), 0);

            valida_cables(1, protoboard.getCelda1(), this);

        }
        if(fin.getLugar() == 2){
            protoboard.getCelda2().alternarColumna(fin.getCol(), 0);
            setSignoColor(fin.getFila(), fin.getCol(), 0);
            valida_cables(2, protoboard.getCelda2(), this);
        }
        if(fin.getLugar() == 3){
            //protoboard.getBus1().toggleFilaBus(fin.getFila(), 0);
            setSignoColor(fin.getFila(), fin.getCol(), 0);
        }
    }

    private void valida_cables(int lugar, Celdas celda, Cable c) {
        for (int i=0; i < protoboard.getCablesConctados().size(); i++){
            if(!protoboard.getCablesConctados().get(i).equals(c) &&
                    (protoboard.getCablesConctados().get(i).getInicio().getLugar() == lugar || protoboard.getCablesConctados().get(i).getFin().getLugar() == lugar)
                    && (protoboard.getCablesConctados().get(i).getInicio().getCol() == inicio.getCol() || protoboard.getCablesConctados().get(i).getFin().getCol() == fin.getCol()) ){
                if (protoboard.getCablesConctados().get(i).getInicio().getLugar() == lugar){
                    celda.alternarColumna(protoboard.getCablesConctados().get(i).getInicio().getCol(), 0);
                    protoboard.getCablesConctados().get(i).setSignoColor(fin.getFila(), fin.getCol(), 0);
                }
                if (protoboard.getCablesConctados().get(i).getFin().getLugar() == lugar){
                    celda.alternarColumna(protoboard.getCablesConctados().get(i).getFin().getCol(), 0);
                    protoboard.getCablesConctados().get(i).setSignoColor(fin.getFila(), fin.getCol(), 0);
                }

            }

        }
    }

    public void actualizarColorDesdeTerminal(Cuadrados terminal) {
        // Actualizar el signo y el color del cable
        inicio.setSigno(terminal.getSigno());
        fin.setSigno(terminal.getSigno());

        Color colorCable = Color.BLACK; // Color por defecto

        if (terminal.getSigno() == -1) {
            colorCable = Color.BLUE;
        } else if (terminal.getSigno() == 1) {
            colorCable = Color.RED;
        }

        inicio.setFill(colorCable);
        fin.setFill(colorCable);
        line.setStroke(colorCable);
    }

    private void setSignoColor(int row, int col, int signo) {
        // Pinta el cable del color del signo, si es -1 es azul (negativo) si es 1 es rojo (positivo)
        inicio.setSigno(signo);
        fin.setSigno(signo);
        if (inicio.getSigno() == -1) {
            inicio.setFill(Color.BLUE);
            fin.setFill(Color.BLUE);
            line.setStroke(Color.BLUE);
        } else if (inicio.getSigno() == 1) {
            inicio.setFill(Color.RED);
            fin.setFill(Color.RED);
            line.setStroke(Color.RED);
        } else if (inicio.getSigno() == 0) {
            line.setStroke(Color.BLACK);
            inicio.setFill(Color.BLACK);
            fin.setFill(Color.BLACK);
        } else if (inicio.getSigno() == 2) {
            line.setStroke(Color.OLIVE);
            inicio.setFill(Color.OLIVE);
            fin.setFill(Color.OLIVE);
        }
    }

    public void signoBateria( int signo, Cuadrados a, Cuadrados b, Line l) {
        // Pinta el cable del color del signo, si es -1 es azul (negativo) si es 1 es rojo (positivo)

        if (signo == -1) {
            a.setFill(Color.BLUE);
            b.setFill(Color.BLUE);
            l.setStroke(Color.BLUE);
        } else if (signo == 1) {
            a.setFill(Color.RED);
            b.setFill(Color.RED);
            l.setStroke(Color.RED);
        } else if (signo == 0) {
            l.setStroke(Color.BLACK);
            a.setFill(Color.BLACK);
            b.setFill(Color.BLACK);
        } else if (signo == 2) {
            l.setStroke(Color.OLIVE);
            a.setFill(Color.OLIVE);
            b.setFill(Color.OLIVE);
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


    public Cuadrados getInicio() {
        return inicio;
    }

    public void setInicio(Cuadrados inicio) {
        this.inicio = inicio;
    }

    public Cuadrados getFin() {
        return fin;
    }

    public void setFin(Cuadrados fin) {
        this.fin = fin;
    }



    private boolean verificarSiEstaEnTerminalPositivo(double mouseX, double mouseY, Bateria bateria) {
        if (mouseX >= bateria.getConectorPositivo().getX() &&
                mouseX <= bateria.getConectorPositivo().getX() + 20 &&
                mouseY >= bateria.getConectorPositivo().getY() && mouseY <= bateria.getConectorPositivo().getY() + 20) {
            return true;
        }
        return false;
    }

    private boolean verificarSiEstaEnTerminalNegativo(double mouseX, double mouseY, Bateria bateria) {
        if (mouseX >= bateria.getConectorNegativo().getX() &&
                mouseX <= bateria.getConectorNegativo().getX() + 20 &&
                mouseY >= bateria.getConectorNegativo().getY() && mouseY <= bateria.getConectorNegativo().getY() + 20) {
            return true;
        }
        return false;
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
}
