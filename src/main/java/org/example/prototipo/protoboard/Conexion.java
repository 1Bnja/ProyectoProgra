package org.example.prototipo.protoboard;

import javafx.scene.Node;

public class Conexion {
    int a_positivo = 0;
    int b_positivo = 0;
    int a_negativo = 0;
    int b_negativo = 0;
    boolean is_cable = false;

    Node a;
    Node b;

    public Conexion(int a_positivo, int b_positivo, int a_negativo, int b_negativo, Node a, boolean is_cable) {
        this.a_positivo = a_positivo;
        this.b_positivo = b_positivo;
        this.a_negativo = a_negativo;
        this.b_negativo = b_negativo;
        this.a = a;
        this.b = null;
        this.is_cable = is_cable;
    }

    public int getA_positivo() {
        return a_positivo;
    }

    public void setA_positivo(int a_positivo) {
        this.a_positivo = a_positivo;
    }

    public int getB_positivo() {
        return b_positivo;
    }

    public void setB_positivo(int b_positivo) {
        this.b_positivo = b_positivo;
    }

    public int getA_negativo() {
        return a_negativo;
    }

    public void setA_negativo(int a_negativo) {
        this.a_negativo = a_negativo;
    }

    public int getB_negativo() {
        return b_negativo;
    }

    public void setB_negativo(int b_negativo) {
        this.b_negativo = b_negativo;
    }

    public boolean isIs_cable() {
        return is_cable;
    }

    public void setIs_cable(boolean is_cable) {
        this.is_cable = is_cable;
    }

    public Node getA() {
        return a;
    }

    public void setA(Node a) {
        this.a = a;
    }

    public Node getB() {
        return b;
    }

    public void setB(Node b) {
        this.b = b;
    }
}
