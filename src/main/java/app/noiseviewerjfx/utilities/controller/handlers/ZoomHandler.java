package app.noiseviewerjfx.utilities.controller.handlers;

import app.noiseviewerjfx.utilities.Vector2D;
import app.noiseviewerjfx.utilities.controller.NodeController;
import app.noiseviewerjfx.utilities.io.input.Keyboard;
import javafx.animation.ScaleTransition;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.event.EventHandler;
import javafx.geometry.Bounds;
import javafx.geometry.Point2D;
import javafx.scene.Cursor;
import javafx.scene.Node;
import javafx.scene.control.ScrollPane;
import javafx.scene.effect.BlurType;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.ScrollEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.transform.Scale;
import javafx.scene.transform.Translate;
import javafx.util.Duration;

public class ZoomHandler {

    private final ScrollPane SCROLL_PANE;
    private final AnchorPane PLANE;
    private final Node CONTENT;

    private Point2D lastMousePosition       = new Point2D(0, 0);
    private Point2D dragOffset              = new Point2D(0, 0);
    private boolean dragInitialised         = false;
    private final int SCROLL_AMOUNT         = 10;
    private double scale = 1;

    private long lastUpdateCycle = 0;
    private final int DELAY      = 10;

    private final Keyboard KEYBOARD = new Keyboard() {
        @Override
        protected void onKeyPressed(KeyEvent keyEvent) {
            if (isKeyPressed(KeyCode.SPACE)) selectImage();
            if (areAllKeysPressed(KeyCode.CONTROL, KeyCode.SHIFT, KeyCode.R)) resetView();
            if (areAllKeysPressed(KeyCode.CONTROL, KeyCode.SHIFT, KeyCode.O)) returnToOrigin();
        }

        @Override
        protected void onKeyReleased(KeyEvent keyEvent) {
            if (isKeyReleased(KeyCode.SPACE)) {
                deselectImage();
                dragInitialised = false;
            }
        }
    };

    public ZoomHandler(
            ScrollPane scrollPane,
            AnchorPane plane,
            Node content
    ) {
        this.SCROLL_PANE    = scrollPane;
        this.PLANE          = plane;
        this.CONTENT        = content;

        addListeners();

        Platform.runLater(SCROLL_PANE::requestFocus);
    }

