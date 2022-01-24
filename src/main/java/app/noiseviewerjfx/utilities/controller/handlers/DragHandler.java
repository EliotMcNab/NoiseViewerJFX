package app.noiseviewerjfx.utilities.controller.handlers;

import app.noiseviewerjfx.utilities.Vector2D;
import app.noiseviewerjfx.utilities.generation.transformations.Transformation;
import javafx.scene.Node;
import javafx.scene.transform.Translate;

import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class DragHandler {

    private static final Map<Node, Vector2D> mouseAnchors = new HashMap<>();

    public static void makeDraggable(Node node) {
        makeDraggable(node, Axis.X_AXIS, Axis.Y_AXIS);
    }

    public static void makeDraggable(Node node, Axis... axis) {
        mouseAnchors.put(node, new Vector2D());
        clickListeners(node);
        dragListeners(node, axis);
    }

    public static void clickListeners(Node node) {
        node.setOnMouseMoved(mouseEvent -> {
            Vector2D mouseAnchor = new Vector2D(mouseEvent.getX(), mouseEvent.getY());
            mouseAnchors.replace(node, mouseAnchor);
        });
    }

    public static void dragListeners(Node node, Axis... axis) {

        final boolean alongX = Arrays.binarySearch(axis, Axis.X_AXIS) >= 0;
        final boolean alongY = Arrays.binarySearch(axis, Axis.Y_AXIS) >= 0;

        if (alongX && alongY) {
            node.setOnMouseDragged(mouseEvent -> {
                Translate drag = new Translate();
                Vector2D lastPosition = mouseAnchors.get(node);
                drag.setX(mouseEvent.getX() - lastPosition.x);
                drag.setY(mouseEvent.getY() - lastPosition.y);
                node.getTransforms().add(drag);
            });
        } else if (alongX) {
            node.setOnMouseDragged(mouseEvent -> {
                Translate drag = new Translate();
                Vector2D lastPosition = mouseAnchors.get(node);
                drag.setX(mouseEvent.getX() - lastPosition.x);
                node.getTransforms().add(drag);
            });
        } else if (alongY) {
            node.setOnMouseDragged(mouseEvent -> {
                Translate drag = new Translate();
                Vector2D lastPosition = mouseAnchors.get(node);
                drag.setY(mouseEvent.getY() - lastPosition.y);
                node.getTransforms().add(drag);
            });
        }
    }

    public enum Axis {
        X_AXIS,
        Y_AXIS
    }

}
