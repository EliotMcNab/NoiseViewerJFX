package app.noiseviewerjfx.utilities.controller.valueControllers;

import javafx.scene.Node;
import javafx.scene.control.Slider;

public class UpdatableSlider extends SliderValueController implements Updatable{

    private ValueController associatedNode;
    private int lastNodeState;

    /**
     * Create a new SliderValueController with a node associated to it
     * @param linkedSlider   (Slider): the slider associated to the SliderValueController
     */
    public UpdatableSlider(Slider linkedSlider) {
        super(linkedSlider);
    }

    @Override
    public void setAssociateNode(ValueController associatedNode) {
        this.associatedNode = associatedNode;
        lastNodeState = associatedNode.getCurrentState();
        syncSliderValue();
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
