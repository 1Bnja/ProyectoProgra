package org.example.prototipo.protoboard;

import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.CubicCurve;
import javafx.scene.shape.Line;
import javafx.scene.text.Text;
import javafx.scene.Group;
import java.util.ArrayList;
import java.util.List;

public class Bateria extends Pane {

    private Group nodo = new Group();

    double origenX = Main.origenX - 350;
    double origenY = Main.origenY + 50;

    Cuadrados conectorPositivo;
    Cuadrados conectorNegativo;

    // Listas de cables conectados
    private List<Cable> cablesConectadosPositivo = new ArrayList<>();
    private List<Cable> cablesConectadosNegativo = new ArrayList<>();

    private Text cantVoltaje;

    public Bateria() {
        // Parte superior de la batería
        Line lineaSuperiorIzquierda = new Line(origenX - 60, origenY - 120, origenX - 60, origenY - 70);
        Line lineaSuperiorDerecha = new Line(origenX + 60, origenY - 120, origenX + 60, origenY - 70);
        Line lineaSuperior = new Line(origenX - 60, origenY - 120, origenX + 60, origenY - 120);
        Line lineaInferior = new Line(origenX - 60, origenY - 70, origenX + 60, origenY - 70);

        // Fondo en la parte superior
        for (int i = 0; i < 50; i++) {
            CubicCurve curvaCobre = new CubicCurve(
                    origenX - 60, origenY - 120 + i,
                    origenX - 40, origenY - 120 + i + 5,
                    origenX + 40, origenY - 120 + i + 5,
                    origenX + 60, origenY - 120 + i
            );
            curvaCobre.setFill(Color.DARKGOLDENROD);
            curvaCobre.setStroke(Color.DARKGOLDENROD);
            nodo.getChildren().add(curvaCobre);
        }

        // Conectores (para los terminales)
        conectorPositivo = new Cuadrados(20, 10);
        conectorPositivo.setX(origenX - 40);
        conectorPositivo.setY(origenY - 140);
        conectorPositivo.setFill(Color.DARKGREY);
        conectorPositivo.setSigno(0);
        conectorPositivo.setVoltaje(9);

        conectorNegativo = new Cuadrados(20, 10);
        conectorNegativo.setX(origenX + 20);
        conectorNegativo.setY(origenY - 140);
        conectorNegativo.setFill(Color.DARKGREY);
        conectorNegativo.setSigno(0);
        conectorNegativo.setVoltaje(9);

        // Parte inferior de la batería (zona negra)
        Line lineaInferiorIzquierda = new Line(origenX - 60, origenY - 70, origenX - 60, origenY + 80);
        Line lineaInferiorDerecha = new Line(origenX + 60, origenY - 70, origenX + 60, origenY + 80);
        Line lineaBase = new Line(origenX - 60, origenY + 80, origenX + 60, origenY + 80);

        // Fondo sólido en la parte inferior (color negro)
        for (int i = 0; i < 150; i++) {
            CubicCurve curvaNegra = new CubicCurve(
                    origenX - 60, origenY - 70 + i,
                    origenX - 40, origenY - 70 + i + 5,
                    origenX + 40, origenY - 70 + i + 5,
                    origenX + 60, origenY - 70 + i
            );
            curvaNegra.setFill(Color.BLACK);
            curvaNegra.setStroke(Color.BLACK);
            nodo.getChildren().add(curvaNegra);
        }

        // Simulación de la división entre la parte cobre y negra
        Line divisionColor = new Line(origenX - 60, origenY - 70, origenX + 60, origenY - 70);
        divisionColor.setStroke(Color.DARKGOLDENROD);
        divisionColor.setStrokeWidth(5);

        // Símbolos + y -
        Text simboloPositivo = new Text(origenX - 50, origenY - 50, "+");
        simboloPositivo.setFill(Color.BLACK);
        simboloPositivo.setStyle("-fx-font-size: 18px; -fx-font-weight: bold;");

        Text simboloNegativo = new Text(origenX + 30, origenY - 50, "-");
        simboloNegativo.setFill(Color.BLACK);
        simboloNegativo.setStyle("-fx-font-size: 18px; -fx-font-weight: bold;");

        // Texto 9V
        cantVoltaje = new Text(origenX - 20, origenY + 30, "9V");
        cantVoltaje.setFill(Color.WHITE);
        cantVoltaje.setStyle("-fx-font-size: 24px; -fx-font-weight: bold;");

        nodo.getChildren().addAll(
                lineaSuperior, lineaInferior, lineaSuperiorIzquierda, lineaSuperiorDerecha, conectorPositivo, conectorNegativo,
                lineaInferiorIzquierda, lineaInferiorDerecha, lineaBase, divisionColor,
                simboloPositivo, simboloNegativo, cantVoltaje
        );

        this.getChildren().add(nodo);
        this.setPickOnBounds(false);
    }

    // Métodos para manejar cables conectados
    public void conectarCablePositivo(Cable cable) {
        if (!cablesConectadosPositivo.contains(cable)) {
            cablesConectadosPositivo.add(cable);
        }
    }

    // Método para conectar un cable al conector negativo
    public void conectarCableNegativo(Cable cable) {
        if (!cablesConectadosNegativo.contains(cable)) {
            cablesConectadosNegativo.add(cable);
        }
    }

    // Método para cambiar el texto del voltaje
    public void cambiarTxtoVoltaje(int voltaje){
        cantVoltaje.setText(voltaje + "V");
    }

    // Método para desconectar un cable del conector positivo
    public void desconectarCablePositivo(Cable cable) {
        cablesConectadosPositivo.remove(cable);
    }

    // Método para desconectar un cable del conector negativo
    public void desconectarCableNegativo(Cable cable) {
        cablesConectadosNegativo.remove(cable);
    }

    // Metodo para obtener los cables conectados positivos
    public List<Cable> getCablesConectadosPositivo() {
        return cablesConectadosPositivo;
    }

    // Método para obtener los cables conectados negativos
    public List<Cable> getCablesConectadosNegativo() {
        return cablesConectadosNegativo;
    }

    // Métodos para obtener el conector negativo
    public Cuadrados getConectorNegativo() {
        return conectorNegativo;
    }

    // Método para obtener el conector positivo
    public Cuadrados getConectorPositivo() {
        return conectorPositivo;
    }
}
