package app.noiseviewerjfx.utilities.controller.handlers;

import app.noiseviewerjfx.utilities.controller.valueControllers.Updatable;
import app.noiseviewerjfx.utilities.controller.valueControllers.settings.MaskValueController;
import app.noiseviewerjfx.utilities.controller.valueControllers.settings.MaskValueController.MaskValues;
import app.noiseviewerjfx.utilities.controller.valueControllers.settings.NoiseValueController;
import app.noiseviewerjfx.utilities.controller.valueControllers.settings.NoiseValueController.NoiseValues;
import app.noiseviewerjfx.utilities.processing.ImageProcessing;
import app.noiseviewerjfx.utilities.processing.Map;
import app.noiseviewerjfx.utilities.tasks.Persistent;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class NoiseDisplayHandler implements Persistent {

    private final ImageView NOISE_LAYER;
    private final ImageView MASK_LAYER;
    private final ImageView TERRAIN_LAYER;

    private int[][] noise   = new int[1][1];
    private int[][] mask    = new int[1][1];

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

        updateView();
    }

    @Override
    public void update() {
        if (changeOccurred()) updateView();
    }

    private void updateView() {

        noise = generateNoise();
        mask = generateMask();

        NOISE_LAYER.setImage(generateNoiseLayer());
        MASK_LAYER.setImage(generateMaskLayer());
        // TERRAIN_LAYER.setImage(generateTerrainLayer());

        MASK_LAYER.setVisible(useMask() && maskIsVisible());
    }

    private int[][] generateNoise() {
        return Map.PerlinNoise.generatePerlinNoise(
                lastNoiseValues.MAP_WIDTH(),
                lastNoiseValues.MAP_HEIGHT(),
                lastNoiseValues.OCTAVES(),
                lastNoiseValues.PERSISTENCE(),
                lastNoiseValues.SEED()
        );
    }

    private int[][] generateMask() {
        if (lastMaskValues.IS_RECTANGLE_MASK()) {
            return Map.Mask.generateRoundedSquareMask(
                    lastNoiseValues.MAP_WIDTH(),
                    lastNoiseValues.MAP_HEIGHT(),
                    (float) lastMaskValues.MASK_WIDTH(),
                    (float) lastMaskValues.MASK_HEIGHT(),
                    lastMaskValues.MASK_STRENGTH()
            );
        } else {
            return Map.Mask.generateCircularMask(
                    lastNoiseValues.MAP_WIDTH(),
                    lastNoiseValues.MAP_HEIGHT(),
                    (float) lastMaskValues.MASK_WIDTH(),
                    lastMaskValues.MASK_STRENGTH()
            );
        }
    }

    private Image generateNoiseLayer() {
        Image noiseImage = Map.toGrayScale(noise);

        return ImageProcessing.upScale(noiseImage, 2);
    }

    private Image generateMaskLayer() {
        return Map.toMaskImage(mask, lastMaskValues.MASK_OPACITY() / 100f);
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
