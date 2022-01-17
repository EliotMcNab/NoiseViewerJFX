package app.noiseviewerjfx.utilities.tasks;

import java.util.HashMap;

/**
 * UpdateStages are responsible for setting which {@link UpdateScene UpdateScenes} are active <br>
 * Tha UpdateStage class is meant to serve as skeleton for UI-specific UpdateScene handlers
 */
public abstract class UpdateStage {

    private final HashMap<String, UpdateScene> SCENES = new HashMap<>();

    public boolean addScene(String key, UpdateScene scene) {
        SCENES.put(key, scene);
        return true;
    }

    protected boolean activateScene(String key) {
        if (!SCENES.containsKey(key)) return false;
        SCENES.get(key).activate();
        return true;
    }

    protected boolean deactivateScene(String key) {
        if (!SCENES.containsKey(key)) return false;
        SCENES.get(key).deactivate();
        return true;
    }
}
