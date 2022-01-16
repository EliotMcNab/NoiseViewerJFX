package app.noiseviewerjfx.utilities.controller.valueControllers;

import app.noiseviewerjfx.utilities.io.serialization.Loadable;
import app.noiseviewerjfx.utilities.io.serialization.Save;

abstract public class ValueController implements Evolving, Loadable {

    private int currentState = 0;

    public final void newState() {
        currentState++;
        currentState %= 100;
    }

    public final int getCurrentState() {
        return currentState;
    }

    protected boolean setValue(double value) {
        return false;
    }
}
