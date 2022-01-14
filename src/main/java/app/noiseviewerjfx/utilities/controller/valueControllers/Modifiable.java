package app.noiseviewerjfx.utilities.controller.valueControllers;

public interface Modifiable {

    default boolean setValue(double value) {
        return false;
    }

    default boolean setObjectValue(Object value) {
        return false;
    }

}
