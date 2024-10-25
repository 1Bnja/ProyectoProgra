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

        // Crear la línea que representa el cable
        line = new Line(startX, startY, endX, endY);
        line.setStroke(Color.GREENYELLOW);
        line.setStrokeWidth(7);

        // Crear los extremos del cable utilizando la clase Cuadrados
        inicio = new Cuadrados(12, 2);
        fin = new Cuadrados(12, 2);
        inicio.setFillColor(Color.ORANGE);
        inicio.setSigno(0);
        fin.setSigno(0);
        fin.setFillColor(Color.ORANGE);

        // Establecer las posiciones iniciales de los extremos
        inicio.setX(startX - inicio.getWidth() / 2);
        inicio.setY(startY - inicio.getHeight() / 2);
        fin.setX(endX - fin.getWidth() / 2);
        fin.setY(endY - fin.getHeight() / 2);

        this.setPickOnBounds(false);

        // Configurar eventos de arrastre para los extremos del cable
        inicio.setOnMouseDragged(event -> arrastrarExtremo(event, true));
        fin.setOnMouseDragged(event -> arrastrarExtremo(event, false));

        // Configurar eventos para mover toda la línea del cable
        line.setOnMousePressed(this::iniciarMovimientoLinea);
        line.setOnMouseDragged(this::moverLineaCompleta);

        // Cuando se suelta el extremo inicial, se verifica si está conectado a una celda o bus
        inicio.setOnMouseReleased(event -> {

            double mouseX = event.getSceneX();
            double mouseY = event.getSceneY();

            // Restablecer la celda o bus anterior
            if (inicio.getLugar() == 1) {
                protoboard.getCelda1().alternarColumna(inicio.getCol(), 0,inicio.getVoltaje());
            } else if (inicio.getLugar() == 2) {
                protoboard.getCelda2().alternarColumna(inicio.getCol(), 0,inicio.getVoltaje());
            } else if (inicio.getLugar() == 3) {
                // protoboard.getBus2().toggleFilaBus(inicio.getFila(), 0);
            } else if (inicio.getLugar() == 0) {
                // protoboard.getBus1().toggleFilaBus(inicio.getFila(), 0);
            }

            // Verifica si el extremo inicial del cable está sobre una celda o bus
            if (protoboard != null) {
                Node arriba = verificarSiEstaEnCelda(mouseX, mouseY, (GridPane) protoboard.getCelda1().getChildren().getFirst());
                Node abajo = verificarSiEstaEnCelda(mouseX, mouseY, (GridPane) protoboard.getCelda2().getChildren().getFirst());
                Node bus_arriba = verificarSiEstaEnCelda(mouseX, mouseY, (GridPane) protoboard.getBus1().getChildren().getFirst());
                Node bus_abajo = verificarSiEstaEnCelda(mouseX, mouseY, (GridPane) protoboard.getBus2().getChildren().getFirst());
                int col = 0;
                int row = 0;

                if (arriba == null && abajo == null && bus_arriba == null && bus_abajo == null) {
                    // No está sobre ninguna celda o bus
                    // 0 = bus1, 1 = celda1, 2 = celda2, 3 = bus2
                    verifica_cables_protoboard();
                }

                // Si el cable está sobre una celda o bus, se conecta
                if (arriba != null) {
                    col = ((GridPane) protoboard.getCelda1().getChildren().getFirst()).getColumnIndex(arriba) - 1;
                    row = ((GridPane) protoboard.getCelda1().getChildren().getFirst()).getRowIndex(arriba);
                    if (inicio.getSigno() != 0) {
                        protoboard.getCelda1().alternarColumna(col, inicio.getSigno(),inicio.getVoltaje());
                    } else {
                        setSignoColor(row, col, protoboard.getCelda1().getSigno(row, col),protoboard.getCelda1().getVoltaje(row, col));
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
                        protoboard.getCelda2().alternarColumna(col, inicio.getSigno(),inicio.getVoltaje());
                    } else {
                        setSignoColor(row, col, protoboard.getCelda2().getSigno(row, col),protoboard.getCelda2().getVoltaje(row, col));
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
                        protoboard.getBus2().toggleFilaBus(row, inicio.getSigno(), inicio.getVoltaje());
                    } else {
                        setSignoColor(row, col, protoboard.getBus2().getSigno(row, col),protoboard.getBus2().getVoltaje(row, col));
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
                        protoboard.getBus1().toggleFilaBus(row, inicio.getSigno(),inicio.getVoltaje());
                    } else {
                        setSignoColor(row, col, protoboard.getBus1().getSigno(row, col),protoboard.getBus1().getVoltaje(row, col));
                    }
                    inicio.setLugar(0);
                    inicio.setFila(row);
                    inicio.setCol(col);
                    protoboard.addCablesConctados(this);
                }
            }

            // Si está conectado a una batería, actualiza el signo y color del cable
            if (bateria != null) {
                if (verificarSiEstaEnTerminalNegativo(mouseX, mouseY, bateria)) {
                    inicio.setSigno(bateria.getConectorNegativo().getSigno());
                    inicio.setVoltaje(bateria.getConectorNegativo().getVoltaje());
                    fin.setSigno(bateria.getConectorNegativo().getSigno());
                    fin.setVoltaje(bateria.getConectorNegativo().getVoltaje());
                    actualizarColorDesdeTerminal(bateria.getConectorNegativo());

                    bateria.conectarCableNegativo(this);

                    componenteInicio = bateria;
                }
                if (verificarSiEstaEnTerminalPositivo(mouseX, mouseY, bateria)) {
                    inicio.setSigno(bateria.getConectorPositivo().getSigno());
                    inicio.setVoltaje(bateria.getConectorPositivo().getVoltaje());
                    fin.setSigno(bateria.getConectorPositivo().getSigno());
                    fin.setVoltaje(bateria.getConectorPositivo().getVoltaje());
                    actualizarColorDesdeTerminal(bateria.getConectorPositivo());

                    bateria.conectarCablePositivo(this);

                    componenteInicio = bateria;
                }
            }
        });

        // Evento al arrastrar el extremo inicial del cable
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

        // Cuando se suelta el extremo final, se verifica si está conectado a una celda o bus
        fin.setOnMouseReleased(event -> {

            double mouseX = event.getSceneX();
            double mouseY = event.getSceneY();

            // Restablecer la celda o bus anterior
            if (fin.getLugar() == 1) {
                protoboard.getCelda1().alternarColumna(fin.getCol(), 0,0);
            } else if (fin.getLugar() == 2) {
                protoboard.getCelda2().alternarColumna(fin.getCol(), 0,0);
            } else if (fin.getLugar() == 3) {
                // protoboard.getBus2().toggleFilaBus(fin.getFila(), 0);
            } else if (fin.getLugar() == 0) {
                // protoboard.getBus1().toggleFilaBus(fin.getFila(), 0);
            }

            // Verifica si el extremo final del cable está sobre una celda o bus
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
                        protoboard.getCelda1().alternarColumna(col, fin.getSigno(),fin.getVoltaje());
                    } else {
                        setSignoColor(row, col, protoboard.getCelda1().getSigno(row, col),protoboard.getCelda1().getVoltaje(row, col));
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
                        protoboard.getCelda2().alternarColumna(col, fin.getSigno(),fin.getVoltaje());
                    } else {
                        setSignoColor(row, col, protoboard.getCelda2().getSigno(row, col),protoboard.getCelda2().getVoltaje(row, col));
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
                        protoboard.getBus2().toggleFilaBus(row, fin.getSigno(),fin.getVoltaje());
                    } else {
                        setSignoColor(row, col, protoboard.getBus2().getSigno(row, col),protoboard.getBus2().getVoltaje(row, col));
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
                        protoboard.getBus1().toggleFilaBus(row, fin.getSigno(),fin.getVoltaje());
                    } else {
                        setSignoColor(row, col, protoboard.getBus1().getSigno(row, col),protoboard.getBus1().getVoltaje(row, col));
                    }
                    fin.setLugar(0);
                    fin.setFila(row);
                    fin.setCol(col);
                    protoboard.addCablesConctados(this);
                }
            }

            // Si está conectado a una batería, actualiza el signo y color del cable
            if (bateria != null) {
                if (verificarSiEstaEnTerminalNegativo(mouseX, mouseY, bateria)) {
                    inicio.setSigno(bateria.getConectorNegativo().getSigno());
                    inicio.setVoltaje(bateria.getConectorNegativo().getVoltaje());
                    fin.setSigno(bateria.getConectorNegativo().getSigno());
                    fin.setVoltaje(bateria.getConectorNegativo().getVoltaje());
                    actualizarColorDesdeTerminal(bateria.getConectorNegativo());

                    // Registrar el cable en la batería
                    bateria.conectarCableNegativo(this);
                }
                if (verificarSiEstaEnTerminalPositivo(mouseX, mouseY, bateria)) {
                    inicio.setSigno(bateria.getConectorPositivo().getSigno());
                    inicio.setVoltaje(bateria.getConectorPositivo().getVoltaje());
                    fin.setSigno(bateria.getConectorPositivo().getSigno());
                    fin.setVoltaje(bateria.getConectorPositivo().getVoltaje());
                    actualizarColorDesdeTerminal(bateria.getConectorPositivo());
                    // Registrar el cable en la batería
                    bateria.conectarCablePositivo(this);
                }
            }
        });

        // Evento al arrastrar el extremo final del cable
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

        // Añadir los elementos gráficos al pane
        this.getChildren().addAll(line, inicio, fin);
    }

    // Método para verificar y actualizar el estado de los cables conectados en la protoboard
    private void verifica_cables_protoboard() {

        if (inicio.getLugar() == 0) {
            // protoboard.getBus1().toggleFilaBus(inicio.getFila(), 0);  // No sé si tiene batería conectada
            setSignoColor(inicio.getFila(), inicio.getCol(), 0,0);

        }
        if (inicio.getLugar() == 1) {
            protoboard.getCelda1().alternarColumna(inicio.getCol(), 0,0);
            setSignoColor(inicio.getFila(), inicio.getCol(), 0,0);
            valida_cables(1, protoboard.getCelda1(), this);
        }
        if (inicio.getLugar() == 2) {
            protoboard.getCelda2().alternarColumna(inicio.getCol(), 0,0);
            setSignoColor(inicio.getFila(), inicio.getCol(), 0,0);
            valida_cables(2, protoboard.getCelda2(), this);
        }
        if (inicio.getLugar() == 3) {
            // protoboard.getBus1().toggleFilaBus(inicio.getFila(), 0);  // No sé si tiene batería conectada
            setSignoColor(inicio.getFila(), inicio.getCol(), 0,0);

        }
        if (fin.getLugar() == 0) {
            // protoboard.getBus1().toggleFilaBus(fin.getFila(), 0);
            setSignoColor(fin.getFila(), fin.getCol(), 0,0);
        }
        if (fin.getLugar() == 1) {
            protoboard.getCelda1().alternarColumna(fin.getCol(), 0,0);
            setSignoColor(fin.getFila(), fin.getCol(), 0,0);

            valida_cables(1, protoboard.getCelda1(), this);

        }
        if (fin.getLugar() == 2) {
            protoboard.getCelda2().alternarColumna(fin.getCol(), 0,0);
            setSignoColor(fin.getFila(), fin.getCol(), 0,0);
            valida_cables(2, protoboard.getCelda2(), this);
        }
        if (fin.getLugar() == 3) {
            // protoboard.getBus1().toggleFilaBus(fin.getFila(), 0);
            setSignoColor(fin.getFila(), fin.getCol(), 0,0);
        }
    }

    // Método para validar cables conectados en una celda específica y actualizar sus signos y colores
    private void valida_cables(int lugar, Celdas celda, Cable c) {
        for (int i = 0; i < protoboard.getCablesConctados().size(); i++) {
            if (!protoboard.getCablesConctados().get(i).equals(c) &&
                    (protoboard.getCablesConctados().get(i).getInicio().getLugar() == lugar || protoboard.getCablesConctados().get(i).getFin().getLugar() == lugar)
                    && (protoboard.getCablesConctados().get(i).getInicio().getCol() == inicio.getCol() || protoboard.getCablesConctados().get(i).getFin().getCol() == fin.getCol())) {
                if (protoboard.getCablesConctados().get(i).getInicio().getLugar() == lugar) {
                    celda.alternarColumna(protoboard.getCablesConctados().get(i).getInicio().getCol(), 0,0);
                    protoboard.getCablesConctados().get(i).setSignoColor(fin.getFila(), fin.getCol(), 0,0);
                }
                if (protoboard.getCablesConctados().get(i).getFin().getLugar() == lugar) {
                    celda.alternarColumna(protoboard.getCablesConctados().get(i).getFin().getCol(), 0,0);
                    protoboard.getCablesConctados().get(i).setSignoColor(fin.getFila(), fin.getCol(), 0,0);
                }

            }

        }
    }

    // Método para actualizar el color del cable desde un terminal específico
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

    // Método para establecer el color del cable dependiendo del signo
    private void setSignoColor(int row, int col, int signo,double voltaje) {
        // Pinta el cable del color del signo, si es -1 es azul (negativo), si es 1 es rojo (positivo)
        inicio.setSigno(signo);
        fin.setSigno(signo);
        inicio.setVoltaje(voltaje);
        fin.setVoltaje(voltaje);
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

    // Método para establecer el color del cable según el signo de la batería
    public void signoBateria(int signo, Cuadrados a, Cuadrados b, Line l) {
        // Pinta el cable según el signo de la batería
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

    // Método para manejar el arrastre de un extremo del cable
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

    // Método para iniciar el movimiento de toda la línea del cable
    private void iniciarMovimientoLinea(MouseEvent event) {
        mouseX = event.getX();
        mouseY = event.getY();
    }

    // Método para mover toda la línea del cable
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

    // Método para actualizar las posiciones de los extremos del cable basándose en la línea
    private void actualizarPosiciones() {
        inicio.setX(line.getStartX() - inicio.getWidth() / 2);
        inicio.setY(line.getStartY() - inicio.getHeight() / 2);

        fin.setX(line.getEndX() - fin.getWidth() / 2);
        fin.setY(line.getEndY() - fin.getHeight() / 2);
    }

    // Getters y setters

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

    // Método para verificar si el ratón está sobre el terminal positivo de la batería
    private boolean verificarSiEstaEnTerminalPositivo(double mouseX, double mouseY, Bateria bateria) {
        if (mouseX >= bateria.getConectorPositivo().getX() &&
                mouseX <= bateria.getConectorPositivo().getX() + 20 &&
                mouseY >= bateria.getConectorPositivo().getY() && mouseY <= bateria.getConectorPositivo().getY() + 20) {
            return true;
        }
        return false;
    }

    // Método para verificar si el ratón está sobre el terminal negativo de la batería
    private boolean verificarSiEstaEnTerminalNegativo(double mouseX, double mouseY, Bateria bateria) {
        if (mouseX >= bateria.getConectorNegativo().getX() &&
                mouseX <= bateria.getConectorNegativo().getX() + 20 &&
                mouseY >= bateria.getConectorNegativo().getY() && mouseY <= bateria.getConectorNegativo().getY() + 20) {
            return true;
        }
        return false;
    }

    // Método para verificar si el ratón está sobre una celda en un GridPane
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
