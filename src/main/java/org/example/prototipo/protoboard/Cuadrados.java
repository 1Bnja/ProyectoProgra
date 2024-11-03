package org.example.prototipo.protoboard;

import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class Cuadrados extends Rectangle {
    private int tamanio;
    private boolean tipocarga = false;
    private int signo = 0;
    int lugar; // 0 = bus1, 1 = celda1, 2 = celda2, 3 = bus2
    int col, fila;
    private double voltaje= 0;
    private int ancho, alto;
    private Color color;
    private double posX, posY;

    // Constructor de la clase Cuadrados
    public Cuadrados(int tamanio, int espacio) {
        this.tamanio = tamanio;
        this.signo = 0;

        // Establecer las dimensiones del cuadrado
        this.setWidth(tamanio);
        this.setHeight(tamanio);

        // Establecer el color de relleno
        this.setFill(Color.WHITE);

        // Establecer el color del borde
        this.setStroke(Color.BLACK);  // Color de la línea del borde
    }

    public Cuadrados(int ancho, int alto, double posX, double posY, Color color) {
        super(ancho, alto);
        setX(posX);
        setY(posY);
        setFill(color);
        setStroke(Color.BLACK);
    }

    // Getter para obtener el tamaño
    public int getTamanio() {
        return tamanio;
    }

    // Setter para establecer el tamaño
    public void setTamanio(int tamanio) {
        this.tamanio = tamanio;
        this.setWidth(tamanio);
        this.setHeight(tamanio);
    }

    // Método para establecer la altura
    public void setheidht(int heidht){
        this.setHeight(heidht);
    }

    // Getter para verificar el tipo de carga
    public boolean isTipocarga() {
        return tipocarga;
    }

    // Setter para establecer el tipo de carga
    public void setTipoCarga(boolean tipocarga) {
        this.tipocarga = tipocarga;
    }

    // Getter para obtener el color de relleno
    public Color getFillColor() {
        return (Color) this.getFill();
    }

    // Setter para establecer el color de relleno
    public void setFillColor(Color color) {
        this.setFill(color);
    }

    // Getter para obtener el signo
    public int getSigno() {
        return this.signo;
    }

    // Setter para establecer el signo
    public void setSigno(int signo) {
        this.signo = signo;
    }

    // Getter para obtener el voltaje
    public double getVoltaje() {
        return this.voltaje;
    }

    // Setter para establecer el voltaje
    public void setVoltaje(double voltaje) {
        this.voltaje = voltaje;
    }

    // Getter para obtener el lugar
    public int getLugar() {
        return this.lugar;
    }

    // Setter para establecer el lugar
    public void setLugar(int lugar) {
        this.lugar = lugar;
    }

    // Getter para obtener la columna
    public int getCol() {
        return this.col;
    }

    // Setter para establecer la columna
    public void setCol(int col) {
        this.col = col;
    }

    // Getter para obtener la fila
    public int getFila() {
        return this.fila;
    }

    // Setter para establecer la fila
    public void setFila(int fila) {
        this.fila = fila;
    }
}
