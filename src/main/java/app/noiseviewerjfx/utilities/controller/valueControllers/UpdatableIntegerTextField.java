package app.noiseviewerjfx.utilities.controller.valueControllers;

import javafx.scene.control.TextField;

public class UpdatableIntegerTextField extends IntegerTextFieldValueController implements Updatable{

    private ValueController associatedNode;
    private int lastNodeState;

    /**
     * Creates a new IntegerTextFieldValueController with no node associated to it
     *
     * @param linkedTextField (TextField): the text field associated to the IntegerTextFieldValueController
     * @param defaultValue    (int): fallback value in case of invalid format
     * @param unit            (String): unit shown in the text field, "" for no unit
     */
    public UpdatableIntegerTextField(TextField linkedTextField, int defaultValue, String unit) {
        super(linkedTextField, defaultValue, unit);
    }

    @Override
    public void setAssociateNode(ValueController associatedNode) {
        this.associatedNode = associatedNode;
        lastNodeState = associatedNode.getCurrentState();
        syncTextFieldValue();
    }

    @Override
    public void update() {

        int currentNodeState = associatedNode.getCurrentState();

        if (!hasUpdated(lastNodeState, currentNodeState)) return;

        syncTextFieldValue();
        lastNodeState = currentNodeState;

    }

    private void syncTextFieldValue() {
        setValue(associatedNode.getValue());
    }
}
