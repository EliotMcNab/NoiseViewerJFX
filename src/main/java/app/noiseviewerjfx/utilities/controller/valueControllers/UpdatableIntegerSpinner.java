package app.noiseviewerjfx.utilities.controller.valueControllers;

import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;

public class UpdatableIntegerSpinner extends IntegerSpinnerValueController implements Updatable {

    private ValueController associatedNode;
    private int lastNodeState;

    /**
     * Creates a new IntegerSpinnerValueController with no node associated to it
     * @param linkedSpinner (TextField): the text field associated to the IntegerTextFieldValueController
     * @param defaultValue (int): fallback value in case of invalid format
     * @param unit (String): unit shown in the text field
     */
    public UpdatableIntegerSpinner(
            Spinner<Integer> linkedSpinner,
            SpinnerValueFactory<Integer> valueFactory,
            int defaultValue,
            String unit) {

        super(linkedSpinner, valueFactory, defaultValue, unit);
    }

    @Override
    public void setAssociateNode(ValueController associatedNode) {
        this.associatedNode = associatedNode;
        lastNodeState = associatedNode.getCurrentState();
        syncSpinnerValue();
    }

    @Override
    public void update() {

        int currentNodeState = associatedNode.getCurrentState();

        if (!hasUpdated(lastNodeState, currentNodeState)) return;

        syncSpinnerValue();
        lastNodeState = currentNodeState;

    }

    private void syncSpinnerValue() {
        setValue((int) associatedNode.getValue());
    }

}
