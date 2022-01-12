package app.noiseviewerjfx.utilities.controller.valueControllers.associative;

import app.noiseviewerjfx.utilities.controller.valueControllers.ProgressBarValueController;
import app.noiseviewerjfx.utilities.controller.valueControllers.ValueController;
import javafx.scene.control.ProgressBar;

public class AssociativeProgressBar extends ProgressBarValueController implements Associative{

    private ValueController associatedNode;
    private int lastNodeState;

    public AssociativeProgressBar(ProgressBar linkedProgressBar) {
        super(linkedProgressBar);
    }

    @Override
    public void setAssociateNode(ValueController associatedNode) {

        if (associatedNode == this) {
            System.out.printf("ERROR: cannot register associated node for %s\n", this);
            return;
        }

        this.associatedNode = associatedNode;
        lastNodeState = associatedNode.getCurrentState();
        syncProgressBarValue();
    }

    @Override
    public boolean hasAssociatedNode() {
        return associatedNode != null;
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
