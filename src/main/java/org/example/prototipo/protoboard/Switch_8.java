package org.example.prototipo.protoboard;

import javafx.geometry.Bounds;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.scene.shape.Polygon;

public class Switch_8 extends Pane {

    private Group nodo = new Group();

    // Definición de las esquinas estirables y las patas del interruptor
    private Cuadrados fin1, fin2, fin3, fin4, fin5, fin6, fin7, fin8;
    private Cuadrados fin12, fin22, fin32, fin42, fin52, fin62, fin72, fin82;
    private Line pata1, pata2, pata3, pata4, pata5, pata6, pata7, pata8;
    private Line pata12, pata22, pata32, pata42, pata52, pata62, pata72, pata82;

    // Posición del mouse para manejar el arrastre
    private double mouseX;
    private double mouseY;
    private boolean line_en_arrastre = false;

    // Coordenadas de origen
    double origenX = Main.origenX;
    double origenY = Main.origenY;

    // Referencias al protoboard y al LED
    Prototipo_Protoboard protoboard;
    LED led;
    int celda;
    int colum_1, colum_2;

    int prueba=0;

    // Estado del interruptor
    boolean encendido;

    // Variables para almacenar la posición de entrada y salida
    int filaEntrada, columnaEntrada;
    int filaSalida, columnaSalida;
    double voltaje;

    // Variables para controlar si las patas están conectadas
    private boolean fin1Conectada = false;
    private boolean fin2Conectada = false;
    private boolean fin3Conectada = false;
    private boolean fin4Conectada = false;
    private boolean fin5Conectada = false;
    private boolean fin6Conectada = false;
    private boolean fin7Conectada = false;
    private boolean fin8Conectada = false;
    private boolean fin12Conectada = false;
    private boolean fin22Conectada = false;
    private boolean fin32Conectada = false;
    private boolean fin42Conectada = false;
    private boolean fin52Conectada = false;
    private boolean fin62Conectada = false;
    private boolean fin72Conectada = false;
    private boolean fin82Conectada = false;

    // Signos de las conexiones de las patas
    private int signoFin1 = 0;
    private int signoFin2 = 0;
    private int signoFin3 = 0;
    private int signoFin4 = 0;
    private int signoFin5 = 0;
    private int signoFin6 = 0;
    private int signoFin7 = 0;
    private int signoFin8 = 0;
    private int signoFin12 = 0;
    private int signoFin22 = 0;
    private int signoFin32 = 0;
    private int signoFin42 = 0;
    private int signoFin52 = 0;
    private int signoFin62 = 0;
    private int signoFin72 = 0;
    private int signoFin82 = 0;

    Cuadrados boton1, boton2, boton3, boton4, boton5, boton6, boton7, boton8;

