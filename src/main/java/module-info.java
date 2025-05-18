module com.example.guess_that_beast {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires org.kordamp.bootstrapfx.core;
    requires com.fasterxml.jackson.databind;
    requires java.desktop;

    opens com.example.guess_that_beast to javafx.fxml;
    exports com.example.guess_that_beast;
}