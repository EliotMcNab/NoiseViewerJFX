package app.noiseviewerjfx.utilities.controller.valueControllers.settings;

import app.noiseviewerjfx.utilities.controller.valueControllers.Updatable;
import app.noiseviewerjfx.utilities.controller.valueControllers.ValueController;
import app.noiseviewerjfx.utilities.controller.valueControllers.associative.AssociativeSlider;
import app.noiseviewerjfx.utilities.io.serialization.Loadable;
import app.noiseviewerjfx.utilities.io.serialization.Save;

public class MaskValueController extends ValueController implements Updatable, Loadable {

    private final ValueController MASK_RESET;
    private final ValueController IS_MASK_VISIBLE;
    private final ValueController IS_MASK_ACTIVE;
    private final ValueController MASK_STRENGTH;
    private final ValueController IS_CIRCLE_MASK;
    private final ValueController IS_RECTANGLE_MASK;
    private final ValueController MASK_OPACITY;
    private final AssociativeSlider MASK_WIDTH;
    private final AssociativeSlider MASK_HEIGHT;

    private int lastMaskResetState;
    private int lastMaskVisibleState;
    private int lastMaskActiveState;
    private int lastMaskWidthState;
    private int lastMaskHeightState;
    private int lastMaskStrengthState;
    private int lastCircleMaskState;
    private int lastRectangleMaskState;
    private int lastMaskOpacityState;

    private final String MASK_VISIBILITY_KEY    = "IS_MASK_VISIBLE";
    private final String MASK_ACTIVE_KEY        = "IS_MASK_ACTIVE";
    private final String MASK_WIDTH_KEY         = "MASK_WIDTH";
    private final String MASK_HEIGHT_KEY        = "MASK_HEIGHT";
    private final String MASK_STRENGTH_KEY      = "MASK_STRENGTH";
    private final String CIRCLE_MASK_KEY        = "IS_CIRCLE_MASK";
    private final String RECTANGLE_MASK_KEY     = "IS_RECTANGLE_MASK";
    private final String MASK_OPACITY_KEY       = "MASK_OPACITY";

    private int currentVersion = 0;
    private final Save originalSave;

    private boolean wasCircleMask = false;

    public MaskValueController(
            ValueController maskReset,
            ValueController isMaskVisible,
            ValueController isMaskActive,
            ValueController maskStrength,
            ValueController isCircleMask,
            ValueController isRectangleMask,
            ValueController opacity,
            AssociativeSlider maskWidth,
            AssociativeSlider maskHeight
    ) {
        this.MASK_RESET         = maskReset;
        this.IS_MASK_VISIBLE    = isMaskVisible;
        this.IS_MASK_ACTIVE     = isMaskActive;
        this.MASK_WIDTH         = maskWidth;
        this.MASK_HEIGHT        = maskHeight;
        this.MASK_STRENGTH      = maskStrength;
        this.IS_CIRCLE_MASK     = isCircleMask;
        this.IS_RECTANGLE_MASK  = isRectangleMask;
        this.MASK_OPACITY       = opacity;

        this.lastMaskResetState     = MASK_RESET.getCurrentState();
        this.lastMaskVisibleState   = IS_MASK_VISIBLE.getCurrentState();
        this.lastMaskActiveState    = IS_MASK_ACTIVE.getCurrentState();
        this.lastMaskWidthState     = MASK_WIDTH.getCurrentState();
        this.lastMaskHeightState    = MASK_HEIGHT.getCurrentState();
        this.lastMaskStrengthState  = MASK_STRENGTH.getCurrentState();
        this.lastCircleMaskState    = IS_CIRCLE_MASK.getCurrentState();
        this.lastRectangleMaskState = IS_RECTANGLE_MASK.getCurrentState();
        this.lastMaskOpacityState   = MASK_OPACITY.getCurrentState();

        originalSave = save();
    }

