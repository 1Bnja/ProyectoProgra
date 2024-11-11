package org.example.prototipo.protoboard;

import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.scene.Group;
import javafx.scene.shape.Polygon;
import java.util.ArrayList;
import java.util.List;

public class Prototipo_Protoboard extends Pane {

    private Group nodo = new Group();

    // Coordenadas de origen para posicionar el protoboard
    double origenX = Main.origenX;
    double origenY = Main.origenY;

    // Componentes del protoboard
    Celdas celdas1;
    Celdas celdas2;
    BusesAlimentacion bus1;
    BusesAlimentacion bus2;

    // Listas para almacenar LEDs y cables conectados al protoboard
    public List<LED> ledsConectados = new ArrayList<>();
    public static List<Cable> cablesConctados = new ArrayList<>();
    public List<Chip> chipsConectados = new ArrayList<>();
    public static List<Resistencia> resistenciasConectadas = new ArrayList<>();

    private boolean notificarEnProceso = false;

    // Constructor de la clase Prototipo_Protoboard
    public Prototipo_Protoboard() {
        this.setPickOnBounds(false); // Desactiva la detección de eventos fuera de los límites

        // Crear el fondo total del protoboard
        Polygon fondoTotal = crearFondo(origenX - 220, origenY - 280, origenX + 580, origenY + 280, Color.LIGHTGRAY);
        Polygon fondoCarrilCentral = crearFondo(origenX - 220, origenY - 20, origenX + 580, origenY + 20, Color.GRAY);

        // Añadir los fondos al nodo
        nodo.getChildren().addAll(fondoTotal, fondoCarrilCentral);

        // Crear fondos exteriores para darle forma al protoboard
        Polygon fondo1Exterior = crearFondo2(new double[]{
                origenX - 210, origenY - 290,
                origenX + 590, origenY - 290,
                origenX + 580, origenY - 280,
                origenX - 220, origenY - 280
        }, Color.GRAY);

        Polygon fondo2Exterior = crearFondo2(new double[]{
                origenX + 580, origenY - 280,
                origenX + 590, origenY - 290,
                origenX + 590, origenY + 270,
                origenX + 580, origenY + 280
        }, Color.GRAY);

        nodo.getChildren().addAll(fondo1Exterior, fondo2Exterior);

        // Crear las líneas principales del cuadrado del protoboard
        Line lineaArriba = crearLinea(origenX - 220, origenY - 280, origenX + 580, origenY - 280, Color.BLACK);
        Line lineaAbajo = crearLinea(origenX - 220, origenY + 280, origenX + 580, origenY + 280, Color.BLACK);
        Line lineaIzquierda = crearLinea(origenX + 580, origenY - 280, origenX + 580, origenY + 280, Color.BLACK);
        Line lineaDerecha = crearLinea(origenX - 220, origenY - 280, origenX - 220, origenY + 280, Color.BLACK);

        // Crear las líneas internas horizontales
        Line lineaInterna1 = crearLinea(origenX - 220, origenY - 210, origenX + 580, origenY - 210, Color.BLACK);
        Line lineaInterna2 = crearLinea(origenX - 220, origenY - 20, origenX + 580, origenY - 20, Color.BLACK);
        Line lineaInterna3 = crearLinea(origenX - 220, origenY + 20, origenX + 580, origenY + 20, Color.BLACK);
        Line lineaInterna4 = crearLinea(origenX - 220, origenY + 210, origenX + 580, origenY + 210, Color.BLACK);

        nodo.getChildren().addAll(lineaArriba, lineaAbajo, lineaIzquierda, lineaDerecha,
                lineaInterna1, lineaInterna2, lineaInterna3, lineaInterna4);

        // Crear líneas guía de colores
        Line lineaGuia1 = crearLinea(origenX - 170, origenY - 273, origenX + 534, origenY - 273, Color.BLUE);
        Line lineaGuia2 = crearLinea(origenX - 170, origenY - 217, origenX + 534, origenY - 217, Color.RED);
        Line lineaGuia3 = crearLinea(origenX - 170, origenY + 217, origenX + 534, origenY + 217, Color.BLUE);
        Line lineaGuia4 = crearLinea(origenX - 170, origenY + 273, origenX + 534, origenY + 273, Color.RED);

        nodo.getChildren().addAll(lineaGuia1, lineaGuia2, lineaGuia3, lineaGuia4);

        // Crear las celdas superiores e inferiores del protoboard
        celdas1 = new Celdas(origenX - 210, origenY - 190, new char[]{'j', 'i', 'h', 'g', 'f'}, false, this);
        nodo.getChildren().add(celdas1);

        celdas2 = new Celdas(origenX - 210, origenY + 30, new char[]{'e', 'd', 'c', 'b', 'a'}, true, this);
        nodo.getChildren().add(celdas2);

        // Crear los buses de alimentación superior e inferior
        bus1 = new BusesAlimentacion(origenX - 190, origenY - 265, new char[]{'-', '+'});
        nodo.getChildren().add(bus1);

        bus2 = new BusesAlimentacion(origenX - 190, origenY + 225, new char[]{'-', '+'});
        nodo.getChildren().add(bus2);

        // Crear líneas exteriores adicionales para el diseño del protoboard
        Line lineaExterior = crearLinea(origenX - 220, origenY - 280, origenX - 210, origenY - 290, Color.BLACK);
        Line lineaExterior1 = crearLinea(origenX - 210, origenY - 290, origenX + 590, origenY - 290, Color.BLACK);
        Line lineaExterior2 = crearLinea(origenX + 590, origenY - 290, origenX + 590, origenY + 270, Color.BLACK);
        Line lineaExterior3 = crearLinea(origenX + 580, origenY - 280, origenX + 590, origenY - 290, Color.BLACK);
        Line lineaExterior4 = crearLinea(origenX + 580, origenY - 210, origenX + 590, origenY - 220, Color.BLACK);
        Line lineaExterior5 = crearLinea(origenX + 580, origenY - 20, origenX + 590, origenY - 30, Color.BLACK);
        Line lineaExterior6 = crearLinea(origenX + 580, origenY + 20, origenX + 590, origenY + 10, Color.BLACK);
        Line lineaExterior7 = crearLinea(origenX + 580, origenY + 210, origenX + 590, origenY + 200, Color.BLACK);
        Line lineaExterior8 = crearLinea(origenX + 580, origenY + 280, origenX + 590, origenY + 270, Color.BLACK);

        nodo.getChildren().addAll(
                lineaExterior, lineaExterior1, lineaExterior2, lineaExterior3, lineaExterior4,
                lineaExterior5, lineaExterior6, lineaExterior7, lineaExterior8
        );

        // Añadir el nodo completo al pane del protoboard
        this.getChildren().add(nodo);
    }

