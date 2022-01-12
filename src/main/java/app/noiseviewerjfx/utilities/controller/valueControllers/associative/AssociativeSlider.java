package app.noiseviewerjfx.utilities.controller.valueControllers.associative;

import app.noiseviewerjfx.utilities.controller.valueControllers.SliderValueController;
import app.noiseviewerjfx.utilities.controller.valueControllers.ValueController;
import javafx.scene.control.Slider;

public class AssociativeSlider extends SliderValueController implements Associative{

    private ValueController associatedNode;
    private int lastNodeState;

    /**
     * Create a new SliderValueController with a node associated to it
     * @param linkedSlider   (Slider): the slider associated to the SliderValueController
     */
    public AssociativeSlider(Slider linkedSlider) {
        super(linkedSlider);
    }

    @Override
    public void setAssociateNode(ValueController associatedNode) {

        if (associatedNode == this) {
            System.out.printf("ERROR: cannot register associated node for %s\n", this);
            return;
        }

        this.associatedNode = associatedNode;
        lastNodeState = associatedNode.getCurrentState();
        syncSliderValue();
    }

    @Override
    public boolean hasAssociatedNode() {
        return associatedNode != null;
    }

    @Override
    public void update() {

        int currentNodeState = associatedNode.getCurrentState();

        if (!hasUpdated(lastNodeState, currentNodeState)) return;

        syncSliderValue();
        lastNodeState = currentNodeState;

    }

    private void syncSliderValue() {
        setValue(associatedNode.getValue());
    }
}
