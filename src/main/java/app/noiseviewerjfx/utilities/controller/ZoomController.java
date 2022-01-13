package app.noiseviewerjfx.utilities.controller;

import app.noiseviewerjfx.utilities.Vector2D;
import app.noiseviewerjfx.utilities.io.input.Keyboard;
import javafx.beans.value.ChangeListener;
import javafx.event.EventHandler;
import javafx.geometry.Bounds;
import javafx.scene.Cursor;
import javafx.scene.Node;
import javafx.scene.control.ScrollPane;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.ScrollEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.transform.Translate;

public class ZoomController {

    private final ScrollPane SCROLL_PANE;
    private final AnchorPane PLANE;
    private final Node CONTENT;

    private Vector2D lastContentPosition    = new Vector2D(0, 0);
    private Vector2D lastMousePosition      = new Vector2D(0, 0);
    private Vector2D dragOffset             = new Vector2D(0, 0);
    private final int SCROLL_AMOUNT         = 10;

    private final Keyboard KEYBOARD = new Keyboard() {
        @Override
        protected void onKeyPressed(KeyEvent keyEvent) {
            if (isKeyPressed(KeyCode.SPACE)) SCROLL_PANE.setCursor(Cursor.CLOSED_HAND);
            if (areAllKeysPressed(KeyCode.CONTROL, KeyCode.O)) resetView();

        }

        @Override
        protected void onKeyReleased(KeyEvent keyEvent) {
            if (isKeyReleased(KeyCode.SPACE)) SCROLL_PANE.setCursor(Cursor.DEFAULT);
        }
    };

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
        PLANE.setOnMouseDragged(drag());
        PLANE.setOnScroll(handleScroll());

        SCROLL_PANE.setOnKeyPressed(KEYBOARD);
        SCROLL_PANE.setOnKeyReleased(KEYBOARD);
        SCROLL_PANE.setOnMouseEntered(focusOntoScrollPane());
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

    private EventHandler<MouseEvent> focusOntoScrollPane() {
        return mouseEvent -> {
            SCROLL_PANE.requestFocus();
        };
    }

    private EventHandler<MouseEvent> drag() {
        return mouseEvent -> {

            if (!KEYBOARD.isKeyPressed(KeyCode.SPACE)) return;

            Vector2D newMousePosition = new Vector2D(mouseEvent.getX(), mouseEvent.getY());
            Vector2D mouseTranslation = lastMousePosition.sub(newMousePosition);
            lastMousePosition = newMousePosition;

            applyDrag(mouseTranslation);
        };
    }

    private EventHandler<ScrollEvent> handleScroll() {
        return scrollEvent -> {

            if (scrollEvent.getDeltaY() == 0 && scrollEvent.getDeltaX() == 0) return;

            scroll(scrollEvent);

            if (!KEYBOARD.isKeyPressed(KeyCode.CONTROL)) return;

            System.out.println(scrollEvent.getDeltaY());

        };
    }

    private void scroll(ScrollEvent scrollEvent) {

        final int deltaDrag;
        final Vector2D dragAmount;

        if (KEYBOARD.isKeyPressed(KeyCode.SHIFT)) {
            deltaDrag = scrollEvent.getDeltaX() > 0 ? SCROLL_AMOUNT : -SCROLL_AMOUNT;
            dragAmount = new Vector2D(deltaDrag, 0);
        } else {
            deltaDrag = scrollEvent.getDeltaY() > 0 ? SCROLL_AMOUNT : -SCROLL_AMOUNT;
            dragAmount = new Vector2D(0, deltaDrag);
        }

        applyDrag(dragAmount);
    }

    private void resetView() {
        dragOffset = new Vector2D();
        centerView();
    }

    private void centerView() {
        Vector2D contentSize    = getContentSize();
        Vector2D availableSpace = getAvailableSpace();
        Vector2D centerPosition = availableSpace.sub(contentSize).div(2);
        Vector2D translation    = centerPosition.sub(lastContentPosition);

        Translate translateToCenter = new Translate();
        translateToCenter.setX(translation.getX() + dragOffset.getX());
        translateToCenter.setY(translation.getY() + dragOffset.getY());

        CONTENT.getTransforms().add(translateToCenter);

        updateContentPosition();
    }

    private void applyDrag(Vector2D dragAmount) {
        Translate drag = new Translate();
        drag.setX(-dragAmount.getX());
        drag.setY(-dragAmount.getY());

        dragOffset = dragOffset.sub(dragAmount);

        CONTENT.getTransforms().add(drag);

        updateContentPosition();
    }

    private void updateContentPosition() {
        lastContentPosition = new Vector2D(getContentPosition());
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
