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

    double origenX = Main.origenX;
    double origenY = Main.origenY;

    Celdas celdas1;
    Celdas celdas2;
    BusesAlimentacion bus1;
    BusesAlimentacion bus2;

   public static List<LED> ledsConectados = new ArrayList<>();
   public static List<Cable> cablesConctados = new ArrayList<>();

    private List<Conexion> conexiones = new ArrayList<>();


    public Prototipo_Protoboard() {
        this.setPickOnBounds(false);

        Polygon fondoTotal = crearFondo(origenX - 220, origenY - 280, origenX + 580, origenY + 280, Color.LIGHTGRAY);
        Polygon fondoCarrilCentral = crearFondo(origenX - 220, origenY - 20, origenX + 580, origenY + 20, Color.GRAY);

        nodo.getChildren().addAll(fondoTotal, fondoCarrilCentral);

        Polygon fondo1Exterior = crearFondo2(new double[]{origenX - 210, origenY - 290,origenX + 590, origenY - 290,origenX + 580, origenY - 280,origenX - 220, origenY - 280}, Color.GRAY);
        Polygon fondo2Exterior = crearFondo2(new double[]{origenX + 580, origenY - 280,origenX + 590, origenY - 290,origenX + 590, origenY + 270, origenX + 580, origenY + 280}, Color.GRAY);

        nodo.getChildren().addAll(fondo1Exterior, fondo2Exterior);

        // Cuadrado Principal
        Line lineaArriba = crearLinea(origenX - 220,origenY - 280, origenX + 580, origenY - 280, Color.BLACK);
        Line lineaAbajo = crearLinea(origenX - 220, origenY + 280, origenX + 580, origenY + 280, Color.BLACK);
        Line lineaIzquierda = crearLinea(origenX + 580, origenY - 280, origenX + 580, origenY + 280, Color.BLACK);
        Line lineaDerecha = crearLinea(origenX - 220, origenY - 280, origenX - 220, origenY + 280, Color.BLACK);

        // Lineas Internas
        Line lineaInterna1 = crearLinea(origenX - 220, origenY - 210, origenX + 580, origenY - 210, Color.BLACK);
        Line lineaInterna2 = crearLinea(origenX - 220, origenY - 20, origenX + 580, origenY - 20, Color.BLACK);
        Line lineaInterna3 = crearLinea(origenX - 220, origenY + 20, origenX + 580, origenY + 20, Color.BLACK);
        Line lineaInterna4 = crearLinea(origenX - 220, origenY + 210, origenX + 580, origenY + 210, Color.BLACK);

        nodo.getChildren().addAll(lineaArriba, lineaAbajo, lineaIzquierda, lineaDerecha, lineaInterna1, lineaInterna2, lineaInterna3, lineaInterna4);

        Line lineaGuia1 = crearLinea(origenX - 170,origenY - 273, origenX + 534, origenY - 273, Color.BLUE);
        Line lineaGuia2 = crearLinea(origenX - 170, origenY - 217, origenX + 534, origenY - 217, Color.RED);
        Line lineaGuia3 = crearLinea(origenX - 170, origenY + 217, origenX + 534, origenY + 217, Color.BLUE);
        Line lineaGuia4 = crearLinea(origenX - 170, origenY + 273, origenX + 534, origenY + 273, Color.RED);

        nodo.getChildren().addAll(lineaGuia1, lineaGuia2, lineaGuia3, lineaGuia4);

        celdas1 = new Celdas(origenX - 210, origenY - 190, new char[]{'j', 'i', 'h', 'g', 'f'}, false, this);
        nodo.getChildren().add(celdas1);
        celdas2 = new Celdas(origenX - 210, origenY + 30, new char[]{'e', 'd', 'c', 'b', 'a'}, true, this);
        nodo.getChildren().add(celdas2);
        bus1 = new BusesAlimentacion(origenX - 190, origenY - 265, new char[]{'-', '+'});
        nodo.getChildren().add(bus1);
        bus2 = new BusesAlimentacion(origenX - 190, origenY + 225, new char[]{'-', '+'});
        nodo.getChildren().add(bus2);

        // Lineas Exteriores
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
                lineaExterior,lineaExterior1, lineaExterior2, lineaExterior3, lineaExterior4, lineaExterior5,
                lineaExterior6, lineaExterior7, lineaExterior8
        );

        this.getChildren().add(nodo);
    }


    private Line crearLinea(double startX, double startY, double endX, double endY, Color color) {
        Line linea = new Line(startX, startY, endX, endY);
        linea.setStroke(color);
        return linea;
    }

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

    private Polygon crearFondo2(double[] puntos, Color color) {
        Polygon fondo = new Polygon();

        Double[] puntosConvertidos = new Double[puntos.length];
        for (int i = 0; i < puntos.length; i++) {
            puntosConvertidos[i] = puntos[i];
        }
        fondo.getPoints().addAll(puntosConvertidos);
        fondo.setFill(color);
        return fondo;
    }

    public void agregarComponenteConectado(LED componente) {
        ledsConectados.add(componente);
    }

    public static void notificarComponentesConectados() {
        for (LED componente : ledsConectados) {
            componente.checkFinConnections();
        }
    }

    private boolean isCuadrado(int posX, int posY){

        return false;
    }

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

    public List<Conexion> getConexiones() {
        return conexiones;
    }

    public void setConexiones(List<Conexion> conexiones) {
        this.conexiones = conexiones;


    }

    public  List<Cable> getCablesConctados() {
        return cablesConctados;
    }

    public  void addCablesConctados(Cable cable) {
        if(!cablesConctados.contains(cable))
            Prototipo_Protoboard.cablesConctados.add(cable);
    }



}
