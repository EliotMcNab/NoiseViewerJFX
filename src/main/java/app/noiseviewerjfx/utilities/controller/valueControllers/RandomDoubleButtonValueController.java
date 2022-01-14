package app.noiseviewerjfx.utilities.controller.valueControllers;

import app.noiseviewerjfx.utilities.controller.valueControllers.associative.Associable;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;

public class RandomDoubleButtonValueController extends ButtonValueController implements Associable {

    private double randomValue;
    private final double MIN_VALUE;
    private final double MAX_VALUE;

    // =================================
    //          CONSTRUCTOR
    // =================================

    /**
     * Creates a new ButtonValueController
     * @param linkedButton (Button): the button associated to the ButtonValueController
     */
    public RandomDoubleButtonValueController(
            Button linkedButton,
            double minValue,
            double maxValue) {

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
        randomValue = MIN_VALUE + Math.random() * (MAX_VALUE - MIN_VALUE);
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
