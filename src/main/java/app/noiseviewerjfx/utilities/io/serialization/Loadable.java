package app.noiseviewerjfx.utilities.io.serialization;

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

    default boolean restoreToState(State state){
        return false;
    }

}
