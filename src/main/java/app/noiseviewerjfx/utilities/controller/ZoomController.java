package app.noiseviewerjfx.utilities.controller;

import app.noiseviewerjfx.utilities.Vector2D;
import javafx.beans.value.ChangeListener;
import javafx.event.EventHandler;
import javafx.geometry.Bounds;
import javafx.scene.Node;
import javafx.scene.control.ScrollPane;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.transform.Translate;

public class ZoomController {

    private final ScrollPane SCROLL_PANE;
    private final AnchorPane PLANE;
    private final Node CONTENT;

    private Vector2D lastContentPostion = new Vector2D(0, 0);
    private Vector2D lastMousePosition = new Vector2D(0, 0);
    private Vector2D dragOffset = new Vector2D(0, 0);

    public ZoomController(
            ScrollPane scrollPane,
            AnchorPane plane,
            Node content
    ) {
        this.SCROLL_PANE    = scrollPane;
        this.PLANE          = plane;
        this.CONTENT        = content;

        addListeners();
    }

    private void addListeners() {
        PLANE.heightProperty().addListener(centerContent());
        PLANE.widthProperty().addListener(centerContent());
        PLANE.setOnMousePressed(getMousePosition());
        PLANE.setOnMouseDragged(testMouseDrag());
    }

    private ChangeListener<Number> centerContent() {
        return (observableValue, number, t1) -> {
            centerView();
        };
    }

    private EventHandler<MouseEvent> getMousePosition() {
        return mouseEvent -> {
            lastMousePosition = new Vector2D(mouseEvent.getX(), mouseEvent.getY());
        };
    }

    private EventHandler<MouseEvent> testMouseDrag() {
        return mouseEvent -> {
            Vector2D newMousePosition = new Vector2D(mouseEvent.getX(), mouseEvent.getY());
            Vector2D mouseTranslation = lastMousePosition.sub(newMousePosition);
            dragOffset = dragOffset.sub(mouseTranslation);
            lastMousePosition = newMousePosition;

            centerView();
        };
    }

    private void centerView() {
        Vector2D contentSize    = getContentSize();
        Vector2D availableSpace = getAvailableSpace();
        Vector2D centerPosition = availableSpace.sub(contentSize).div(2);
        Vector2D translation    = centerPosition.sub(lastContentPostion);

        Translate translateToCenter = new Translate();
        translateToCenter.setX(translation.getX() + dragOffset.getX());
        translateToCenter.setY(translation.getY() + dragOffset.getY());

        CONTENT.getTransforms().add(translateToCenter);

        updateContentPosition();
    }

    private void updateContentPosition() {
        lastContentPostion = new Vector2D(getContentPosition());
    }

    private Vector2D getContentSize() {
        Bounds contentBounds = CONTENT.getBoundsInParent();

        return new Vector2D(contentBounds.getWidth(), contentBounds.getHeight());
    }

    private Vector2D getContentPosition() {
        Bounds contentBounds = CONTENT.getBoundsInParent();

        return new Vector2D(contentBounds.getMinX(), contentBounds.getMinY());
    }

    private Vector2D getAvailableSpace() {
        return new Vector2D(PLANE.getWidth(), PLANE.getHeight());
    }

}