    @Override
    public Save save() {
        Save currentState = new Save(currentVersion++);

        currentState.put(MASK_VISIBILITY_KEY, IS_MASK_VISIBLE.getState());
        currentState.put(MASK_ACTIVE_KEY, IS_MASK_ACTIVE.getState());
        currentState.put(MASK_WIDTH_KEY, MASK_WIDTH.getState());
        currentState.put(MASK_HEIGHT_KEY, MASK_HEIGHT.getState());
        currentState.put(MASK_STRENGTH_KEY, MASK_STRENGTH.getState());
        currentState.put(CIRCLE_MASK_KEY, IS_CIRCLE_MASK.getState());
        currentState.put(RECTANGLE_MASK_KEY, IS_RECTANGLE_MASK.getState());
        currentState.put(MASK_OPACITY_KEY, MASK_OPACITY.getState());

        return currentState;
    }

    @Override
    public boolean load(Save save) {
        boolean loadSuccessful = true;

        loadSuccessful = IS_MASK_VISIBLE    .restoreToState(save.get(MASK_VISIBILITY_KEY))  && loadSuccessful;
        loadSuccessful = MASK_WIDTH         .restoreToState(save.get(MASK_WIDTH_KEY))       && loadSuccessful;
        loadSuccessful = MASK_HEIGHT        .restoreToState(save.get(MASK_HEIGHT_KEY))      && loadSuccessful;
        loadSuccessful = MASK_STRENGTH      .restoreToState(save.get(MASK_STRENGTH_KEY))    && loadSuccessful;
        loadSuccessful = IS_CIRCLE_MASK     .restoreToState(save.get(CIRCLE_MASK_KEY))      && loadSuccessful;
        loadSuccessful = IS_RECTANGLE_MASK  .restoreToState(save.get(RECTANGLE_MASK_KEY))   && loadSuccessful;
        loadSuccessful = MASK_OPACITY       .restoreToState(save.get(MASK_OPACITY_KEY))     && loadSuccessful;

        return loadSuccessful;
    }

    public record MaskValues (
            boolean IS_MASK_VISIBLE,
            boolean IS_MASK_ACTIVE,
            double MASK_WIDTH,
            double MASK_HEIGHT,
            int MASK_STRENGTH,
            boolean IS_CIRCLE_MASK,
            boolean IS_RECTANGLE_MASK,
            int MASK_OPACITY
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
                isRectangleMask(),
                getMaskOpacity()
        );
    }

    protected boolean shouldResetMask() {
        return MASK_RESET.getValue() >= 1;
    }

    private boolean isMaskVisible() {
        return IS_MASK_VISIBLE.getValue() < 1;
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

    private int getMaskOpacity() {
        return (int) MASK_OPACITY.getValue();
    }

    private boolean changeOccurred() {
        if (hasUpdated(lastMaskResetState, MASK_RESET.getCurrentState())) {
            lastMaskResetState = MASK_RESET.getCurrentState();
            return true;
        } else if (hasUpdated(lastMaskVisibleState, IS_MASK_VISIBLE.getCurrentState())) {
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
        } else if (hasUpdated(lastMaskOpacityState, MASK_OPACITY.getCurrentState())) {
            lastMaskOpacityState = MASK_OPACITY.getCurrentState();
            return true;
        }
        return false;
    }

    @Override
    public void update() {

        if (!changeOccurred()) return;

        newState();

        if (shouldResetMask()) {
            load(originalSave);
        }

        if (isCircleMask() && !wasCircleMask) {
            MASK_WIDTH.addAssociatedNode(MASK_HEIGHT);
            MASK_HEIGHT.addAssociatedNode(MASK_WIDTH);
            wasCircleMask = true;
        } else if (isRectangleMask() && wasCircleMask) {
            MASK_WIDTH.removeAssociatedNode(MASK_HEIGHT);
            MASK_HEIGHT.removeAssociatedNode(MASK_WIDTH);
            wasCircleMask = false;
        }
    }
}
