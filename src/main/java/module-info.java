module app.noiseviewerjfx {
    requires javafx.controls;
    requires javafx.fxml;


    opens app.noiseviewerjfx to javafx.fxml;
    exports app.noiseviewerjfx;
    exports app.noiseviewerjfx.controller;
    opens app.noiseviewerjfx.controller to javafx.fxml;
}