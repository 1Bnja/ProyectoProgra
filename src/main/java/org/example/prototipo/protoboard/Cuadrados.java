package org.example.prototipo.protoboard;

import javafx.scene.Node;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

import java.sql.SQLOutput;
import java.util.Arrays;

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
    private Node celdaConectada;
    private int previoSignoEntrada1 = 0;
    private int previoSignoEntrada2 = 0;
    private int[] arreglo= new int[4];

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

    public int getPrevioSignoEntrada1() {
        return previoSignoEntrada1;
    }

    public void setPrevioSignoEntrada1(int previoSignoEntrada1) {
        this.previoSignoEntrada1 = previoSignoEntrada1;
    }

    public int getPrevioSignoEntrada2() {
        return previoSignoEntrada2;
    }

    public void setPrevioSignoEntrada2(int previoSignoEntrada2) {
        this.previoSignoEntrada2 = previoSignoEntrada2;
    }

    public void actualizarSigno(int nuevoSigno, int entrada1, int entrada2) {
        this.previoSignoEntrada1 = entrada1;
        this.previoSignoEntrada2 = entrada2;
        this.signo = nuevoSigno;
    }

    public void actualizarSigno2(int nuevoSigno, int entrada1) {
        this.previoSignoEntrada1 = entrada1;
        this.signo = nuevoSigno;
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

    public void setCeldaConectada(Node celdaConectada) {
        this.celdaConectada = celdaConectada;
    }

    public Node getCeldaConectada() {
        return celdaConectada;
    }

    public void setArreglo(int indice, int valor) {
        if (indice >= 0 && indice < arreglo.length) {
            arreglo[indice] = valor;
        }
    }

    public int getArregloEspecifico(int indice) {
        if (indice >= 0 && indice < arreglo.length) {
            return arreglo[indice];
        }else{
        return -1;
    }}

    public int[] getArreglo() {
        return arreglo;
    }

    public void printArreglo(){
        System.out.println(Arrays.toString(arreglo));

    }
}


