package app.noiseviewerjfx.utilities.controller.valueControllers;

public interface Evolving {

    void newState();
    int getCurrentState();

    default double getValue() {
        return Double.NaN;
    };

    default Object getObjectValue() {
        return null;
    }
}
