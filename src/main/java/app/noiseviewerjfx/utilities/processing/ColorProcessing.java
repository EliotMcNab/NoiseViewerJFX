package app.noiseviewerjfx.utilities.processing;

public class ColorProcessing {

    public static int grayScaleToArgb(final int grayScale) {
        return (0xFF << 24) | grayScale << 16 | grayScale << 8 | grayScale;
    }

    public static int argb(int a, int r, int g, int b) {
        return (a << 24) | (r << 16) | (g << 8) | b;
    }

}
