package app.noiseviewerjfx.utilities.processing;

public class ColorProcessing {

    public static int grayScaleToArgb(final int grayScale) {
        return (0xFF << 24) | grayScale << 16 | grayScale << 8 | grayScale;
    }

}
