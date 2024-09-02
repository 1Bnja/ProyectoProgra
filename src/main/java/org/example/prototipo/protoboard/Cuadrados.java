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
    }


    public int getTamanio() {
        return tamanio;
    }

    public void setTamanio(int tamanio) {
        this.tamanio = tamanio;
        this.setWidth(tamanio);
    }

    public boolean tipo_carga(boolean carga) {
        this.tipocarga = carga;
        return tipocarga;
    }

    public Color getFillColor() {
        return (Color) this.getFill();
    }

}
