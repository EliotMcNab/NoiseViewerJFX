package app.noiseviewerjfx.utilities.tasks;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * An UpdateManager is responsible for updating the active {@link UpdateScene UpdateScenes} which compose the UI
 */
public class UpdateManager {

    private final List<UpdateScene> stages = new ArrayList<>();
    private final List<Persistent> persistentsCompoenents = new ArrayList<>();

    public UpdateManager(UpdateScene... updateStages) {
        registerAll(updateStages);
    }

    public boolean registerStage(UpdateScene toRegister) {
        return stages.add(toRegister);
    }

    public boolean registerAll(List<UpdateScene> toRegister) {
        return stages.addAll(toRegister);
    }

    public boolean registerAll(UpdateScene... toRegister) {
        return stages.addAll(Arrays.asList(toRegister));
    }

    public boolean registerPersistent(Persistent persistentComponent) {
        return persistentsCompoenents.add(persistentComponent);
    }

    public void update() {
        for (UpdateScene stage : stages) {
            if (stage.isActive()) stage.update();
        }
        for (Persistent persistentsCompoenent : persistentsCompoenents) {
            persistentsCompoenent.update();
        }
    }

    public void startUpdating() {
        PeriodicTask periodicTask = new PeriodicTask(10) {
            @Override
            public void run() {
                update();
            }
        };
        periodicTask.start();
    }

}
