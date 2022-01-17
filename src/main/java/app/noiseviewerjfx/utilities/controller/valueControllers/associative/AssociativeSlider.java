package app.noiseviewerjfx.utilities.controller.valueControllers.associative;

import app.noiseviewerjfx.utilities.controller.valueControllers.SliderValueController;
import app.noiseviewerjfx.utilities.controller.valueControllers.ValueController;
import javafx.scene.control.Slider;

import java.util.HashMap;
import java.util.Map;

public class AssociativeSlider extends SliderValueController implements Associative{

    public final Map<Associable, Integer> associatedNodes = new HashMap<>();
    /**
     * Create a new SliderValueController with a node associated to it
     * @param linkedSlider   (Slider): the slider associated to the SliderValueController
     */
    public AssociativeSlider(Slider linkedSlider) {
        super(linkedSlider);
    }

    @Override
    public boolean addAssociatedNode(Associable associatedNode) {
        if (associatedNode == this) return false;
        associatedNodes.put(associatedNode, associatedNode.getCurrentState());
        syncSliderValue();
        return true;
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

        syncSliderValue();

        return !errorHappen;
    }

    @Override
    public boolean removeAssociatedNode(Associable associated) {
        return associatedNodes.remove(associated) != null;
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

    private void syncSliderValue() {
        for (Associable associatedNode : associatedNodes.keySet()) {
            setValue(associatedNode.getValue());
        }
    }
}
