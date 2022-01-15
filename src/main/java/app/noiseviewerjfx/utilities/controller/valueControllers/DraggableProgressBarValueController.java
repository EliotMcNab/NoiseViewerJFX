package app.noiseviewerjfx.utilities.controller.valueControllers;

import app.noiseviewerjfx.utilities.ComplementaryMath;
import app.noiseviewerjfx.utilities.controller.NodeController;
import app.noiseviewerjfx.utilities.controller.valueControllers.associative.Associable;
import javafx.event.EventHandler;
import javafx.scene.Cursor;
import javafx.scene.control.ProgressBar;
import javafx.scene.input.MouseEvent;

public class DraggableProgressBarValueController extends ProgressBarValueController implements Associable {

    private boolean isOnRightEdge = false;
    private final int TOLERANCE = 10;

    public DraggableProgressBarValueController(ProgressBar linkedProgressBar) {
        super(linkedProgressBar);

        addListeners();
    }

    private void addListeners() {
        PROGRESS_BAR.setOnMouseMoved(checkMouseIsOnRightEdge());
        PROGRESS_BAR.setOnMouseDragged(resizeOnDrag());
        PROGRESS_BAR.setOnMouseClicked(setValueOnClick());
    }

    private EventHandler<MouseEvent> checkMouseIsOnRightEdge() {
        return mouseEvent -> {

            if (isMouseOnRightEdge(mouseEvent)) {
                PROGRESS_BAR.setCursor(Cursor.H_RESIZE);
                isOnRightEdge = true;
            } else {
                PROGRESS_BAR.setCursor(Cursor.DEFAULT);
                isOnRightEdge = false;
            }
        };
    }

    private EventHandler<MouseEvent> resizeOnDrag() {
        return mouseEvent -> {
            if (!isOnRightEdge) return;
            double currentMousePosition = mouseEvent.getX();
            double progress = (currentMousePosition / getAvailableSpace());
            PROGRESS_BAR.setProgress(ComplementaryMath.clamp(progress, 0, 1));
            newState();
        };
    }

    private EventHandler<MouseEvent> setValueOnClick() {
        return mouseEvent -> {
            double currentMousePosition = mouseEvent.getX();
            double progress = currentMousePosition / getAvailableSpace();
            PROGRESS_BAR.setProgress(ComplementaryMath.clamp(progress, 0, 1));
            newState();
        };
    }

    private boolean isMouseOnRightEdge(MouseEvent mouseEvent) {
        double progressbarSize = getProgressBarSize();
        return (mouseEvent.getX() >= progressbarSize - TOLERANCE) && (mouseEvent.getX() <= progressbarSize + TOLERANCE);
    }

    private double getProgressBarSize() {
        return getAvailableSpace() * PROGRESS_BAR.getProgress();
    }

    private double getAvailableSpace() {
        return NodeController.getParentContentSize(PROGRESS_BAR).getX();
    }
}
