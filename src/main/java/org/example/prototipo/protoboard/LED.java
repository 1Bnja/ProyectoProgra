package org.example.prototipo.protoboard;

import javafx.scene.Group;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Line;

public class LED extends Pane{
    private Group nodo = new Group();

    double origenX = Main.origenX;
    double origenY = Main.origenY;

    public LED(){
        Line led1 = new Line();
    }
}
