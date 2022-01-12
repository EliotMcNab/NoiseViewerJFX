package app.noiseviewerjfx.utilities.controller.valueControllers;

import javafx.scene.control.ProgressBar;

public class ProgressBarValueController extends ValueController{

    private final ProgressBar PROGRESS_BAR;

    public ProgressBarValueController(ProgressBar linkedProgressBar) {
        this.PROGRESS_BAR = linkedProgressBar;
    }

    @Override
    public double getValue() {
        return PROGRESS_BAR.getProgress();
    }

    @Override
    protected void setValue(double value) {
        PROGRESS_BAR.setProgress(value);
    }
}
