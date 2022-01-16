package app.noiseviewerjfx.utilities.controller.valueControllers;

import javafx.scene.control.Button;

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
