package app.noiseviewerjfx.utilities.io.serialization;

import app.noiseviewerjfx.utilities.controller.valueControllers.Evolving;

/**
 * The loadable Interface represents a class' ability to return to a previously stored {@link State}
 */
public interface Loadable extends Evolving {

    default Save save() {
        return null;
    };

    default boolean load(Save save) {
        return false;
    }

    default boolean restoreToState(State state){
        return false;
    }

}
