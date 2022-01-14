package app.noiseviewerjfx.utilities.controller.valueControllers;

abstract public class ValueController implements Evolving{

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