    private void addListeners() {
        PLANE.heightProperty().addListener(centerContent());
        PLANE.widthProperty().addListener(centerContent());

        PLANE.setOnMouseMoved(getMousePosition());
        PLANE.setOnMousePressed(initialiseDrag());
        PLANE.setOnMouseReleased(endDrag());
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
            lastMousePosition = new Point2D(mouseEvent.getX(), mouseEvent.getY());
        };
    }

    private EventHandler<MouseEvent> focusOntoScrollPane() {
        return mouseEvent -> {
            SCROLL_PANE.requestFocus();
        };
    }

    private void selectImage() {
        SCROLL_PANE.setCursor(Cursor.CLOSED_HAND);
    }

    private void pickUpImage() {

        ScaleTransition pickupScale = new ScaleTransition(
                Duration.millis(50),
                CONTENT
        );

        pickupScale.setToX(1.02);
        pickupScale.setToY(1.02);
        pickupScale.play();

        CONTENT.setEffect(NodeController.generateDropShadow(
                BlurType.GAUSSIAN,
                Color.rgb(18, 18, 23, 0.5),
                10,
                0.5,
                10,
                10
        ));
    }

    private void deselectImage() {
        SCROLL_PANE.setCursor(Cursor.DEFAULT);
        dropImage();
    }

    private void dropImage() {

        if (!dragInitialised) return;

        ScaleTransition pickupScale = new ScaleTransition(
                Duration.millis(50),
                CONTENT
        );

        pickupScale.setToX(1/1.02);
        pickupScale.setToY(1/1.02);
        pickupScale.play();

        CONTENT.setEffect(NodeController.generateDropShadow(
                BlurType.GAUSSIAN,
                Color.rgb(18, 18, 23, 0.5),
                10,
                0.5,
                5,
                5
        ));
    }

    private EventHandler<MouseEvent> initialiseDrag() {
        return mouseEvent -> {

            if (!KEYBOARD.isKeyPressed(KeyCode.SPACE)) return;
            if (System.currentTimeMillis() - lastUpdateCycle <= 10) return;
            lastUpdateCycle = System.currentTimeMillis();

            pickUpImage();
            dragInitialised = true;
        };
    }

    private EventHandler<MouseEvent> endDrag() {
        return mouseEvent -> {

            if (!dragInitialised) return;
            if (System.currentTimeMillis() - lastUpdateCycle <= 10) return;
            lastUpdateCycle = System.currentTimeMillis();

            dropImage();
            dragInitialised = false;
        };
    }

    private EventHandler<MouseEvent> drag() {
        return mouseEvent -> {

            if (!KEYBOARD.isKeyPressed(KeyCode.SPACE)) return;

            if (System.currentTimeMillis() - lastUpdateCycle <= DELAY) return;
            lastUpdateCycle = System.currentTimeMillis();

            Point2D newMousePosition = new Point2D(mouseEvent.getX(), mouseEvent.getY());
            Point2D mouseTranslation = lastMousePosition.subtract(newMousePosition);
            lastMousePosition = newMousePosition;

            applyDrag(mouseTranslation);
        };
    }

    private EventHandler<ScrollEvent> handleScroll() {
        return scrollEvent -> {

            if (scrollEvent.getDeltaY() == 0 && scrollEvent.getDeltaX() == 0) return;

            if (System.currentTimeMillis() - lastUpdateCycle <= 10) return;
            lastUpdateCycle = System.currentTimeMillis();

            if (KEYBOARD.isKeyPressed(KeyCode.CONTROL)) {
                zoom(scrollEvent);
            } else {
                scroll(scrollEvent);
            }

        };
    }

    private void scroll(ScrollEvent scrollEvent) {

        final int deltaDrag;
        final Point2D dragAmount;

        if (KEYBOARD.isKeyPressed(KeyCode.SHIFT)) {
            deltaDrag = scrollEvent.getDeltaX() > 0 ? -SCROLL_AMOUNT : SCROLL_AMOUNT;
            dragAmount = new Point2D(deltaDrag, 0);
        } else {
            deltaDrag = scrollEvent.getDeltaY() > 0 ? SCROLL_AMOUNT : -SCROLL_AMOUNT;
            dragAmount = new Point2D(0, deltaDrag);
        }

        applyDrag(dragAmount);
    }

    private void resetView() {
        resetZoom();
        resetDrag();
        centerView();
    }

    private void returnToOrigin() {
        resetDrag();
        centerView();
    }

    private void centerView() {
        // gets the coordinates we need to place the image at for it to be centered
        Point2D centerPosition = CONTENT.parentToLocal(getCenterEdge().toPoint2D());
        // calculates the path to take from the image's current position
        // to the position it has to be at to be centered
        // ie: the displacement vector
        Point2D translation    = centerPosition.subtract(getContentPosition());

        // applies the necessary translation to the image...
        Translate translateToCenter = new Translate();
        // ...while account for drag so image is not fully re-centered
        translateToCenter.setX(translation.getX() + dragOffset.getX());
        translateToCenter.setY(translation.getY() + dragOffset.getY());
        CONTENT.getTransforms().add(translateToCenter);
    }

    private void resetDrag() {
        dragOffset = new Point2D(0, 0);
        applyDrag(new Point2D(0, 0));
    }

    private void applyDrag(Point2D dragAmount) {

        // updates the total displacement caused by drag (used when we re-center the image)
        dragOffset = dragOffset.subtract(dragAmount);

        // applies the necessary translation to the image...
        Translate drag = new Translate();
        // ...based on the mouse's movement
        drag.setX(-dragAmount.getX());
        drag.setY(-dragAmount.getY());
        CONTENT.getTransforms().add(drag);
    }

    private void resetZoom() {
        applyZoom(1 / scale, getContentSize().div(2).toPoint2D());
        scale = 1;
    }

    private void zoom(ScrollEvent scrollEvent) {

        // adds or subtracts to the image's scale based on
        // whether user is scrolling backwards or forwards
        final double dScale = scrollEvent.getDeltaY() > 0 ? 0.1 : -0.1;
        scale += dScale;

        // gets the coordinates IN THE IMAGE's FRAME OF REFERENCE
        // of the point at which to zoom the image so it is centered on the mouse
        Point2D target = CONTENT.parentToLocal(new Point2D(scrollEvent.getX(), scrollEvent.getY()));

        // applies the zoom to the image
        applyZoom(1 + dScale, target);
    }

    private void applyZoom(final double zoomAmount, Point2D target) {
        // applies the necessary scaling to the image...
        Scale zoom = new Scale(zoomAmount, zoomAmount);
        // ...and centers the scaling to the point where the mouse is located at
        zoom.setPivotY(target.getY());
        zoom.setPivotX(target.getX());
        CONTENT.getTransforms().add(zoom);
    }

    private Vector2D getContentSize() {
        // gets the bounds around the image
        Bounds contentBounds = CONTENT.getBoundsInParent();
        return new Vector2D(contentBounds.getWidth(), contentBounds.getHeight());
    }

    private Point2D getContentPosition() {
        // gets the minimal coordinates of the bounds around the image
        // ie: the image's coordinates
        Bounds contentBounds = CONTENT.getBoundsInLocal();
        return new Point2D(contentBounds.getMinX(), contentBounds.getMinY());
    }

    private Vector2D getParentSize() {
        // gets the size of the Anchorpane the image is inn
        return new Vector2D(PLANE.getWidth(), PLANE.getHeight());
    }

    private Vector2D getCenterEdge() {
        // gets the size of the image and the anchor pane it is in...
        Vector2D contentSize    = getContentSize();
        Vector2D availableSpace = getParentSize();
        // ...to determine the coordinates at which to place the image for it to be centerd
        return availableSpace.sub(contentSize).div(2);
    }

    private Vector2D getContentCenter() {
        // gets the central coordinates of the bounds around the image
        // ie: the center of the image
        Bounds contentBounds = CONTENT.getBoundsInLocal();
        return new Vector2D(contentBounds.getCenterX(), contentBounds.getCenterY());
    }

}
