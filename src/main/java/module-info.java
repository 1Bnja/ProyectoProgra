module org.example.prototipo {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.desktop;


    opens org.example.prototipo to javafx.fxml;
    exports org.example.prototipo.protoboard;
    opens org.example.prototipo.protoboard to javafx.fxml;
}


