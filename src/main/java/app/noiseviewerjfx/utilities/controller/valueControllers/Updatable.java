package app.noiseviewerjfx.utilities.controller.valueControllers;

public interface Updatable {

    void update();

    default boolean hasUpdated(int lastState, int currentState) {
        return lastState != currentState;
    }

    void setAssociateNode(ValueController associatedNode);

}
