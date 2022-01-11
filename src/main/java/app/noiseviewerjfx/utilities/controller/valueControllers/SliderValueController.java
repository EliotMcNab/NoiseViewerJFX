package app.noiseviewerjfx.utilities.controller.valueControllers;

import app.noiseviewerjfx.utilities.controller.NodeController;
import javafx.beans.value.ChangeListener;
import javafx.scene.Cursor;
import javafx.scene.Node;
import javafx.scene.control.Slider;
import javafx.scene.control.Spinner;

public class SliderValueController extends ValueController {

    private final Slider SLIDER;

    /**
     * Create a new SliderValueController with a node associated to it
     * @param linkedSlider (Slider): the slider associated to the SliderValueController
     */
    public SliderValueController(Slider linkedSlider) {
        this.SLIDER = linkedSlider;

        styleSlider();
        addListeners();
    }

    // =================================
    //              VALUE
    // =================================

    /**
     * Gets the value contained in the slider
     * @return (double): the value of the slider
     */
    public double getValue() {
        return SLIDER.getValue();
    }

    @Override
    protected void setValue(double value) {
        SLIDER.setValue(value);
    }

    // =================================
    //              STYLE
    // =================================

    /**
     * Styles the slider
     */
    private void styleSlider() {
        SLIDER.setOnMouseEntered(NodeController.changeNodeCursor(SLIDER, Cursor.H_RESIZE));
        SLIDER.setSnapToTicks(true);
    }

    // =================================
    //           LISTENERS
    // =================================

    /**
     * Adds the necessary listeners to the slider
     */
    private void addListeners() {
       SLIDER.valueProperty().addListener(update());
    }

    /**
     * Updates the state of the slider if a change in value is detected
     * @return (ChangeListener(Number)): event listener for changes in the slider's value
     */
    private ChangeListener<Number> update() {
        return (observableValue, oldValue, newValue) -> {
            newState();
        };
    }
}
