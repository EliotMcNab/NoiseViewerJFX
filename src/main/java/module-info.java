module app.noiseviewerjfx {
    requires javafx.controls;
    requires javafx.fxml;


    opens app.noiseviewerjfx to javafx.fxml;
    exports app.noiseviewerjfx;
    exports app.noiseviewerjfx.utilities.controller;
    opens app.noiseviewerjfx.utilities.controller to javafx.fxml;
    exports app.noiseviewerjfx.codebehind;
    opens app.noiseviewerjfx.codebehind to javafx.fxml;
}