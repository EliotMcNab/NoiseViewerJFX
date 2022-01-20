package app.noiseviewerjfx.utilities.generation.transformations;

import app.noiseviewerjfx.utilities.generation.Grid;
import app.noiseviewerjfx.utilities.generation.VectorField;

/**
 * A {@link FieldTransformation field transformation} used to layer multiple layers of {@link Noise} on top of each other
 */
public class FractalNoiseTransformation implements FieldTransformation {

    private Noise transformation;

    private final int WIDTH;
    private final int HEIGHT;

    private final double INITIAL_SCALE;
    private final double LACUNARITY;
    private final double PERSISTENCE;
    private final int OCTAVES;

    public FractalNoiseTransformation(
            Noise transformation, final double lacunarity, final double persistence, final int octaves) {

        this.transformation = transformation;

        this.WIDTH  = transformation.getWidth();
        this.HEIGHT = transformation.getHeight();

        this.INITIAL_SCALE  = transformation.getScale();
        this.LACUNARITY     = lacunarity;
        this.PERSISTENCE    = persistence;
        this.OCTAVES        = octaves;
    }

    private double scale(int n) {
        return INITIAL_SCALE / Math.pow(LACUNARITY, n);
    }

    private double force(int n) {
        return Math.pow(PERSISTENCE, n);
    }

    @Override
    public Grid transform(VectorField target) {

        target = target.resize((int) (WIDTH / INITIAL_SCALE), (int) (HEIGHT / INITIAL_SCALE));
        Grid fractalNoise = target.applyTransformation(transformation);

        for (int n = 1; n < OCTAVES; n++) {

            double s = scale(n);
            double f = force(n);

            target = target.resize((int) (WIDTH / s), (int) (HEIGHT / s));
            transformation = transformation.setScale(s);
            fractalNoise = fractalNoise.add(target.applyTransformation(transformation), f);
        }

        return fractalNoise;
    }
}
