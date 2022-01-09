module app.noiseviewerjfx {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.desktop;


    opens app.noiseviewerjfx to javafx.fxml;
    exports app.noiseviewerjfx.utilities.controller;
    opens app.noiseviewerjfx.utilities.controller to javafx.fxml;
    opens app.noiseviewerjfx.utilities to javafx.fxml;
    exports app.noiseviewerjfx;
 }