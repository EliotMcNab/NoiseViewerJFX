package app.noiseviewerjfx.utilities.controller.valueControllers;

import app.noiseviewerjfx.utilities.controller.valueControllers.associative.Associable;
import javafx.scene.control.Button;

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
    protected void onButtonPress() {
        update();
    }

    private void update() {
        newState();
        generateRandomValue();
    }
}
