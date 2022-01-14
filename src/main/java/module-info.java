module app.noiseviewerjfx {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.desktop;
    requires org.kordamp.ikonli.javafx;


    opens app.noiseviewerjfx to javafx.fxml;
    exports app.noiseviewerjfx.utilities.controller;
    opens app.noiseviewerjfx.utilities.controller to javafx.fxml;
    opens app.noiseviewerjfx.utilities to javafx.fxml;
    exports app.noiseviewerjfx;
    exports app.noiseviewerjfx.utilities.controller.valueControllers;
    opens app.noiseviewerjfx.utilities.controller.valueControllers to javafx.fxml;
    exports app.noiseviewerjfx.utilities.controller.valueControllers.associative;
    opens app.noiseviewerjfx.utilities.controller.valueControllers.associative to javafx.fxml;
    exports app.noiseviewerjfx.utilities.controller.valueControllers.settings;
    opens app.noiseviewerjfx.utilities.controller.valueControllers.settings to javafx.fxml;
}