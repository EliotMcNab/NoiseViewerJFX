package app.noiseviewerjfx.utilities.tasks;

import app.noiseviewerjfx.utilities.controller.valueControllers.Updatable;

/**
 * The Persistent interface is used to signal a specific type of {@link Updatable} {@link java.awt.Component Component}
 * which is constantly being loaded by an {@link UpdateManager}
 */
public interface Persistent extends Updatable {
}
