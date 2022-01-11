package app.noiseviewerjfx.utilities.controller.valueControllers;

import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;

public class RandomIntegerButtonValueController extends ValueController{

    private final Button BUTTON;
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
    public RandomIntegerButtonValueController(Button linkedButton, int minValue, int maxValue) {
        this.BUTTON = linkedButton;
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

    @Override
    protected void setValue(double value) {

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
    private void addListeners() {
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
