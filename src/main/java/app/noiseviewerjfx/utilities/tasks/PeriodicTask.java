package app.noiseviewerjfx.utilities.tasks;

import javafx.animation.AnimationTimer;

public abstract class PeriodicTask extends AnimationTimer {

    private final long NANOS_BETWEEN_TASKS;
    private long timeLastTask = 0;

    public PeriodicTask(long millis) {
        NANOS_BETWEEN_TASKS = millis * 1000000;
    }

    private boolean isTimeForTask(long l) {
        return l - timeLastTask >= NANOS_BETWEEN_TASKS;
    }

    @Override
    public void handle(long l) {

        if (!isTimeForTask(l)) return;

        timeLastTask = l;

        run();
    }

    public abstract void run();
}
