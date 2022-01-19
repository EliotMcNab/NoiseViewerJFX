package app.noiseviewerjfx.utilities.controller.valueControllers.settings;

import app.noiseviewerjfx.utilities.controller.valueControllers.Updatable;
import app.noiseviewerjfx.utilities.controller.valueControllers.ValueController;
import app.noiseviewerjfx.utilities.io.serialization.Save;

public class NoiseValueController extends ValueController implements Updatable {

    private final ValueController NOISE_RESET;
    private final ValueController SCALE;
    private final ValueController LACUNARITY;
    private final ValueController OCTAVE;
    private final ValueController PERSISTENCE;
    private final ValueController SEED;
    private final ValueController MAP_WIDTH;
    private final ValueController MAP_HEIGHT;

    private int lastNoiseResetState;
    private int lastScaleState;
    private int lastLacunarityState;
    private int lastOctaveState;
    private int lastPersistenceState;
    private int lastSeedState;
    private int lastMapWidthState;
    private int lastMapHeightState;

    private final String SCALE_KEY          = "SCALE";
    private final String LACUNARITY_KEY     = "LACUNARITY";
    private final String OCTAVES_KEY        = "OCTAVE_COUNT";
    private final String PERSISTENCE_KEY    = "PERSISTENCE";
    private final String SEED_KEY           = "SEED";
    private final String MAP_WIDTH_KEY      = "MAP_WIDTH";
    private final String MAP_HEIGHT_KEY     = "MAP_HEIGHT";

    private final Save ORIGINAL_SAVE;
    private int currentVersion = 0;

    public NoiseValueController(
            ValueController noiseReset,
            ValueController scale,
            ValueController lacunarity,
            ValueController octave,
            ValueController persistence,
            ValueController seed,
            ValueController mapWidth,
            ValueController mapHeight
    ) {
        this.NOISE_RESET    = noiseReset;
        this.SCALE          = scale;
        this.LACUNARITY     = lacunarity;
        this.OCTAVE         = octave;
        this.PERSISTENCE    = persistence;
        this.SEED           = seed;
        this.MAP_WIDTH      = mapWidth;
        this.MAP_HEIGHT     = mapHeight;

        this.lastNoiseResetState    = NOISE_RESET.getCurrentState();
        this.lastScaleState         = SCALE.getCurrentState();
        this.lastLacunarityState    = LACUNARITY.getCurrentState();
        this.lastOctaveState        = OCTAVE.getCurrentState();
        this.lastPersistenceState   = PERSISTENCE.getCurrentState();
        this.lastSeedState          = SEED.getCurrentState();
        this.lastMapWidthState      = MAP_WIDTH.getCurrentState();
        this.lastMapHeightState     = MAP_HEIGHT.getCurrentState();

        this.ORIGINAL_SAVE = save();
    }

    @Override
    public Save save() {
        Save save = new Save(currentVersion++);

        save.put(SCALE_KEY, SCALE.getState());
        save.put(LACUNARITY_KEY, LACUNARITY.getState());
        save.put(OCTAVES_KEY, OCTAVE.getState());
        save.put(PERSISTENCE_KEY, PERSISTENCE.getState());
        save.put(SEED_KEY, SEED.getState());
        save.put(MAP_WIDTH_KEY, MAP_WIDTH.getState());
        save.put(MAP_HEIGHT_KEY, MAP_HEIGHT.getState());

        return save;
    }

    @Override
    public boolean load(Save save) {
        boolean loadSuccessful;

        loadSuccessful = SCALE      .restoreToState(save.get(SCALE_KEY));
        loadSuccessful = LACUNARITY .restoreToState(save.get(LACUNARITY_KEY))   && loadSuccessful;
        loadSuccessful = OCTAVE     .restoreToState(save.get(OCTAVES_KEY))      && loadSuccessful;
        loadSuccessful = PERSISTENCE.restoreToState(save.get(PERSISTENCE_KEY))  && loadSuccessful;
        loadSuccessful = SEED       .restoreToState(save.get(SEED_KEY))         && loadSuccessful;
        loadSuccessful = MAP_WIDTH  .restoreToState(save.get(MAP_WIDTH_KEY))    && loadSuccessful;
        loadSuccessful = MAP_HEIGHT .restoreToState(save.get(MAP_HEIGHT_KEY))   && loadSuccessful;

        return loadSuccessful;
    }

    public record NoiseValues(
            int SCALE,
            double LACUNARITY,
            int OCTAVES,
            double PERSISTENCE,
            int SEED,
            int MAP_WIDTH,
            int MAP_HEIGHT
    ) { }

    @Override
    public Object getObjectValue() {
        return new NoiseValues(
                getScale(),
                getLacunarity(),
                getOctaves(),
                getPersistence(),
                getSeed(),
                getMapWidth(),
                getMapHeight()
        );
    }

    private boolean shouldReset() {
        return NOISE_RESET.getValue() >= 1;
    }

    private int getScale() {
        return (int) SCALE.getValue();
    }

    private double getLacunarity() {
        return LACUNARITY.getValue();
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

        if (hasUpdated(lastNoiseResetState, NOISE_RESET.getCurrentState())) {
            lastNoiseResetState = NOISE_RESET.getCurrentState();
            return true;
        } else if (hasUpdated(lastScaleState, SCALE.getCurrentState())) {
            lastScaleState = SCALE.getCurrentState();
            return true;
        } else if (hasUpdated(lastLacunarityState, LACUNARITY.getCurrentState())) {
            lastLacunarityState = LACUNARITY.getCurrentState();
            return true;
        } else if (hasUpdated(lastOctaveState, OCTAVE.getCurrentState())) {
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

        if (!changeOccurred()) return;

        newState();

        if (shouldReset()) {
            load(ORIGINAL_SAVE);
        }
    }
}
