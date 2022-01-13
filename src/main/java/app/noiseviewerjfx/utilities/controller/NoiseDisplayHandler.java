package app.noiseviewerjfx.utilities.controller;

import app.noiseviewerjfx.utilities.controller.valueControllers.Updatable;
import app.noiseviewerjfx.utilities.controller.valueControllers.ValueController;
import app.noiseviewerjfx.utilities.processing.ImageProcessing;
import app.noiseviewerjfx.utilities.processing.NoiseProcessing;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class NoiseDisplayHandler implements Updatable {

    private final ImageView DISPLAY;

    private final ValueController OCTAVE;
    private final ValueController PERSISTENCE;
    private final ValueController SEED;
    private final ValueController MAP_WIDTH;
    private final ValueController MAP_HEIGHT;
    private final ValueController MASK_WIDTH;
    private final ValueController MASK_HEIGHT;
    private final ValueController MASK_STRENGTH;

    private int lastOctaveState;
    private int lastPersistenceState;
    private int lastSeedState;
    private int lastMapWidthState;
    private int lastMapHeightState;
    private int lastMaskWithState;
    private int lastMaskHeightState;
    private int lastMaskStrengthState;

    /*private float zoomAmount = 1;
    private final float ZOOM_STRENGTH = 0.5f;
    private Point2D mousePositionRatio = new Point2D(0.5, 0.5);*/

    /**
     * Creates a new Value Controller responsible for drawing
     * the perlin noise according to the parameters specified by the user
     * @param listenerNode  (Node) : the node used to register event listeners
     * @param displayScrollPane (ScrollPane): the scroll pane in which we display the noise
     * @param mainDisplay (ImageView) : the ImageView used to display the noise
     * @param octave (ValueController) : Octave spinner
     * @param persistence (ValueController) : Persistence spinner
     * @param seed (ValueController) : Seed text field
     * @param mapWidth (ValueController) : Map width spinner
     * @param mapHeight (ValueController) : Map height spinner
     * @param maskWidth (ValueController) : Mask width spinner
     * @param maskHeight (ValueController) : Mask height spinner
     * @param maskStrength (ValueController) : Mask strength spinner
     */
    public NoiseDisplayHandler(
            ImageView mainDisplay,
            ValueController octave,
            ValueController persistence,
            ValueController seed,
            ValueController mapWidth,
            ValueController mapHeight,
            ValueController maskWidth,
            ValueController maskHeight,
            ValueController maskStrength) {

        this.DISPLAY        = mainDisplay;

        this.OCTAVE         = octave;
        this.PERSISTENCE    = persistence;
        this.SEED           = seed;
        this.MAP_WIDTH      = mapWidth;
        this.MAP_HEIGHT     = mapHeight;
        this.MASK_WIDTH     = maskWidth;
        this.MASK_HEIGHT    = maskHeight;
        this.MASK_STRENGTH  = maskStrength;

        lastOctaveState         = OCTAVE.getCurrentState();
        lastPersistenceState    = PERSISTENCE.getCurrentState();
        lastSeedState           = SEED.getCurrentState();
        lastMapWidthState       = MAP_WIDTH.getCurrentState();
        lastMapHeightState      = MAP_HEIGHT.getCurrentState();
        lastMaskWithState       = MAP_WIDTH.getCurrentState();
        lastMaskHeightState     = MAP_HEIGHT.getCurrentState();
        lastMaskStrengthState   = MASK_STRENGTH.getCurrentState();

        addListeners();
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
                getMapWidth(),
                getMapHeight(),
                getOctaveCount(),
                getPersistence(),
                getSeed()
        );

        Image grayScaleImage = ImageProcessing.toGrayScale(noise);

        return ImageProcessing.upScale(grayScaleImage, 2);
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
        } else if (hasUpdated(lastMaskWithState, MASK_WIDTH.getCurrentState())) {
            lastMaskWithState = MASK_WIDTH.getCurrentState();
            return true;
        } else if (hasUpdated(lastMaskHeightState, MASK_HEIGHT.getCurrentState())) {
            lastMaskHeightState = MASK_HEIGHT.getCurrentState();
            return true;
        } else if (hasUpdated(lastMaskStrengthState, MASK_STRENGTH.getCurrentState())) {
            lastMaskStrengthState = MASK_STRENGTH.getCurrentState();
            return true;
        }

        return false;
    }

    private int getOctaveCount() {
        return (int) OCTAVE.getValue();
    }

    private double getPersistence() {
        return PERSISTENCE.getValue();
    }

    private int getSeed() {
        return (int) SEED.getValue();
    }

    private int getMapHeight() {
        return (int) MAP_HEIGHT.getValue();
    }

    private int getMapWidth() {
        return (int) MAP_WIDTH.getValue();
    }

    private int getMaskWidth() {
        return (int) MASK_WIDTH.getValue();
    }

    private int getMaskHeight() {
        return (int) MASK_HEIGHT.getValue();
    }

    private int getMaskStrength() {
        return (int) MASK_STRENGTH.getValue();
    }

    private void addListeners() {

    }
}
