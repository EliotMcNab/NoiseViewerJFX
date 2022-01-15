package app.noiseviewerjfx.utilities.controller;

import app.noiseviewerjfx.utilities.controller.valueControllers.Updatable;
import app.noiseviewerjfx.utilities.controller.valueControllers.settings.MaskValueController;
import app.noiseviewerjfx.utilities.controller.valueControllers.settings.MaskValueController.MaskValues;
import app.noiseviewerjfx.utilities.controller.valueControllers.settings.NoiseValueController;
import app.noiseviewerjfx.utilities.controller.valueControllers.settings.NoiseValueController.NoiseValues;
import app.noiseviewerjfx.utilities.processing.ImageProcessing;
import app.noiseviewerjfx.utilities.processing.NoiseProcessing;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class NoiseDisplayHandler implements Updatable {

    private final ImageView DISPLAY;

    private final NoiseValueController NOISE;
    private final MaskValueController MASK;

    private int lastNoiseState;
    private int lastMaskState;

    private NoiseValues lastNoiseValues;
    private MaskValues lastMaskValues;

    /**
     * Creates a new Value Controller responsible for drawing
     * the perlin noise according to the parameters specified by the user
     * @param mainDisplay (ImageView) : the ImageView used to display the noise
     */
    public NoiseDisplayHandler(
            ImageView mainDisplay,
            NoiseValueController noiseValueController,
            MaskValueController maskValueController
    ) {

        this.DISPLAY    = mainDisplay;
        this.NOISE      = noiseValueController;
        this.MASK       = maskValueController;

        lastNoiseState  = NOISE.getCurrentState();
        lastMaskState   = MASK.getCurrentState();

        lastNoiseValues = getNoiseValues();
        lastMaskValues  = getMaskValues();

        updateView(generateView());
    }

    @Override
    public void update() {
        if (changeOccurred()) updateView(generateView());
    }

    private void updateView(Image newView) {
        DISPLAY.setImage(newView);
    }

    private Image generateView() {

        int[][] noise = NoiseProcessing.PerlinNoise.generatePerlinNoise(
            lastNoiseValues.MAP_WIDTH(),
            lastNoiseValues.MAP_HEIGHT(),
            lastNoiseValues.OCTAVES(),
            lastNoiseValues.PERSISTENCE(),
            lastNoiseValues.SEED()
        );

        if (useMask() && !maskIsVisible()) {

            int[][] mask;

            if (lastMaskValues.IS_CIRCLE_MASK()) {
                mask = NoiseProcessing.Mask.generateCircularMask(
                        lastNoiseValues.MAP_WIDTH(),
                        lastNoiseValues.MAP_HEIGHT(),
                        (float) lastMaskValues.MASK_WIDTH()
                );
                noise = NoiseProcessing.subtract(noise, mask, lastMaskValues.MASK_STRENGTH());
            } else if (lastMaskValues.IS_RECTANGLE_MASK()) {
                mask = NoiseProcessing.Mask.generateRoundedSquareMask(
                        lastNoiseValues.MAP_WIDTH(),
                        lastNoiseValues.MAP_HEIGHT(),
                        (float) lastMaskValues.MASK_WIDTH(),
                        (float) lastMaskValues.MASK_HEIGHT()
                );
                noise = NoiseProcessing.subtract(noise, mask, lastMaskValues.MASK_STRENGTH());
            }
        }

        Image grayScaleImage = ImageProcessing.toGrayScale(noise);

        return ImageProcessing.upScale(grayScaleImage, 2);
    }

    private boolean changeOccurred() {
        if (hasUpdated(lastNoiseState, NOISE.getCurrentState())) {
            lastNoiseState = NOISE.getCurrentState();
            lastNoiseValues = getNoiseValues();
            return true;
        } else if (hasUpdated(lastMaskState, MASK.getCurrentState())) {
            lastMaskState = MASK.getCurrentState();
            lastMaskValues = getMaskValues();
            return true;
        }

        return false;
    }

    private NoiseValues getNoiseValues() {
        return (NoiseValues) NOISE.getObjectValue();
    }

    private MaskValues getMaskValues() {
        return (MaskValues) MASK.getObjectValue();
    }

    private boolean useMask() {
        return lastMaskValues.IS_MASK_ACTIVE();
    }

    private boolean maskIsVisible() {
        return lastMaskValues.IS_MASK_VISIBLE();
    }
}
