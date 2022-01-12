package app.noiseviewerjfx.utilities.controller.valueControllers.associative;

import app.noiseviewerjfx.utilities.controller.valueControllers.Updatable;
import app.noiseviewerjfx.utilities.controller.valueControllers.ValueController;

public interface Associative extends Updatable {

    void setAssociateNode(ValueController associatedNode);

    boolean hasAssociatedNode();

    @Override
    default boolean canBeRegistered() {
        return hasAssociatedNode();
    }
}
