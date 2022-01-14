package app.noiseviewerjfx.utilities.controller.valueControllers;

import app.noiseviewerjfx.utilities.controller.valueControllers.associative.Associable;
import javafx.scene.control.ProgressBar;

public class ProgressBarValueController extends ValueController implements Associable {

    private final ProgressBar PROGRESS_BAR;

    public ProgressBarValueController(ProgressBar linkedProgressBar) {
        this.PROGRESS_BAR = linkedProgressBar;
    }

    @Override
    public double getValue() {
        return PROGRESS_BAR.getProgress();
    }

    @Override
    protected boolean setValue(double value) {
        PROGRESS_BAR.setProgress(value);
        return true;
    }
}
