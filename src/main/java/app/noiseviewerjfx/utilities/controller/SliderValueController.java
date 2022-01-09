package app.noiseviewerjfx.utilities.controller;

import javafx.beans.value.ChangeListener;
import javafx.scene.Cursor;
import javafx.scene.Node;
import javafx.scene.control.Slider;
import javafx.scene.control.Spinner;

public class SliderValueController {

    private final Slider SLIDER;
    private final Node ASSOCIATED_NODE;

    public SliderValueController(Slider linkedSlider, Node associatedNode) {
        this.SLIDER = linkedSlider;
        this.ASSOCIATED_NODE = associatedNode;

        styleSlider();
        addAssociatedListeners();
    }

    private void styleSlider() {
        SLIDER.setOnMouseEntered(NodeController.changeNodeCursor(SLIDER, Cursor.H_RESIZE));
        SLIDER.setSnapToTicks(true);
    }

    private void addListeners() {

    }

    private void addAssociatedListeners() {
        if (ASSOCIATED_NODE instanceof Spinner<?>) {
            addSpinnerListeners();
        }
    }

    private void addSpinnerListeners() {
        Spinner<Integer> associatedSpinner = (Spinner<Integer>) ASSOCIATED_NODE;
        SLIDER.valueProperty().addListener(updateAssociatedSpinner(associatedSpinner));
    }

    /**
     * Updates the spinner associated to this slider
     * @param targetSpinner (Spinner): the associated spinner
     * @return (ChangeListener(Number)): listener for a change in the slider's value
     */
    public static ChangeListener<Number> updateAssociatedSpinner(Spinner<Integer> targetSpinner) {
        return (observableValue, oldValue, newValue) -> targetSpinner.getValueFactory().setValue(newValue.intValue());
    }

}
