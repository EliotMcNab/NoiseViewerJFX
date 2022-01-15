package app.noiseviewerjfx.utilities.controller.valueControllers.settings;

import app.noiseviewerjfx.utilities.controller.valueControllers.SliderValueController;
import app.noiseviewerjfx.utilities.controller.valueControllers.Updatable;
import app.noiseviewerjfx.utilities.controller.valueControllers.ValueController;
import app.noiseviewerjfx.utilities.controller.valueControllers.associative.AssociativeSlider;

public class MaskValueController extends ValueController implements Updatable {

    private final ValueController IS_MASK_VISIBLE;
    private final ValueController IS_MASK_ACTIVE;
    private final ValueController MASK_WIDTH;
    private final ValueController MASK_HEIGHT;
    private final ValueController MASK_STRENGTH;
    private final ValueController IS_CIRCLE_MASK;
    private final ValueController IS_RECTANGLE_MASK;
    private final AssociativeSlider MASK_WIDTH_SLIDER;
    private final AssociativeSlider MASK_HEIGHT_SLIDER;

    private int lastMaskVisibleState;
    private int lastMaskActiveState;
    private int lastMaskWidthState;
    private int lastMaskHeightState;
    private int lastMaskStrengthState;
    private int lastCircleMaskState;
    private int lastRectangleMaskState;

    public MaskValueController(
            ValueController isMaskVisible,
            ValueController isMaskActive,
            ValueController maskWidth,
            ValueController maskHeight,
            ValueController maskStrength,
            ValueController isCircleMask,
            ValueController isRectangleMask,
            AssociativeSlider maskWidthSlider,
            AssociativeSlider maskHeightSlider
    ) {
        this.IS_MASK_VISIBLE    = isMaskVisible;
        this.IS_MASK_ACTIVE     = isMaskActive;
        this.MASK_WIDTH         = maskWidth;
        this.MASK_HEIGHT        = maskHeight;
        this.MASK_STRENGTH      = maskStrength;
        this.IS_CIRCLE_MASK     = isCircleMask;
        this.IS_RECTANGLE_MASK  = isRectangleMask;
        this.MASK_WIDTH_SLIDER  = maskWidthSlider;
        this.MASK_HEIGHT_SLIDER = maskHeightSlider;

        this.lastMaskVisibleState   = IS_MASK_VISIBLE.getCurrentState();
        this.lastMaskActiveState    = IS_MASK_ACTIVE.getCurrentState();
        this.lastMaskWidthState     = MASK_WIDTH.getCurrentState();
        this.lastMaskHeightState    = MASK_HEIGHT.getCurrentState();
        this.lastMaskStrengthState  = MASK_STRENGTH.getCurrentState();
        this.lastCircleMaskState    = IS_CIRCLE_MASK.getCurrentState();
        this.lastRectangleMaskState = IS_RECTANGLE_MASK.getCurrentState();
    }

    public record MaskValues (
            boolean IS_MASK_VISIBLE,
            boolean IS_MASK_ACTIVE,
            double MASK_WIDTH,
            double MASK_HEIGHT,
            int MASK_STRENGTH,
            boolean IS_CIRCLE_MASK,
            boolean IS_RECTANGLE_MASK
    ) { }

    @Override
    public Object getObjectValue() {
        return new MaskValues(
                isMaskVisible(),
                isMaskActive(),
                getMaskWidth(),
                getMaskHeight(),
                getMaskStrength(),
                isCircleMask(),
                isRectangleMask()
        );
    }

    private boolean isMaskVisible() {
        return IS_MASK_VISIBLE.getValue() >= 1;
    }

    private boolean isMaskActive() { return IS_MASK_ACTIVE.getValue() >= 1;}

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
        if (hasUpdated(lastMaskVisibleState, IS_MASK_VISIBLE.getCurrentState())) {
            lastMaskVisibleState = IS_MASK_VISIBLE.getCurrentState();
            return true;
        } else if (hasUpdated(lastMaskActiveState, IS_MASK_ACTIVE.getCurrentState())) {
            lastMaskActiveState = IS_MASK_ACTIVE.getCurrentState();
            return true;
        } else if (hasUpdated(lastMaskWidthState, MASK_WIDTH.getCurrentState())) {
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

        if (isCircleMask()) {
            MASK_WIDTH_SLIDER.addAssociatedNode(MASK_HEIGHT_SLIDER);
            MASK_HEIGHT_SLIDER.addAssociatedNode(MASK_WIDTH_SLIDER);
        } else if (isRectangleMask()) {
            MASK_WIDTH_SLIDER.removeAssociatedNode(MASK_HEIGHT_SLIDER);
            MASK_HEIGHT_SLIDER.removeAssociatedNode(MASK_WIDTH_SLIDER);
        }
    }
}
