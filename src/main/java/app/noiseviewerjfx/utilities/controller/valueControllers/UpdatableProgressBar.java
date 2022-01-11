package app.noiseviewerjfx.utilities.controller.valueControllers;

import javafx.scene.control.ProgressBar;

public class UpdatableProgressBar extends ProgressBarValueController implements Updatable{

    private ValueController associatedNode;
    private int lastNodeState;

    public UpdatableProgressBar(ProgressBar linkedProgressBar) {
        super(linkedProgressBar);
    }

    @Override
    public void setAssociateNode(ValueController associatedNode) {
        this.associatedNode = associatedNode;
        lastNodeState = associatedNode.getCurrentState();
        syncProgressBarValue();
    }

    @Override
    public void update() {

        int currentNodeState = associatedNode.getCurrentState();

        if (!hasUpdated(lastNodeState, currentNodeState)) return;

        syncProgressBarValue();
        lastNodeState = currentNodeState;

    }

    private void syncProgressBarValue() {
        setValue(associatedNode.getValue() / 100);
    }
}