    // Constructor de la clase Switch_8
    public Switch_8() {

        this.encendido = false;

        double posX = origenX - 570;
        double posY = origenY - 105;
        int ancho = 180;
        int alto = 70;

        Cuadrados switch8 = new Cuadrados(ancho, alto, posX, posY, Color.GRAY);

        nodo.getChildren().add(switch8);

        // Crear las patas superiores (con la misma lógica de distancia que en Chip)
        pata1 = crearLinea(origenX - 567, origenY - 105, origenX - 567, origenY - 125);
        pata2 = crearLinea(origenX - 541, origenY - 105, origenX - 541, origenY - 125);
        pata3 = crearLinea(origenX - 517, origenY - 105, origenX - 517, origenY - 125);
        pata4 = crearLinea(origenX - 491, origenY - 105, origenX - 491, origenY - 125);
        pata5 = crearLinea(origenX - 467, origenY - 105, origenX - 467, origenY - 125);
        pata6 = crearLinea(origenX - 442, origenY - 105, origenX - 442, origenY - 125);
        pata7 = crearLinea(origenX - 418, origenY - 105, origenX - 418, origenY - 125);
        pata8 = crearLinea(origenX - 393, origenY - 105, origenX - 393, origenY - 125);

        // Crear las patas inferiores (con la misma lógica de distancia que en Chip)
        pata12 = crearLinea(origenX - 567, origenY - 35, origenX - 567, origenY - 15);
        pata22 = crearLinea(origenX - 541, origenY - 35, origenX - 541, origenY - 15);
        pata32 = crearLinea(origenX - 517, origenY - 35, origenX - 517, origenY - 15);
        pata42 = crearLinea(origenX - 491, origenY - 35, origenX - 491, origenY - 15);
        pata52 = crearLinea(origenX - 467, origenY - 35, origenX - 467, origenY - 15);
        pata62 = crearLinea(origenX - 442, origenY - 35, origenX - 442, origenY - 15);
        pata72 = crearLinea(origenX - 418, origenY - 35, origenX - 418, origenY - 15);
        pata82 = crearLinea(origenX - 393, origenY - 35, origenX - 393, origenY - 15);

        // Crear los botones del interruptor
        boton1 = crearBoton(origenX - 567, origenY - 95);
        boton2 = crearBoton(origenX - 543, origenY - 95);
        boton3 = crearBoton(origenX - 519, origenY - 95);
        boton4 = crearBoton(origenX - 495, origenY - 95);
        boton5 = crearBoton(origenX - 471, origenY - 95);
        boton6 = crearBoton(origenX - 447, origenY - 95);
        boton7 = crearBoton(origenX - 423, origenY - 95);
        boton8 = crearBoton(origenX - 399, origenY - 95);

        // Crear las esquinas estirables en las patas superiores
        fin1 = crearEsquinaEstirable(pata1, 1);
        fin2 = crearEsquinaEstirable(pata2, 1);
        fin3 = crearEsquinaEstirable(pata3, 1);
        fin4 = crearEsquinaEstirable(pata4, 1);
        fin5 = crearEsquinaEstirable(pata5, 1);
        fin6 = crearEsquinaEstirable(pata6, 1);
        fin7 = crearEsquinaEstirable(pata7, 1);
        fin8 = crearEsquinaEstirable(pata8, 1);

        // Crear las esquinas estirables en las patas inferiores
        fin12 = crearEsquinaEstirable(pata12, 2);
        fin22 = crearEsquinaEstirable(pata22, 2);
        fin32 = crearEsquinaEstirable(pata32, 2);
        fin42 = crearEsquinaEstirable(pata42, 2);
        fin52 = crearEsquinaEstirable(pata52, 2);
        fin62 = crearEsquinaEstirable(pata62, 2);
        fin72 = crearEsquinaEstirable(pata72, 2);
        fin82 = crearEsquinaEstirable(pata82, 2);

        // Configurar el arrastre para cada esquina estirable
        configurarArrastre(fin1, pata1, 1);
        configurarArrastre(fin2, pata2, 1);
        configurarArrastre(fin3, pata3, 1);
        configurarArrastre(fin4, pata4, 1);
        configurarArrastre(fin5, pata5, 1);
        configurarArrastre(fin6, pata6, 1);
        configurarArrastre(fin7, pata7, 1);
        configurarArrastre(fin8, pata8, 1);

        configurarArrastre(fin12, pata12, 2);
        configurarArrastre(fin22, pata22, 2);
        configurarArrastre(fin32, pata32, 2);
        configurarArrastre(fin42, pata42, 2);
        configurarArrastre(fin52, pata52, 2);
        configurarArrastre(fin62, pata62, 2);
        configurarArrastre(fin72, pata72, 2);
        configurarArrastre(fin82, pata82, 2);



        boton1.setOnMouseClicked(event ->{
            if (encendido) {
                // Si está encendido, apagar el interruptor
                if (prueba == 1) {
                    protoboard.getCelda1().alternarColumna(columnaEntrada, 0,0); // Cortar energía
                } else if (prueba== 2) {
                    protoboard.getCelda2().alternarColumna(columnaEntrada, 0,0);
                }
                boton1.setFill(Color.BLACK);
            } else {
                // Si está apagado, encender el interruptor
                int signoEntrada;
                int signoSalida;
                double volt1;
                double volt2;

                if (celda == 1) {
                    signoEntrada = protoboard.getCelda1().getSigno(filaEntrada, columnaEntrada);
                    signoSalida= protoboard.getCelda2().getSigno(filaEntrada, columnaEntrada);
                    volt1= protoboard.getCelda1().getVoltaje2(columnaEntrada);
                    volt2= protoboard.getCelda2().getVoltaje2(columnaEntrada);
                    if(signoEntrada==0){
                        protoboard.getCelda1().alternarColumna(columnaEntrada, signoSalida,volt1);
                        prueba=1;
                    }else{protoboard.getCelda2().alternarColumna(columnaEntrada, signoEntrada,volt2);
                    prueba=2;} // Transferir energía
                } else if (celda == 2) {
                    volt1= protoboard.getCelda1().getVoltaje2(columnaEntrada);
                    signoEntrada = protoboard.getCelda2().getSigno(filaEntrada, columnaEntrada);
                    protoboard.getCelda1().alternarColumna(columnaEntrada, signoEntrada,volt1);
                    prueba=1;
                }
                boton1.setFill(Color.YELLOW); // Cambiar color a encendido
            }
            encendido = !encendido; // Cambiar estado

            // Notificar a los componentes conectados para actualizar su estado
            Prototipo_Protoboard.notificarComponentesConectados();
        });
        boton2.setOnMouseClicked(event ->{
            if (encendido) {
                // Si está encendido, apagar el interruptor
                if (prueba == 1) {
                    protoboard.getCelda1().alternarColumna(columnaEntrada, 0,0); // Cortar energía
                } else if (prueba== 2) {
                    protoboard.getCelda2().alternarColumna(columnaEntrada, 0,0);
                }
                boton2.setFill(Color.BLACK);
            } else {
                // Si está apagado, encender el interruptor
                int signoEntrada;
                int signoSalida;
                double volt1;
                double volt2;

                if (celda == 1) {
                    signoEntrada = protoboard.getCelda1().getSigno(filaEntrada, columnaEntrada);
                    signoSalida= protoboard.getCelda2().getSigno(filaEntrada, columnaEntrada);
                    volt1= protoboard.getCelda1().getVoltaje2(columnaEntrada);
                    volt2= protoboard.getCelda2().getVoltaje2(columnaEntrada);
                    if(signoEntrada==0){
                        protoboard.getCelda1().alternarColumna(columnaEntrada, signoSalida,volt1);
                        prueba=1;
                    }else{protoboard.getCelda2().alternarColumna(columnaEntrada, signoEntrada,volt2);
                        prueba=2;} // Transferir energía
                } else if (celda == 2) {
                    volt1= protoboard.getCelda1().getVoltaje2(columnaEntrada);
                    signoEntrada = protoboard.getCelda2().getSigno(filaEntrada, columnaEntrada);
                    protoboard.getCelda1().alternarColumna(columnaEntrada, signoEntrada,volt1);
                    prueba=1;
                }
                boton2.setFill(Color.YELLOW); // Cambiar color a encendido
            }
            encendido = !encendido; // Cambiar estado

            // Notificar a los componentes conectados para actualizar su estado
            Prototipo_Protoboard.notificarComponentesConectados();
        });
        boton3.setOnMouseClicked(event ->{
            if (encendido) {
                // Si está encendido, apagar el interruptor
                if (prueba == 1) {
                    protoboard.getCelda1().alternarColumna(columnaEntrada, 0,0); // Cortar energía
                } else if (prueba== 2) {
                    protoboard.getCelda2().alternarColumna(columnaEntrada, 0,0);
                }
                boton3.setFill(Color.BLACK);
            } else {
                // Si está apagado, encender el interruptor
                int signoEntrada;
                int signoSalida;
                double volt1;
                double volt2;

                if (celda == 1) {
                    signoEntrada = protoboard.getCelda1().getSigno(filaEntrada, columnaEntrada);
                    signoSalida= protoboard.getCelda2().getSigno(filaEntrada, columnaEntrada);
                    volt1= protoboard.getCelda1().getVoltaje2(columnaEntrada);
                    volt2= protoboard.getCelda2().getVoltaje2(columnaEntrada);
                    if(signoEntrada==0){
                        protoboard.getCelda1().alternarColumna(columnaEntrada, signoSalida,volt1);
                        prueba=1;
                    }else{protoboard.getCelda2().alternarColumna(columnaEntrada, signoEntrada,volt2);
                        prueba=2;} // Transferir energía
                } else if (celda == 2) {
                    volt1= protoboard.getCelda1().getVoltaje2(columnaEntrada);
                    signoEntrada = protoboard.getCelda2().getSigno(filaEntrada, columnaEntrada);
                    protoboard.getCelda1().alternarColumna(columnaEntrada, signoEntrada,volt1);
                    prueba=1;
                }
                boton3.setFill(Color.YELLOW); // Cambiar color a encendido
            }
            encendido = !encendido; // Cambiar estado

            // Notificar a los componentes conectados para actualizar su estado
            Prototipo_Protoboard.notificarComponentesConectados();
        });
        boton4.setOnMouseClicked(event ->{
            if (encendido) {
                // Si está encendido, apagar el interruptor
                if (prueba == 1) {
                    protoboard.getCelda1().alternarColumna(columnaEntrada, 0,0); // Cortar energía
                } else if (prueba== 2) {
                    protoboard.getCelda2().alternarColumna(columnaEntrada, 0,0);
                }
                boton4.setFill(Color.BLACK);
            } else {
                // Si está apagado, encender el interruptor
                int signoEntrada;
                int signoSalida;
                double volt1;
                double volt2;

                if (celda == 1) {
                    signoEntrada = protoboard.getCelda1().getSigno(filaEntrada, columnaEntrada);
                    signoSalida= protoboard.getCelda2().getSigno(filaEntrada, columnaEntrada);
                    volt1= protoboard.getCelda1().getVoltaje2(columnaEntrada);
                    volt2= protoboard.getCelda2().getVoltaje2(columnaEntrada);
                    if(signoEntrada==0){
                        protoboard.getCelda1().alternarColumna(columnaEntrada, signoSalida,volt1);
                        prueba=1;
                    }else{protoboard.getCelda2().alternarColumna(columnaEntrada, signoEntrada,volt2);
                        prueba=2;} // Transferir energía
                } else if (celda == 2) {
                    volt1= protoboard.getCelda1().getVoltaje2(columnaEntrada);
                    signoEntrada = protoboard.getCelda2().getSigno(filaEntrada, columnaEntrada);
                    protoboard.getCelda1().alternarColumna(columnaEntrada, signoEntrada,volt1);
                    prueba=1;
                }
                boton4.setFill(Color.YELLOW); // Cambiar color a encendido
            }
            encendido = !encendido; // Cambiar estado

            // Notificar a los componentes conectados para actualizar su estado
            Prototipo_Protoboard.notificarComponentesConectados();
        });
        boton5.setOnMouseClicked(event ->{
            if (encendido) {
                // Si está encendido, apagar el interruptor
                if (prueba == 1) {
                    protoboard.getCelda1().alternarColumna(columnaEntrada, 0,0); // Cortar energía
                } else if (prueba== 2) {
                    protoboard.getCelda2().alternarColumna(columnaEntrada, 0,0);
                }
                boton5.setFill(Color.BLACK);
            } else {
                // Si está apagado, encender el interruptor
                int signoEntrada;
                int signoSalida;
                double volt1;
                double volt2;

                if (celda == 1) {
                    signoEntrada = protoboard.getCelda1().getSigno(filaEntrada, columnaEntrada);
                    signoSalida= protoboard.getCelda2().getSigno(filaEntrada, columnaEntrada);
                    volt1= protoboard.getCelda1().getVoltaje2(columnaEntrada);
                    volt2= protoboard.getCelda2().getVoltaje2(columnaEntrada);
                    if(signoEntrada==0){
                        protoboard.getCelda1().alternarColumna(columnaEntrada, signoSalida,volt1);
                        prueba=1;
                    }else{protoboard.getCelda2().alternarColumna(columnaEntrada, signoEntrada,volt2);
                        prueba=2;} // Transferir energía
                } else if (celda == 2) {
                    volt1= protoboard.getCelda1().getVoltaje2(columnaEntrada);
                    signoEntrada = protoboard.getCelda2().getSigno(filaEntrada, columnaEntrada);
                    protoboard.getCelda1().alternarColumna(columnaEntrada, signoEntrada,volt1);
                    prueba=1;
                }
                boton5.setFill(Color.YELLOW); // Cambiar color a encendido
            }
            encendido = !encendido; // Cambiar estado

            // Notificar a los componentes conectados para actualizar su estado
            Prototipo_Protoboard.notificarComponentesConectados();
        });
        boton6.setOnMouseClicked(event ->{
            if (encendido) {
                // Si está encendido, apagar el interruptor
                if (prueba == 1) {
                    protoboard.getCelda1().alternarColumna(columnaEntrada, 0,0); // Cortar energía
                } else if (prueba== 2) {
                    protoboard.getCelda2().alternarColumna(columnaEntrada, 0,0);
                }
                boton6.setFill(Color.BLACK);
            } else {
                // Si está apagado, encender el interruptor
                int signoEntrada;
                int signoSalida;
                double volt1;
                double volt2;

                if (celda == 1) {
                    signoEntrada = protoboard.getCelda1().getSigno(filaEntrada, columnaEntrada);
                    signoSalida= protoboard.getCelda2().getSigno(filaEntrada, columnaEntrada);
                    volt1= protoboard.getCelda1().getVoltaje2(columnaEntrada);
                    volt2= protoboard.getCelda2().getVoltaje2(columnaEntrada);
                    if(signoEntrada==0){
                        protoboard.getCelda1().alternarColumna(columnaEntrada, signoSalida,volt1);
                        prueba=1;
                    }else{protoboard.getCelda2().alternarColumna(columnaEntrada, signoEntrada,volt2);
                        prueba=2;} // Transferir energía
                } else if (celda == 2) {
                    volt1= protoboard.getCelda1().getVoltaje2(columnaEntrada);
                    signoEntrada = protoboard.getCelda2().getSigno(filaEntrada, columnaEntrada);
                    protoboard.getCelda1().alternarColumna(columnaEntrada, signoEntrada,volt1);
                    prueba=1;
                }
                boton6.setFill(Color.YELLOW); // Cambiar color a encendido
            }
            encendido = !encendido; // Cambiar estado

            // Notificar a los componentes conectados para actualizar su estado
            Prototipo_Protoboard.notificarComponentesConectados();
        });
        boton7.setOnMouseClicked(event ->{
            if (encendido) {
                // Si está encendido, apagar el interruptor
                if (prueba == 1) {
                    protoboard.getCelda1().alternarColumna(columnaEntrada, 0,0); // Cortar energía
                } else if (prueba== 2) {
                    protoboard.getCelda2().alternarColumna(columnaEntrada, 0,0);
                }
                boton7.setFill(Color.BLACK);
            } else {
                // Si está apagado, encender el interruptor
                int signoEntrada;
                int signoSalida;
                double volt1;
                double volt2;

                if (celda == 1) {
                    signoEntrada = protoboard.getCelda1().getSigno(filaEntrada, columnaEntrada);
                    signoSalida= protoboard.getCelda2().getSigno(filaEntrada, columnaEntrada);
                    volt1= protoboard.getCelda1().getVoltaje2(columnaEntrada);
                    volt2= protoboard.getCelda2().getVoltaje2(columnaEntrada);
                    if(signoEntrada==0){
                        protoboard.getCelda1().alternarColumna(columnaEntrada, signoSalida,volt1);
                        prueba=1;
                    }else{protoboard.getCelda2().alternarColumna(columnaEntrada, signoEntrada,volt2);
                        prueba=2;} // Transferir energía
                } else if (celda == 2) {
                    volt1= protoboard.getCelda1().getVoltaje2(columnaEntrada);
                    signoEntrada = protoboard.getCelda2().getSigno(filaEntrada, columnaEntrada);
                    protoboard.getCelda1().alternarColumna(columnaEntrada, signoEntrada,volt1);
                    prueba=1;
                }
                boton7.setFill(Color.YELLOW); // Cambiar color a encendido
            }
            encendido = !encendido; // Cambiar estado

            // Notificar a los componentes conectados para actualizar su estado
            Prototipo_Protoboard.notificarComponentesConectados();
        });
        boton8.setOnMouseClicked(event ->{
            if (encendido) {
                // Si está encendido, apagar el interruptor
                if (prueba == 1) {
                    protoboard.getCelda1().alternarColumna(columnaEntrada, 0,0); // Cortar energía
                } else if (prueba== 2) {
                    protoboard.getCelda2().alternarColumna(columnaEntrada, 0,0);
                }
                boton8.setFill(Color.BLACK);
            } else {
                // Si está apagado, encender el interruptor
                int signoEntrada;
                int signoSalida;
                double volt1;
                double volt2;

                if (celda == 1) {
                    signoEntrada = protoboard.getCelda1().getSigno(filaEntrada, columnaEntrada);
                    signoSalida= protoboard.getCelda2().getSigno(filaEntrada, columnaEntrada);
                    volt1= protoboard.getCelda1().getVoltaje2(columnaEntrada);
                    volt2= protoboard.getCelda2().getVoltaje2(columnaEntrada);
                    if(signoEntrada==0){
                        protoboard.getCelda1().alternarColumna(columnaEntrada, signoSalida,volt1);
                        prueba=1;
                    }else{protoboard.getCelda2().alternarColumna(columnaEntrada, signoEntrada,volt2);
                        prueba=2;} // Transferir energía
                } else if (celda == 2) {
                    volt1= protoboard.getCelda1().getVoltaje2(columnaEntrada);
                    signoEntrada = protoboard.getCelda2().getSigno(filaEntrada, columnaEntrada);
                    protoboard.getCelda1().alternarColumna(columnaEntrada, signoEntrada,volt1);
                    prueba=1;
                }
                boton8.setFill(Color.YELLOW); // Cambiar color a encendido
            }
            encendido = !encendido; // Cambiar estado

            // Notificar a los componentes conectados para actualizar su estado
            Prototipo_Protoboard.notificarComponentesConectados();
        });


        // Configurar el arrastre para el nodo completo (interruptor)
        configurarArrastreNodo();

        // Añadir todos los elementos al grupo nodo
        nodo.getChildren().addAll(
                boton1, boton2, boton3, boton4, boton5, boton6, boton7, boton8,
                pata1, pata2, pata3, pata4, pata5, pata6, pata7, pata8,
                pata12, pata22, pata32, pata42, pata52, pata62, pata72, pata82,
                fin1, fin2, fin3, fin4, fin5, fin6, fin7, fin8,
                fin12, fin22, fin32, fin42, fin52, fin62, fin72, fin82
        );

        // Añadir el nodo al pane
        this.getChildren().add(nodo);
        // Desactivar la detección de eventos en los límites del pane
        this.setPickOnBounds(false);
    }

