package app.noiseviewerjfx.utilities.controller.valueControllers;

import app.noiseviewerjfx.utilities.io.serialization.Loadable;
import app.noiseviewerjfx.utilities.io.serialization.Save;
import app.noiseviewerjfx.utilities.io.serialization.State;

abstract public class ValueController implements Evolving, Loadable {

    private int currentState = 0;
    private int currentVersion = 0;

    public final void newState() {
        currentState++;
        currentState %= 100;
    }

    public final int getCurrentState() {
        return currentState;
    }

    @Override
    public boolean restoreToState(State state) {
        newState();
        return setValue((Double) state.getVALUE());
    }

    @Override
    public State getState() {
        return new State(currentVersion++, getValue());
    }

    protected boolean setValue(double value) {
        return false;
    }
}
