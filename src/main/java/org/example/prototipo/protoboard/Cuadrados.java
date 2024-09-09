package org.example.prototipo.protoboard;

import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class Cuadrados extends Rectangle {
    private int tamanio;
    private boolean tipocarga = false;


    public Cuadrados(int tamanio, int espacio) {
        this.tamanio = tamanio;


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
}
