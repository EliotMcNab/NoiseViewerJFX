package app.noiseviewerjfx.utilities.tasks;

import app.noiseviewerjfx.utilities.controller.valueControllers.Updatable;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class UpdateManager {

    private final List<Updatable> updatableNodes = new ArrayList<>();

    public boolean registerUpdatable(Updatable toRegister) {
        return tryRegister(toRegister);
    }

    public boolean registerAll(List<Updatable> toRegister) {
        boolean errorHappen = false;

        for (Updatable updatable : toRegister) {
            errorHappen = !tryRegister(updatable);
        }

        return !errorHappen;
    }

    public boolean registerAll(Updatable... toRegister) {
        return registerAll(Arrays.stream(toRegister).toList());
    }

    private boolean tryRegister(Updatable toRegister) {
        if (!toRegister.canBeRegistered()) {
            System.out.printf("ERROR: cannot register associative node %s since it has no node associated to it", toRegister);
            return false;
        }
        return updatableNodes.add(toRegister);
    }

    public void update() {
        for (Updatable updatable : updatableNodes) {
            updatable.update();
        }
    }

}
