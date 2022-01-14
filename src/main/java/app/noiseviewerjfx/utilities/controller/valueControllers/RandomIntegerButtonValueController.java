package app.noiseviewerjfx.utilities.controller.valueControllers;

import app.noiseviewerjfx.utilities.controller.valueControllers.associative.Associable;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;

public class RandomIntegerButtonValueController extends ButtonValueController implements Associable {

    private int randomValue;
    private final int MIN_VALUE;
    private final int MAX_VALUE;

    // =================================
    //          CONSTRUCTOR
    // =================================

    /**
     * Creates a new ButtonValueController
     * @param linkedButton (Button): the button associated to the ButtonValueController
     */
    public RandomIntegerButtonValueController(
            Button linkedButton,
            int minValue,
            int maxValue) {

        super(linkedButton);

        this.MIN_VALUE = minValue;
        this.MAX_VALUE = maxValue;
        randomValue = minValue;

        addListeners();
    }

    // =================================
    //              VALUE
    // =================================

    @Override
    public double getValue() {
        return randomValue;
    }

    private void generateRandomValue() {
        randomValue = MIN_VALUE + (int) (Math.random() * (MAX_VALUE - MIN_VALUE));
    }

    // =================================
    //            LISTENERS
    // =================================

    /**
     * Adds the necessary listeners to the button
     */
    @Override
    protected void addListeners() {
        BUTTON.setOnMouseClicked(update());
    }

    /**
     * Updates the state of the button whenever the user clicks on it
     * @return (EventHandler(MouseEvent)): event handler for mouse presses
     */
    private EventHandler<MouseEvent> update() {
        return mouseEvent -> {
            newState();
            generateRandomValue();
        };
    }
}
