package app.noiseviewerjfx.utilities.controller.valueControllers;

/**
 * The Updatable interface represents the ability of a class to have its value changed by exterior sources over time <br>
 * <i>see similar: {@link Evolving}</i>
 */
public interface Updatable {

    void update();

    default boolean hasUpdated(int lastState, int currentState) {
        return lastState != currentState;
    }

    default boolean canBeRegistered() {
        return true;
    }
}
