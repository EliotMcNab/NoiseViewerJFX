package app.noiseviewerjfx.utilities.controller.valueControllers.associative.switches;

import app.noiseviewerjfx.utilities.controller.valueControllers.CheckBoxValueController;
import app.noiseviewerjfx.utilities.controller.valueControllers.associative.Associable;
import javafx.scene.control.CheckBox;

import java.util.HashMap;
import java.util.Map;

public class SwitchCheckBox extends CheckBoxValueController implements Switch {

    private final Map<Associable, Integer> associatedNodes = new HashMap<>();

    public SwitchCheckBox(CheckBox linkedCheckBox) {
        super(linkedCheckBox);
    }

    @Override
    public boolean addAssociatedNode(Associable associatedNode) {
        if (associatedNode == this) return false;
        boolean errorHappen = associatedNodes.put(associatedNode, associatedNode.getCurrentState()) != null;
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

        return !errorHappen;
    }

    @Override
    public boolean hasAssociatedNode() {
        return associatedNodes.size() != 0;
    }

    @Override
    public void update() {

        for (Associable associateNode : associatedNodes.keySet()) {
            if (!hasUpdated(associatedNodes.get(associateNode), associateNode.getCurrentState())) continue;

            associatedNodes.replace(associateNode, associateNode.getCurrentState());
            if (associateNode.getValue() >= 1) {
                setValue(0);
            }
        }
        
    }
}
