package app.noiseviewerjfx.utilities.controller.valueControllers;

import app.noiseviewerjfx.utilities.io.serialization.State;

/**
 * The Evolving interface represents the fact that the values associated to a class can change over time <br>
 * Every change in value in an Evolving class is considered as a new {@link app.noiseviewerjfx.utilities.io.serialization.State State} <br>
 * <i>see similar: {@link Updatable}</i>
 */
public interface Evolving {

    void newState();
    int getCurrentState();

    default double getValue() {
        return Double.NaN;
    };

    default State getState() {
        return null;
    }

    default Object getObjectValue() {
        return null;
    }
}
