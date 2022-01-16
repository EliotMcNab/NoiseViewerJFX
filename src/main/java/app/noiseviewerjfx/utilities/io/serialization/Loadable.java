package app.noiseviewerjfx.utilities.io.serialization;

import java.util.HashMap;

public interface Loadable {

    default Save save() {
        return null;
    };

    default State getState() {
        return null;
    }

    default boolean load(Save save) {
        return false;
    }

    default boolean returnToState(State state){
        return false;
    }

}
