package org.example.prototipo.protoboard;

import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class Cuadrados extends Rectangle {
    private int tamanio;
    private boolean tipocarga = false;
    private int signo = 0;
    int lugar; // 0= bus1, 1= celda1, 2= celda2, 3= bus2
    int col, fila;

    public Cuadrados(int tamanio, int espacio) {
        this.tamanio = tamanio;
        this.signo = 0;

        this.setWidth(tamanio);
        this.setHeight(tamanio);


        this.setFill(Color.WHITE);


        this.setStroke(Color.BLACK);  // Color de la línea del borde
    }


    public int getTamanio() {
        return tamanio;
    }


    public void setTamanio(int tamanio) {
        this.tamanio = tamanio;
        this.setWidth(tamanio);
        this.setHeight(tamanio);
    }

    public void setheidht(int heidht){
        this.setHeight(heidht);
    }

    public boolean isTipocarga() {
        return tipocarga;
    }


    public void setTipoCarga(boolean tipocarga) {
        this.tipocarga = tipocarga;
    }


    public Color getFillColor() {
        return (Color) this.getFill();
    }


    public void setFillColor(Color color) {
        this.setFill(color);
    }

    public int getSigno() {
        return this.signo;
    }

    public void setSigno(int signo) {
        this.signo = signo;
    }

    public int getLugar() {
        return this.lugar;
    }
    public void setLugar(int lugar) {
        this.lugar = lugar;
    }
    public int getCol() {
        return this.col;
    }
    public void setCol(int col) {
        this.col = col;
    }
    public int getFila() {
        return this.fila;
    }
    public void setFila(int fila) {
        this.fila = fila;
    }

}
