module com.cincuentazo {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.graphics;

    opens com.cincuentazo.controller to javafx.fxml;

    exports com.cincuentazo;
    exports com.cincuentazo.controller;
    exports com.cincuentazo.model;
    exports com.cincuentazo.exceptions;
}   