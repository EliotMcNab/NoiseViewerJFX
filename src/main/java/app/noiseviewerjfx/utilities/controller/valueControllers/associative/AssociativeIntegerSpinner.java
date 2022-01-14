package app.noiseviewerjfx.utilities.controller.valueControllers.associative;

import app.noiseviewerjfx.utilities.ComplementaryMath;
import app.noiseviewerjfx.utilities.controller.valueControllers.IntegerSpinnerValueController;
import app.noiseviewerjfx.utilities.controller.valueControllers.ValueController;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;

import java.util.HashMap;
import java.util.Map;

public class AssociativeIntegerSpinner extends IntegerSpinnerValueController implements Associative {

    private final Map<Associable, Integer> associatedNodes = new HashMap<>();

    /**
     * Creates a new IntegerSpinnerValueController with no node associated to it
     * @param linkedSpinner (TextField): the text field associated to the IntegerTextFieldValueController
     * @param defaultValue (int): fallback value in case of invalid format
     * @param unit (String): unit shown in the text field
     */
    public AssociativeIntegerSpinner(
            Spinner<Integer> linkedSpinner,
            SpinnerValueFactory<Integer> valueFactory,
            int defaultValue,
            String unit) {

        super(linkedSpinner, valueFactory, defaultValue, unit);
    }

    @Override
    public boolean addAssociatedNode(Associable associatedNode) {
        if (associatedNode == this) return false;
        boolean errorHappen = associatedNodes.put(associatedNode, associatedNode.getCurrentState()) != null;
        syncSpinnerValue();
        return errorHappen;
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

        syncSpinnerValue();

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

    private void syncSpinnerValue() {
        for (Associable associatedNode : associatedNodes.keySet()) {
            setValue((int) associatedNode.getValue());
            return;
        }
    }

}
