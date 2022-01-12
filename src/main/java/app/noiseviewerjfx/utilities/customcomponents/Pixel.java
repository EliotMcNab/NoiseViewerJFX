package app.noiseviewerjfx.utilities.customcomponents;

import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class Pixel extends Rectangle {

    Pixel(final int size, final int x, final int y, final int r, final int g, final int b, final float alpha) {
        super(x * size, y * size, size, size);

        Color pixelColor = Color.rgb(r, g, b, alpha);

        setFill(pixelColor);
        setStroke(pixelColor);
    }

}
