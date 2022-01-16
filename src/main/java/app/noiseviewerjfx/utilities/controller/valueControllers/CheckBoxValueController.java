package app.noiseviewerjfx.utilities.controller.valueControllers;

import app.noiseviewerjfx.utilities.controller.valueControllers.associative.Associable;
import app.noiseviewerjfx.utilities.io.serialization.State;
import javafx.event.EventHandler;
import javafx.scene.control.CheckBox;
import javafx.scene.input.MouseEvent;

public class CheckBoxValueController extends ValueController implements Associable {

    private final CheckBox CHECKBOX;

    public CheckBoxValueController(CheckBox linkedCheckBox) {
        this.CHECKBOX = linkedCheckBox;

        addListeners();
    }

    private void addListeners() {
        CHECKBOX.setOnMouseClicked(update());
    }

    private EventHandler<MouseEvent> update() {
        return mouseEvent -> {
            newState();
        };
    }

    @Override
    public double getValue() {
        return CHECKBOX.isSelected() ? 1 : 0;
    }

    @Override
    protected boolean setValue(double value) {
        CHECKBOX.setSelected(value >= 1);
        newState();
        return true;
    }
}
