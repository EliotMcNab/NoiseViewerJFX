package app.noiseviewerjfx.utilities.controller.valueControllers;

import app.noiseviewerjfx.utilities.controller.valueControllers.associative.Associable;
import javafx.scene.control.Button;

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

    @Override
    protected void onButtonPress() {
        update();
    }

    private void update() {
        newState();
        generateRandomValue();
    }

}
