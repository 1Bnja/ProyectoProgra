module org.example.prototipo {
    requires javafx.controls;
    requires javafx.fxml;


    opens org.example.prototipo to javafx.fxml;
    exports org.example.prototipo.protoboard;
}