    // Método para crear una línea entre dos puntos con un color específico
    private Line crearLinea(double startX, double startY, double endX, double endY, Color color) {
        Line linea = new Line(startX, startY, endX, endY);
        linea.setStroke(color);
        return linea;
    }

    // Método para crear un rectángulo (fondo) utilizando polígonos
    private Polygon crearFondo(double startX, double startY, double endX, double endY, Color color) {
        Polygon fondo = new Polygon();
        fondo.getPoints().addAll(
                startX, startY,
                endX, startY,
                endX, endY,
                startX, endY
        );
        fondo.setFill(color);
        return fondo;
    }

    // Método para crear un polígono personalizado basado en un arreglo de puntos
    private Polygon crearFondo2(double[] puntos, Color color) {
        Polygon fondo = new Polygon();

        // Convertir el arreglo de double a Double
        Double[] puntosConvertidos = new Double[puntos.length];
        for (int i = 0; i < puntos.length; i++) {
            puntosConvertidos[i] = puntos[i];
        }
        fondo.getPoints().addAll(puntosConvertidos);
        fondo.setFill(color);
        return fondo;
    }

    // Método para agregar un LED a la lista de componentes conectados
    public void agregarComponenteConectado(LED componente) {
        ledsConectados.add(componente);
    }

    // Método estático para notificar a todos los componentes conectados (LEDs)

    // Método para verificar si una posición está dentro de un cuadrado (no implementado)
    private boolean isCuadrado(int posX, int posY){
        return false;
    }

    // Getters para obtener las celdas y buses del protoboard
    public Celdas getCelda1(){
        return celdas1;
    }

    public Celdas getCelda2(){
        return celdas2;
    }

    public BusesAlimentacion getBus1() {
        return bus1;
    }

    public BusesAlimentacion getBus2() {
        return bus2;
    }


    // Métodos para gestionar los cables conectados al protoboard
    public List<Cable> getCablesConctados() {
        return cablesConctados;
    }

    public void addCablesConctados(Cable cable) {
        if(!cablesConctados.contains(cable))
            Prototipo_Protoboard.cablesConctados.add(cable);
    }

    public void agregarChipConectado(Chip chip) {
       if (!chipsConectados.contains(chip)) {
           chipsConectados.add(chip);
       }
    }

    public void agregarLEDSConectados(LED led) {
        if (!ledsConectados.contains(led)) {
            ledsConectados.add(led);
        }
    }

    public void notificarChipsConectados(){
        if (notificarEnProceso) {
            return;
        }
        notificarEnProceso = true;
        for (Chip chip : chipsConectados) {
            chip.checkFinConnections();
        }
        notificarEnProceso = false;
    }

    public void notificarLEDSConectados(){
        for (LED led : ledsConectados) {
            led.checkFinConnections();
        }
    }

    // Métodos para gestionar los cables conectados al protoboard
    public List<Resistencia> getResistenciasConectadas() {
        return resistenciasConectadas;
    }

    public void addResistenciasConectadas(Resistencia resistencia) {
        if(!resistenciasConectadas.contains(resistencia))
            Prototipo_Protoboard.resistenciasConectadas.add(resistencia);
    }
}
