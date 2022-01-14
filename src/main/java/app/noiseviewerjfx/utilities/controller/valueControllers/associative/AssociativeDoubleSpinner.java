package app.noiseviewerjfx.utilities.controller.valueControllers.associative;

import app.noiseviewerjfx.utilities.ComplementaryMath;
import app.noiseviewerjfx.utilities.controller.valueControllers.DoubleSpinnerValueController;
import app.noiseviewerjfx.utilities.controller.valueControllers.ValueController;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AssociativeDoubleSpinner extends DoubleSpinnerValueController implements Associative {

    private final Map<Associable, Integer> associatedNodes = new HashMap<>();

    /**
     * Creates a new IntegerSpinnerValueController with no node associated to it
     * @param linkedSpinner (TextField): the text field associated to the IntegerTextFieldValueController
     * @param defaultValue (int): fallback value in case of invalid format
     * @param unit (String): unit shown in the text field
     */
    public AssociativeDoubleSpinner(
            Spinner<Double> linkedSpinner,
            SpinnerValueFactory<Double> valueFactory,
            double defaultValue,
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
            setValue(associatedNode.getValue());
        }

    }

    private void syncSpinnerValue() {
        for (Associable associatedNode : associatedNodes.keySet()) {
            setValue(ComplementaryMath.roundToPrecision(associatedNode.getValue(), 2));
            return;
        }
    }
}