    // Método para crear un botón en el interruptor
    private Cuadrados crearBoton(double x, double y) {
        Cuadrados boton = new Cuadrados(7, 0);
        boton.setheidht(50);
        boton.setTranslateX(x);
        boton.setTranslateY(y);
        boton.setFill(Color.BLACK);


        return boton;
    }

    // Método para crear una línea entre dos puntos
    private Line crearLinea(double startX, double startY, double endX, double endY) {
        Line linea = new Line(startX, startY, endX, endY);
        linea.setStroke(Color.BLACK);
        return linea;
    }

    // Método para crear una esquina estirable en la punta de una pata
    private Cuadrados crearEsquinaEstirable(Line pata, int lugar) {
        Cuadrados point = new Cuadrados(11, 2);
        point.setX(pata.getEndX() - 5);
        point.setY(pata.getEndY() - 5);
        point.setFill(Color.RED);
        point.setLugar(lugar);
        return point;
    }

    // Método para configurar el arrastre de las esquinas estirables
    private void configurarArrastre(Cuadrados estirable, Line pata, int lado) {
        estirable.setOnMousePressed(e -> {
            empezarArrastre(e, pata);
            nodo.toFront();
        });
        estirable.setOnMouseDragged(e -> arrastre(e, pata, estirable));

        estirable.setOnMouseReleased(event -> {
            verificarConexionPata(estirable, pata, lado);
        });
    }

