package app.noiseviewerjfx.utilities.tasks;

import app.noiseviewerjfx.utilities.controller.valueControllers.Updatable;
import app.noiseviewerjfx.utilities.controller.valueControllers.ValueController;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class UpdateManager {

    private final List<Updatable> updatables = new ArrayList<>();

    public boolean registerUpdatable(Updatable toRegister) {
        return updatables.add(toRegister);
    }

    public boolean registerUpdatable(Updatable toRegister, ValueController associatedNode) {
        toRegister.setAssociateNode(associatedNode);

        return updatables.add(toRegister);
    }

    public boolean registerAll(List<Updatable> toRegister) {
        return updatables.addAll(toRegister);
    }

    public boolean registerAll(Updatable... toRegister) {
        return updatables.addAll(Arrays.asList(toRegister));
    }

    public void update() {
        for (Updatable updatable : updatables) {
            updatable.update();
        }
    }

}
