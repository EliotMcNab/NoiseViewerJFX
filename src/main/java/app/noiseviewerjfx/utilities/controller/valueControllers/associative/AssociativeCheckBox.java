package app.noiseviewerjfx.utilities.controller.valueControllers.associative;

import app.noiseviewerjfx.utilities.controller.valueControllers.CheckBoxValueController;
import javafx.scene.control.CheckBox;

import java.util.HashMap;
import java.util.Map;

public class AssociativeCheckBox extends CheckBoxValueController implements Associative {

    private final Map<Associable, Integer> associatedNodes = new HashMap<>();

    public AssociativeCheckBox(CheckBox linkedCheckBox) {
        super(linkedCheckBox);
    }

    @Override
    public boolean addAssociatedNode(Associable associatedNode) {
        if (associatedNode == this) return false;
        boolean errorHappen = associatedNodes.put(associatedNode, associatedNode.getCurrentState()) != null;
        syncCheckBoxValue();
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

        syncCheckBoxValue();

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

    private void syncCheckBoxValue() {
        for (Associable associatedNode : associatedNodes.keySet()) {
            setValue(associatedNode.getValue());
        }
    }

}
