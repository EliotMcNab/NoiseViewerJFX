package app.noiseviewerjfx.utilities.controller.valueControllers.associative;

import app.noiseviewerjfx.utilities.controller.valueControllers.IntegerTextFieldValueController;
import app.noiseviewerjfx.utilities.controller.valueControllers.ValueController;
import javafx.scene.control.TextField;

import java.util.HashMap;
import java.util.Map;

public class AssociativeIntegerTextField extends IntegerTextFieldValueController implements Associative{

    private final Map<Associable, Integer> associatedNodes = new HashMap<>();

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
    public boolean addAssociatedNode(Associable associatedNode) {
        if (associatedNode == this) return false;
        syncTextFieldValue();
        return associatedNodes.put(associatedNode, associatedNode.getCurrentState()) != null;
    }

    @Override
    public boolean addAllAssociatedNodes(Associable... associatedNodes) {
        boolean errorHappen = false;
        for (Associable associatedNode : associatedNodes) {
            if (associatedNode == this) {
                errorHappen = false;
                continue;
            }
            errorHappen = this.associatedNodes.put(associatedNode, associatedNode.getCurrentState()) != null | errorHappen;
        }

        syncTextFieldValue();

        return !errorHappen;
    }

    @Override
    public boolean hasAssociatedNode() {
        return associatedNodes.size() != 0;
    }

    @Override
    public void update() {

        for (Associable associatedNode : associatedNodes.keySet()) {
            if (!hasUpdated(associatedNodes.get(associatedNode), associatedNode.getCurrentState())) continue;

            associatedNodes.replace(associatedNode, associatedNode.getCurrentState());
            setValue((int) associatedNode.getValue());
        }

    }

    private void syncTextFieldValue() {
        for (Associable associatedNode : associatedNodes.keySet()) {
            setValue((int) associatedNode.getValue());
            return;
        }
    }
}
