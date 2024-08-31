package org.example.prototipo.protoboard;

import javafx.scene.shape.Rectangle;

public class Cuadrados extends Rectangle {
    private int tamanio;

    public Cuadrados(int tamanio, int espacio) {
        this.tamanio = tamanio;

        this.setWidth(tamanio);
        this.setHeight(tamanio);
    }

    public int getTamanio() {
        return tamanio;
    }

    public void setTamanio(int tamanio) {
        this.tamanio = tamanio;
        this.setWidth(tamanio);
    }
}
