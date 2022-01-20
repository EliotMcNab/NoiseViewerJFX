package app.noiseviewerjfx.utilities.controller.handlers;

import app.noiseviewerjfx.utilities.controller.valueControllers.settings.MaskValueController;
import app.noiseviewerjfx.utilities.controller.valueControllers.settings.MaskValueController.MaskValues;
import app.noiseviewerjfx.utilities.controller.valueControllers.settings.NoiseValueController;
import app.noiseviewerjfx.utilities.controller.valueControllers.settings.NoiseValueController.NoiseValues;
import app.noiseviewerjfx.utilities.generation.Grid;
import app.noiseviewerjfx.utilities.generation.VectorField;
import app.noiseviewerjfx.utilities.generation.effects.Upscale;
import app.noiseviewerjfx.utilities.generation.generationmodel.GradientCircleGenerationModel;
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
    private int lastMapWidth;
    private int lastMapHeight;

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

        lastSeed        = lastNoiseValues.SEED();
        lastMapWidth    = lastNoiseValues.MAP_WIDTH();
        lastMapHeight   = lastNoiseValues.MAP_HEIGHT();

        noiseField = new VectorField(1000, 1000);
        noiseField = noiseField.setGenerationModel(VectorField.RANDOM_GENERATION);
        noiseField = noiseField.generate(lastNoiseValues.SEED());

        updateView();
    }

    @Override
    public void update() {
        updateView();
    }

    private void updateView() {

        if (noiseHasChanged()) {
            Grid noise = generateNoise();
            NOISE_LAYER.setImage(noise.toGrayscaleImage());
        }

        if (maskHasChanged() && useMask() && maskIsVisible() || mapResized()) {
            Grid mask = generateMask();
            MASK_LAYER.setImage(mask.toGrayscaleImage());
        }

        MASK_LAYER.setVisible(useMask() && maskIsVisible());

    }

    private Grid generateNoise() {

        if (seedHasChanged()) {
            noiseField = noiseField.generate(lastNoiseValues.SEED());
        }

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

    private Grid generateMask() {

        Grid mask = new Grid(
                lastNoiseValues.MAP_WIDTH(),
                lastNoiseValues.MAP_HEIGHT()
        );

        final int size = (int) (lastNoiseValues.MAP_WIDTH() * lastMaskValues.MASK_WIDTH() / 100);

        mask = mask.setGenerationModel(new GradientCircleGenerationModel(size / 2));
        mask = mask.setImageModel(Grid.OPACITY);
        mask = mask.generate(0);
        mask = mask.applyEffect(new Upscale(2));

        return mask;
    }

    private boolean noiseHasChanged() {
        if (!hasUpdated(lastNoiseState, NOISE_PARAMETERS.getCurrentState())) return false;

        lastNoiseState = NOISE_PARAMETERS.getCurrentState();
        lastNoiseValues = getNoiseValues();
        return true;
    }

    private boolean maskHasChanged() {
        if (!hasUpdated(lastMaskState, MASK_PARAMETERS.getCurrentState())) return false;

        lastMaskState = MASK_PARAMETERS.getCurrentState();
        lastMaskValues = getMaskValues();
        return true;
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

    private boolean mapResized() {
        if (lastNoiseValues.MAP_HEIGHT() == lastMapHeight && lastNoiseValues.MAP_WIDTH() == lastMapWidth) return false;

        lastMapHeight   = lastNoiseValues.MAP_HEIGHT();
        lastMapWidth    = lastNoiseValues.MAP_WIDTH();

        return true;
    }
}