    // Método para verificar si una pata está conectada a una celda del protoboard
    private void verificarConexionPata(Cuadrados estirable, Line pata, int lado) {
        double mouseX = estirable.localToScene(estirable.getBoundsInLocal()).getCenterX();
        double mouseY = estirable.localToScene(estirable.getBoundsInLocal()).getCenterY();

        if (protoboard != null) {
            Node celdaEncontrada = null;
            int col = 0;
            int row = 0;
            int signoCelda = 0;
            double volt=0;

            // Verificar si está sobre celda1
            celdaEncontrada = verificarSiEstaEnCelda(mouseX, mouseY, (GridPane) protoboard.getCelda1().getChildren().get(0));
            if (celdaEncontrada != null) {
                col = GridPane.getColumnIndex(celdaEncontrada) - 1;
                row = GridPane.getRowIndex(celdaEncontrada);
                signoCelda = protoboard.getCelda1().getSigno(row, col);
                estirable.setSigno(signoCelda);
                volt= protoboard.getCelda1().getVoltaje2(col);
                estirable.setVoltaje(volt);
                if(signoCelda==0){
                    celda=2;
                }else{
                    celda=1;
                }

            } else {
                // Verificar si está sobre celda2
                celdaEncontrada = verificarSiEstaEnCelda(mouseX, mouseY, (GridPane) protoboard.getCelda2().getChildren().get(0));
                if (celdaEncontrada != null) {
                    col = GridPane.getColumnIndex(celdaEncontrada) - 1;
                    row = GridPane.getRowIndex(celdaEncontrada) ;
                    signoCelda = protoboard.getCelda2().getSigno(row, col);
                    estirable.setSigno(signoCelda);
                    volt= protoboard.getCelda2().getVoltaje2(col);
                    estirable.setVoltaje(volt);

                    if(signoCelda==0){
                        celda=1;
                    }else{
                        celda=2;
                    }
                } else {
                    // No está conectado a ninguna celda
                    estirable.setSigno(0);
                    return;
                }
            }

            // Establecer el signo de la pata según la celda conectada
            estirable.setSigno(signoCelda);
            if (celda == 1 ) {
                if(estirable.getSigno()!=0){
                // Lado de entrada
                filaEntrada = row;
                columnaEntrada = col;
                }else{
                    filaSalida = row;
                    columnaSalida = col;
                }
            } else if (celda == 2) {
                if(estirable.getSigno()!=0){
                    filaEntrada = row;
                    columnaEntrada = col;
                }else {
                    // Lado de salida
                    filaSalida = row;
                    columnaSalida = col;
                }
            }
        }

    }


