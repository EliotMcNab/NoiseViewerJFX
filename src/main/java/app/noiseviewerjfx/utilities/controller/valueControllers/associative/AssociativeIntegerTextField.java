package app.noiseviewerjfx.utilities.controller.valueControllers.associative;

import app.noiseviewerjfx.utilities.controller.valueControllers.IntegerTextFieldValueController;
import app.noiseviewerjfx.utilities.controller.valueControllers.ValueController;
import javafx.scene.control.TextField;

public class AssociativeIntegerTextField extends IntegerTextFieldValueController implements Associative{

    private ValueController associatedNode;
    private int lastNodeState;

    /**
     * Creates a new IntegerTextFieldValueController with no node associated to it
     *
     * @param linkedTextField (TextField): the text field associated to the IntegerTextFieldValueController
     * @param defaultValue    (int): fallback value in case of invalid format
     * @param unit            (String): unit shown in the text field, "" for no unit
     */
    public AssociativeIntegerTextField(TextField linkedTextField, int defaultValue, String unit) {
        super(linkedTextField, defaultValue, unit);
    }

    @Override
    public void setAssociateNode(ValueController associatedNode) {

        if (associatedNode == this) {
            System.out.printf("ERROR: cannot register associated node for %s\n", this);
            return;
        }

        this.associatedNode = associatedNode;
        lastNodeState = associatedNode.getCurrentState();
        syncTextFieldValue();
    }

    @Override
    public boolean hasAssociatedNode() {
        return associatedNode != null;
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
