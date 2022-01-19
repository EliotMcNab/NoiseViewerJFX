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
import app.noiseviewerjfx.utilities.processing.ImageProcessing;
import app.noiseviewerjfx.utilities.processing.Map;
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

        noiseField = new VectorField(1000, 1000);
        noiseField.setGenerationModel(VectorField.RANDOM_GENERATION);
        noiseField = noiseField.generate(lastNoiseValues.SEED());

        updateView();
    }

    @Override
    public void update() {
        if (changeOccurred()) updateView();
    }

    int i = 1;

    private void updateView() {

        Grid noise = generateNoise();
        NOISE_LAYER.setImage(noise.toGrayscaleImage());

        MASK_LAYER.setVisible(useMask() && maskIsVisible());
    }

    private Grid generateNoise() {

        PerlinNoiseTransformation perlinNoise = new PerlinNoiseTransformation(100, 100, 10);
        return noiseField.applyTransformation(new FractalNoiseTransformation(perlinNoise, 2, 0.5, 3));

        /*noiseField = noiseField.generate((long) (Math.random() * 10_000_000));

        int SCALE = 10;
        double STRENGTH = 1;
        final int WIDTH  = lastNoiseValues.MAP_WIDTH();
        final int HEIGHT = lastNoiseValues.MAP_HEIGHT();

        noiseField = noiseField.resize(
                WIDTH / SCALE,
                HEIGHT / SCALE);

        Grid noise = noiseField.applyTransformation(new PerlinNoiseTransformation(
                lastNoiseValues.MAP_WIDTH(),
                lastNoiseValues.MAP_HEIGHT(),
                SCALE
        ));

        SCALE /= 2;
        STRENGTH /= 2;

        noiseField = noiseField.resize(
                WIDTH / SCALE,
                HEIGHT / SCALE);

        Grid noise1 = noiseField.applyTransformation(new PerlinNoiseTransformation(
                lastNoiseValues.MAP_WIDTH(),
                lastNoiseValues.MAP_HEIGHT(),
                SCALE
        ));

        Grid noiseF = noise.add(noise1, STRENGTH);

        SCALE /= 2;
        STRENGTH /= 2;

        noiseField = noiseField.resize(
                WIDTH / SCALE,
                HEIGHT / SCALE);

        Grid noise2 = noiseField.applyTransformation(new PerlinNoiseTransformation(
                lastNoiseValues.MAP_WIDTH(),
                lastNoiseValues.MAP_HEIGHT(),
                SCALE
        ));

        noiseF = noiseF.add(noise2, STRENGTH);

        return noiseF.applyEffect(new Upscale(2));*/
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
}
