package app.noiseviewerjfx.utilities.controller;

import javafx.event.EventHandler;
import javafx.scene.Cursor;
import javafx.scene.Node;
import javafx.scene.effect.BlurType;
import javafx.scene.effect.DropShadow;
import javafx.scene.effect.Glow;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;

import java.awt.*;

public class NodeController {

    /**
     * Changes the cursor upon hovering on a node
     * @param targetNode (Node): the node of we want to change the hover cursor of
     * @param newCursor (Cursor): the new cursor to apply upon hover
     * @return (EventHandler(MouseEvent)): listener for changes in the mouse's state
     */
    public static EventHandler<MouseEvent> changeNodeCursor(Node targetNode, Cursor newCursor) {
        return mouseEvent -> targetNode.setCursor(newCursor);
    }

    public static DropShadow generateDropShadow(
            BlurType blurType, Color color, final int radius, final double spread, final double offsetX, final double offsetY) {

        DropShadow dropShadow = new DropShadow();
        dropShadow.setBlurType(blurType);
        dropShadow.setColor(color);
        dropShadow.setRadius(radius);
        dropShadow.setSpread(spread);
        dropShadow.setOffsetX(offsetX);
        dropShadow.setOffsetY(offsetY);

        return dropShadow;
    }

    public static Glow generateGlow(final double level) {

        Glow glow = new Glow();
        glow.setLevel(level);

        return glow;
    }

}