    // Método para verificar si un punto está sobre una celda específica
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

    // Método para configurar el arrastre del nodo completo (interruptor)
    private void configurarArrastreNodo() {
        nodo.setOnMousePressed(e -> {
            if (!line_en_arrastre) {
                nodo.toFront();
                mouseX = e.getSceneX();
                mouseY = e.getSceneY();
            }
        });

        nodo.setOnMouseDragged(e -> {
            if (!line_en_arrastre) {
                double dX = e.getSceneX() - mouseX;
                double dY = e.getSceneY() - mouseY;

                // Actualizar la posición del nodo
                nodo.setLayoutX(nodo.getLayoutX() + dX);
                nodo.setLayoutY(nodo.getLayoutY() + dY);

                mouseX = e.getSceneX();
                mouseY = e.getSceneY();

                actualizarPosiciones();
            }
        });

        nodo.setOnMouseReleased(event -> {
            // Actualizar las conexiones de las patas al soltar el nodo
            actualizarConexionesPatas();
        });
    }

    // Método que inicia el arrastre al presionar el mouse
    private void empezarArrastre(MouseEvent event, Line pata) {
        line_en_arrastre = true;
        mouseX = event.getSceneX();
        mouseY = event.getSceneY();
    }

    // Método que maneja el arrastre de una pata
    private void arrastre(MouseEvent event, Line line, Cuadrados estirable) {
        double offsetX = event.getSceneX() - mouseX;
        double offsetY = event.getSceneY() - mouseY;

        // Actualizar la posición de la línea (pata)
        line.setEndX(line.getEndX() + offsetX);
        line.setEndY(line.getEndY() + offsetY);

        actualizarEstirable(estirable, line);

        mouseX = event.getSceneX();
        mouseY = event.getSceneY();

        line_en_arrastre = false;
    }

