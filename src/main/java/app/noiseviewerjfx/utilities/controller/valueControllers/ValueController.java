package app.noiseviewerjfx.utilities.controller.valueControllers;

abstract public class ValueController {

    private int currentState = 0;

    public abstract double getValue();

    protected abstract void setValue(double value);

    protected final void newState() {
        currentState++;
        currentState %= 100;
    }

    public final int getCurrentState() {
        return currentState;
    }

}
