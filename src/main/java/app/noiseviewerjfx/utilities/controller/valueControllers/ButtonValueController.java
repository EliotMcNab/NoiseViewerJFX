package app.noiseviewerjfx.utilities.controller.valueControllers;

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

    protected void addListeners() {
        BUTTON.setOnAction(selectButton());
    }

    private EventHandler<ActionEvent> selectButton() {
        return actionEvent -> {
            newState();
        };
    }

}