    // Método para actualizar la posición de la esquina estirable según la pata
    private void actualizarEstirable(Cuadrados estirable, Line pata) {
        estirable.setX(pata.getEndX() - 5);
        estirable.setY(pata.getEndY() - 5);
    }

    // Método para actualizar las posiciones de las esquinas estirables al mover el nodo
    private void actualizarPosiciones() {
        actualizarEstirable(fin1, pata1);
        actualizarEstirable(fin2, pata2);
        actualizarEstirable(fin3, pata3);
        actualizarEstirable(fin4, pata4);
        actualizarEstirable(fin5, pata5);
        actualizarEstirable(fin6, pata6);
        actualizarEstirable(fin7, pata7);
        actualizarEstirable(fin8, pata8);
        actualizarEstirable(fin12, pata12);
        actualizarEstirable(fin22, pata22);
        actualizarEstirable(fin32, pata32);
        actualizarEstirable(fin42, pata42);
        actualizarEstirable(fin52, pata52);
        actualizarEstirable(fin62, pata62);
        actualizarEstirable(fin72, pata72);
        actualizarEstirable(fin82, pata82);
    }

    // Método para actualizar las conexiones de todas las patas
    private void actualizarConexionesPatas() {
        verificarConexionPata(fin1, pata1, 1);
        verificarConexionPata(fin2, pata2, 1);
        verificarConexionPata(fin3, pata3, 1);
        verificarConexionPata(fin4, pata4, 1);
        verificarConexionPata(fin5, pata5, 1);
        verificarConexionPata(fin6, pata6, 1);
        verificarConexionPata(fin7, pata7, 1);
        verificarConexionPata(fin8, pata8, 1);
        verificarConexionPata(fin12, pata12, 2);
        verificarConexionPata(fin22, pata22, 2);
        verificarConexionPata(fin32, pata32, 2);
        verificarConexionPata(fin42, pata42, 2);
        verificarConexionPata(fin52, pata52, 2);
        verificarConexionPata(fin62, pata62, 2);
        verificarConexionPata(fin72, pata72, 2);
        verificarConexionPata(fin82, pata82, 2);
    }



