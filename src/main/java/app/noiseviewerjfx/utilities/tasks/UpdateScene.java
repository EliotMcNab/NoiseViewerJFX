package app.noiseviewerjfx.utilities.tasks;

import app.noiseviewerjfx.utilities.controller.valueControllers.Updatable;

import java.util.ArrayList;
import java.util.List;

/**
 * An UpdateScene represents a JavaFx {@link java.awt.Container Container} and is responsible
 * for updating its {@link java.awt.Component Components}. <br>
 * It should be set to be active or inactive based on whether that part of the UI is currently visible to the user <br>
 * <i>Setting UpdateScenes to be active or inactive should be handled by an {@link UpdateStage}<i/><br>
 * <i>Updating an UpdateScene is handled within an {@link UpdateManager}</i>
 */
public class UpdateScene implements Updatable {

    private final List<Updatable> stageComponents = new ArrayList<>();
    private boolean isActive = false;

    public UpdateScene(Updatable... updatables) {
        registerAll(updatables);
    }

    public boolean registerAll(Updatable... updatables) {
        boolean errorHappen = false;
        for (Updatable updatable : updatables) {
            errorHappen = tryRegister(updatable) || errorHappen;
        }
        return errorHappen;
    }

    public boolean register(Updatable updatable) {
        return tryRegister(updatable);
    }

    private boolean tryRegister(Updatable updatable) {
        if (!updatable.canBeRegistered()) {
            System.out.printf("ERROR: could not add node %s to update stage\n", updatable);
            return false;
        }

        return stageComponents.add(updatable);
    }

    public void activate() {
        isActive = true;
    }

    public void deactivate() {
        isActive = false;
    }

    public boolean isActive() {
        return isActive;
    }

    @Override
    public void update() {
        for (Updatable stageComponent : stageComponents) {
            stageComponent.update();
        }
    }

}
