package app.noiseviewerjfx.utilities.generation.transformations;

/**
 * A pseudo-randomly generated 2D texture <br>
 * <i>see {@link PerlinNoiseTransformation} for a working implementation</i>
 */
public abstract class Noise implements FieldTransformation {

    protected final int WIDTH;
    protected final int HEIGHT;
    protected final double SCALE;

    public Noise(final int width, final int height, final double scale) {
        this.WIDTH  = width;
        this.HEIGHT = height;
        this.SCALE  = scale;
    }

    public int getWidth() {
        return WIDTH;
    }

    public int getHeight() {
        return HEIGHT;
    }

    public double getScale() {
        return SCALE;
    }

    public abstract Noise setScale(double scale);
}