    // Getters y setters para el protoboard y el LED
    public Prototipo_Protoboard getProtoboard() {
        return protoboard;
    }

    public void setProtoboard(Prototipo_Protoboard protoboard) {
        this.protoboard = protoboard;
    }

    public LED getLed() {
        return led;
    }

    public void setLed(LED led) {
        this.led = led;
    }

    // Getters para las esquinas estirables
    public Cuadrados getFin1() {
        return fin1;
    }

    public Cuadrados getFin2() {
        return fin2;
    }

    public Cuadrados getFin3() {
        return fin3;
    }

    public Cuadrados getFin4() {
        return fin4;
    }

    public Cuadrados getFin5() {
        return fin5;
    }

    public Cuadrados getFin6() {
        return fin6;
    }

    public Cuadrados getFin7() {
        return fin7;
    }

    public Cuadrados getFin8() {
        return fin8;
    }

    public Cuadrados getFin12() {
        return fin12;
    }

    public Cuadrados getFin22() {
        return fin22;
    }

    public Cuadrados getFin32() {
        return fin32;
    }

    public Cuadrados getFin42() {
        return fin42;
    }

    public Cuadrados getFin52() {
        return fin52;
    }

    public Cuadrados getFin62() {
        return fin62;
    }

    public Cuadrados getFin72() {
        return fin72;
    }

    public Cuadrados getFin82() {
        return fin82;
    }
}
