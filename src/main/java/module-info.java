module com.example.gamelibrary {
    requires javafx.controls;
    requires javafx.fxml;
    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires net.synedra.validatorfx;
    requires org.kordamp.bootstrapfx.core;
    requires org.kordamp.ikonli.javafx;
    requires java.sql;
    requires java.desktop;

    opens com.example.gamelibrary.controllers to javafx.fxml;
    opens com.example.gamelibrary.models to javafx.base;

    exports com.example.gamelibrary;
}
