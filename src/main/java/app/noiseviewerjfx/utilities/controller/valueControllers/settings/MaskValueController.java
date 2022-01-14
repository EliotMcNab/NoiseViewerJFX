package app.noiseviewerjfx.utilities.controller.valueControllers.settings;

import app.noiseviewerjfx.utilities.controller.valueControllers.Updatable;
import app.noiseviewerjfx.utilities.controller.valueControllers.ValueController;

public class MaskValueController extends ValueController implements Updatable {

    private final ValueController MASK_WIDTH;
    private final ValueController MASK_HEIGHT;
    private final ValueController MASK_STRENGTH;
    private final ValueController IS_CIRCLE_MASK;
    private final ValueController IS_RECTANGLE_MASK;

    private int lastMaskWidthState;
    private int lastMaskHeightState;
    private int lastMaskStrengthState;
    private int lastCircleMaskState;
    private int lastRectangleMaskState;

    public MaskValueController(
            ValueController maskWidth,
            ValueController maskHeight,
            ValueController maskStrength,
            ValueController isCircleMask,
            ValueController isRectangleMask
    ) {
        this.MASK_WIDTH         = maskWidth;
        this.MASK_HEIGHT        = maskHeight;
        this.MASK_STRENGTH      = maskStrength;
        this.IS_CIRCLE_MASK     = isCircleMask;
        this.IS_RECTANGLE_MASK  = isRectangleMask;

        this.lastMaskWidthState     = MASK_WIDTH.getCurrentState();
        this.lastMaskHeightState    = MASK_HEIGHT.getCurrentState();
        this.lastMaskStrengthState  = MASK_STRENGTH.getCurrentState();
        this.lastCircleMaskState    = IS_CIRCLE_MASK.getCurrentState();
        this.lastRectangleMaskState = IS_RECTANGLE_MASK.getCurrentState();
    }

    public record MaskValues (
             double MASK_WIDTH,
             double MASK_HEIGHT,
             int MASK_STRENGTH,
             boolean IS_CIRCLE_MASK,
             boolean IS_RECTANGLE_MASK
    ) { }

    @Override
    public Object getObjectValue() {
        return new MaskValues(
                getMaskWidth(),
                getMaskHeight(),
                getMaskStrength(),
                isCircleMask(),
                isRectangleMask()
        );
    }

    private double getMaskWidth() {
        return MASK_WIDTH.getValue();
    }

    private double getMaskHeight() {
        return MASK_HEIGHT.getValue();
    }

    private int getMaskStrength() {
        return (int) MASK_STRENGTH.getValue();
    }

    private boolean isCircleMask() {
        return IS_CIRCLE_MASK.getValue() >= 1;
    }

    private boolean isRectangleMask() {
        return IS_RECTANGLE_MASK.getValue() >= 1;
    }

    private boolean changeOccurred() {
        if (hasUpdated(lastMaskWidthState, MASK_WIDTH.getCurrentState())) {
            lastMaskWidthState = MASK_WIDTH.getCurrentState();
            return true;
        } else if (hasUpdated(lastMaskHeightState, MASK_HEIGHT.getCurrentState())) {
            lastMaskHeightState = MASK_HEIGHT.getCurrentState();
            return true;
        } else if (hasUpdated(lastMaskStrengthState, MASK_STRENGTH.getCurrentState())) {
            lastMaskStrengthState = MASK_STRENGTH.getCurrentState();
            return true;
        } else if (hasUpdated(lastCircleMaskState, IS_CIRCLE_MASK.getCurrentState())) {
            lastCircleMaskState = IS_CIRCLE_MASK.getCurrentState();
            return true;
        } else if (hasUpdated(lastRectangleMaskState, IS_RECTANGLE_MASK.getCurrentState())) {
            lastRectangleMaskState = IS_RECTANGLE_MASK.getCurrentState();
            return true;
        }

        return false;
    }

    @Override
    public void update() {
        if (changeOccurred()) newState();
    }
}
