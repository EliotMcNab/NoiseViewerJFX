package app.noiseviewerjfx.utilities.controller;

import app.noiseviewerjfx.utilities.controller.valueControllers.Updatable;
import app.noiseviewerjfx.utilities.controller.valueControllers.ValueController;
import javafx.scene.image.ImageView;

import java.util.HashMap;

public class NoiseImageViewController implements Updatable {

    private final ImageView DISPLAY;
    private final HashMap<Components, ValueController> COMPONENTS;

    public NoiseImageViewController(ImageView mainDisplay, HashMap<Components, ValueController> components) {
        this.DISPLAY = mainDisplay;
        this.COMPONENTS = new HashMap<>(components);
    }

    @Override
    public void update() {

    }

    public enum Components {
        OCTAVES,
        PERSISTENCE,
        SEED,
        RANDOM_OCTAVE,
        RANDOM_PERSISTENCE,
        RANDOM_SEED,
        MAP_WIDTH,
        MAP_HEIGHT,
        MASK_WIDTH,
        MASK_HEIGHT,
        MASK_STRENGTH;
    }

}
