package app.noiseviewerjfx.utilities.controller.valueControllers;

import javafx.beans.value.ChangeListener;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import org.kordamp.ikonli.javafx.FontIcon;

public class ButtonValueController extends ValueController {

    protected final Button BUTTON;

    public ButtonValueController(
            Button button
    ) {
        this.BUTTON = button;

        addListeners();
    }

    private void addListeners() {
        BUTTON.armedProperty().addListener((observableValue, wasPressed, pressed) -> {
            if (pressed) {
                onButtonPress();
            } else if (wasPressed) {
                onButtonRelease();
            }
        });
    }

    protected void onButtonPress() {
        newState();
    }

    protected void onButtonRelease() {

    }

}
