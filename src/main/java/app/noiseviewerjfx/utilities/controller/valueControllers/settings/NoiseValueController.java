package app.noiseviewerjfx.utilities.controller.valueControllers.settings;

import app.noiseviewerjfx.utilities.controller.valueControllers.Updatable;
import app.noiseviewerjfx.utilities.controller.valueControllers.ValueController;

public class NoiseValueController extends ValueController implements Updatable {

    private final ValueController OCTAVE;
    private final ValueController PERSISTENCE;
    private final ValueController SEED;
    private final ValueController MAP_WIDTH;
    private final ValueController MAP_HEIGHT;

    private int lastOctaveState;
    private int lastPersistenceState;
    private int lastSeedState;
    private int lastMapWidthState;
    private int lastMapHeightState;

    public NoiseValueController(
            ValueController octave,
            ValueController persistence,
            ValueController seed,
            ValueController mapWidth,
            ValueController mapHeight
    ) {
        this.OCTAVE         = octave;
        this.PERSISTENCE    = persistence;
        this.SEED           = seed;
        this.MAP_WIDTH      = mapWidth;
        this.MAP_HEIGHT     = mapHeight;

        this.lastOctaveState        = OCTAVE.getCurrentState();
        this.lastPersistenceState   = PERSISTENCE.getCurrentState();
        this.lastSeedState          = SEED.getCurrentState();
        this.lastMapWidthState      = MAP_WIDTH.getCurrentState();
        this.lastMapHeightState     = MAP_HEIGHT.getCurrentState();
    }

    public record NoiseValues(
            int OCTAVES,
            double PERSISTENCE,
            int SEED,
            int MAP_WIDTH,
            int MAP_HEIGHT
    ) { }

    @Override
    public Object getObjectValue() {
        return new NoiseValues(
                getOctaves(),
                getPersistence(),
                getSeed(),
                getMapWidth(),
                getMapHeight()
        );
    }

    private int getOctaves() {
        return (int) OCTAVE.getValue();
    }

    private double getPersistence() {
        return PERSISTENCE.getValue();
    }

    private int getSeed() {
        return (int) SEED.getValue();
    }

    private int getMapWidth() {
        return (int) MAP_WIDTH.getValue();
    }

    private int getMapHeight() {
        return (int) MAP_HEIGHT.getValue();
    }

    private boolean changeOccurred() {

        if (hasUpdated(lastOctaveState, OCTAVE.getCurrentState())) {
            lastOctaveState = OCTAVE.getCurrentState();
            return true;
        } else if (hasUpdated(lastPersistenceState, PERSISTENCE.getCurrentState())) {
            lastPersistenceState = PERSISTENCE.getCurrentState();
            return true;
        } else if (hasUpdated(lastSeedState, SEED.getCurrentState())) {
            lastSeedState = SEED.getCurrentState();
            return true;
        } else if (hasUpdated(lastMapWidthState, MAP_WIDTH.getCurrentState())) {
            lastMapWidthState = MAP_WIDTH.getCurrentState();
            return true;
        } else if (hasUpdated(lastMapHeightState, MAP_HEIGHT.getCurrentState())) {
            lastMapHeightState = MAP_HEIGHT.getCurrentState();
            return true;
        }

        return false;
    }

    @Override
    public void update() {
        if (changeOccurred()) newState();
    }
}
