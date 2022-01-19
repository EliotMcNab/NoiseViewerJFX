package app.noiseviewerjfx.utilities.controller.handlers;

import app.noiseviewerjfx.utilities.controller.valueControllers.settings.MaskValueController;
import app.noiseviewerjfx.utilities.controller.valueControllers.settings.MaskValueController.MaskValues;
import app.noiseviewerjfx.utilities.controller.valueControllers.settings.NoiseValueController;
import app.noiseviewerjfx.utilities.controller.valueControllers.settings.NoiseValueController.NoiseValues;
import app.noiseviewerjfx.utilities.generation.Grid;
import app.noiseviewerjfx.utilities.generation.VectorField;
import app.noiseviewerjfx.utilities.generation.effects.Upscale;
import app.noiseviewerjfx.utilities.generation.transformations.FractalNoiseTransformation;
import app.noiseviewerjfx.utilities.generation.transformations.PerlinNoiseTransformation;
import app.noiseviewerjfx.utilities.tasks.Persistent;
import javafx.scene.image.ImageView;

public class NoiseDisplayHandler implements Persistent {

    private final ImageView NOISE_LAYER;
    private final ImageView MASK_LAYER;
    private final ImageView TERRAIN_LAYER;

    private VectorField noiseField;

    private final NoiseValueController NOISE_PARAMETERS;
    private final MaskValueController MASK_PARAMETERS;

    private int lastNoiseState;
    private int lastMaskState;
    private long lastSeed;

    private NoiseValues lastNoiseValues;
    private MaskValues lastMaskValues;

    /**
     * Creates a new Value Controller responsible for drawing
     * the perlin noise according to the parameters specified by the user
     * @param noiseLayer (ImageView) : the ImageView used to display the noise
     */
    public NoiseDisplayHandler(
            ImageView noiseLayer,
            ImageView maskLayer,
            ImageView terrainLayer,
            NoiseValueController noiseValueController,
            MaskValueController maskValueController
    ) {

        this.NOISE_LAYER    = noiseLayer;
        this.MASK_LAYER     = maskLayer;
        this.TERRAIN_LAYER  = terrainLayer;

        this.NOISE_PARAMETERS   = noiseValueController;
        this.MASK_PARAMETERS    = maskValueController;

        lastNoiseState  = NOISE_PARAMETERS.getCurrentState();
        lastMaskState   = MASK_PARAMETERS.getCurrentState();

        lastNoiseValues = getNoiseValues();
        lastMaskValues  = getMaskValues();

        lastSeed = lastNoiseValues.SEED();

        noiseField = new VectorField(1000, 1000);
        noiseField.setGenerationModel(VectorField.RANDOM_GENERATION);
        noiseField = noiseField.generate(lastNoiseValues.SEED());

        updateView();
    }

    @Override
    public void update() {
        updateView();
    }

    int i = 1;

    private void updateView() {

        if (seedHasChanged()) generateNewSeed();

        if (noiseHasChanged()) {
            Grid noise = generateNoise();
            NOISE_LAYER.setImage(noise.toGrayscaleImage());
        }

        MASK_LAYER.setVisible(useMask() && maskIsVisible());
    }

    private Grid generateNoise() {
        PerlinNoiseTransformation perlinNoise = new PerlinNoiseTransformation(
                lastNoiseValues.MAP_WIDTH(),
                lastNoiseValues.MAP_HEIGHT(),
                lastNoiseValues.SCALE()
        );

        FractalNoiseTransformation fractalNoise = new FractalNoiseTransformation(
                perlinNoise,
                lastNoiseValues.LACUNARITY(),
                lastNoiseValues.PERSISTENCE(),
                lastNoiseValues.OCTAVES()
        );

        Grid fractalPerlinNoise = noiseField.applyTransformation(fractalNoise);

        return fractalPerlinNoise.applyEffect(new Upscale(2));
    }

    private boolean noiseHasChanged() {
        if (!hasUpdated(lastNoiseState, NOISE_PARAMETERS.getCurrentState())) return false;

        lastNoiseState = NOISE_PARAMETERS.getCurrentState();
        lastNoiseValues = getNoiseValues();
        return true;
    }

    private boolean changeOccurred() {
        if (hasUpdated(lastNoiseState, NOISE_PARAMETERS.getCurrentState())) {
            lastNoiseState = NOISE_PARAMETERS.getCurrentState();
            lastNoiseValues = getNoiseValues();
            return true;
        } else if (hasUpdated(lastMaskState, MASK_PARAMETERS.getCurrentState())) {
            lastMaskState = MASK_PARAMETERS.getCurrentState();
            lastMaskValues = getMaskValues();
            return true;
        }

        return false;
    }

    private NoiseValues getNoiseValues() {
        return (NoiseValues) NOISE_PARAMETERS.getObjectValue();
    }

    private MaskValues getMaskValues() {
        return (MaskValues) MASK_PARAMETERS.getObjectValue();
    }

    private boolean useMask() {
        return lastMaskValues.IS_MASK_ACTIVE();
    }

    private boolean maskIsVisible() {
        return lastMaskValues.IS_MASK_VISIBLE();
    }

    private boolean seedHasChanged() {
        if (lastSeed == lastNoiseValues.SEED()) return false;

        lastSeed = lastNoiseValues.SEED();
        return true;
    }

    private void generateNewSeed() {
        noiseField = noiseField.generate(lastSeed);
    }
}
