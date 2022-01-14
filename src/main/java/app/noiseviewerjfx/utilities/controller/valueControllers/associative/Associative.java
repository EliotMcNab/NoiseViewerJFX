package app.noiseviewerjfx.utilities.controller.valueControllers.associative;

import app.noiseviewerjfx.utilities.controller.valueControllers.Updatable;

public interface Associative extends Updatable {

    boolean addAssociatedNode(Associable associatedNode);

    boolean addAllAssociatedNodes(Associable... associatedNodes);

    boolean hasAssociatedNode();

    @Override
    default boolean canBeRegistered() {
        return hasAssociatedNode();
    }
}
