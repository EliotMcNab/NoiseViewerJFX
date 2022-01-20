package app.noiseviewerjfx.utilities.generation.transformations;

import app.noiseviewerjfx.utilities.Vector2D;
import app.noiseviewerjfx.utilities.generation.Grid;
import app.noiseviewerjfx.utilities.generation.VectorField;

import static app.noiseviewerjfx.utilities.ComplementaryMath.lerp;
import static app.noiseviewerjfx.utilities.ComplementaryMath.smoothStep;

/**
 * Generates Perlin {@link Noise} from a {@link VectorField vector field}
 */
public class PerlinNoiseTransformation extends Noise {

    public PerlinNoiseTransformation(final int width, final int height, final double scale) {
        super(width, height, scale);
    }

    @Override
    public PerlinNoiseTransformation setScale(double scale) {
        return new PerlinNoiseTransformation(WIDTH, HEIGHT, scale);
    }

    @Override
    public Grid transform(VectorField target) {

        double[] noiseGrid = new double[WIDTH * HEIGHT];

        double min = Double.MAX_VALUE;
        double max = Double.MIN_VALUE;

        for (int y = 0; y < HEIGHT; y++) {
            for (int x = 0; x < WIDTH; x++) {
                noiseGrid[y * WIDTH + x] = getNoiseAt(target, x, y);
                if (noiseGrid[y * WIDTH + x] < min) {
                    min = noiseGrid[y * WIDTH + x];
                }
                if (noiseGrid[y * WIDTH + x] > max) {
                    max = noiseGrid[y * WIDTH + x];
                }
            }
        }

        return new Grid(noiseGrid, WIDTH, HEIGHT, min, max, Grid.GRAYSCALE, Grid.RANDOM_GENERATION);
    }

    private double getNoiseAt(VectorField target, int x, int y) {

        // gets the scaled coordinates of the noise in the vector field
        // so that the noise is centered with the vector field
        Vector2D scaled = new Vector2D(x, y)
                .div(SCALE)
                .add(target.getWidth() / 2, target.getHeight() / 2)
                .sub((double) WIDTH / (2 * SCALE), (double) HEIGHT / (2 * SCALE));

        // coordinates of the closest gradient vector in the vector field
        Vector2D nearest = scaled.floor();

        // gets the neighbouring gradient vectors in the vector field
        Vector2D v1 = target.get((int) nearest.x, (int) nearest.y);
        Vector2D v2 = target.get((int) nearest.x + 1, (int) nearest.y);
        Vector2D v3 = target.get((int) nearest.x, (int) nearest.y + 1);
        Vector2D v4 = target.get((int) nearest.x + 1, (int) nearest.y + 1);

        // gets the local coordinates of the point we are sampling from
        Vector2D local = new Vector2D(scaled.x - nearest.x, scaled.y - nearest.y);

        // calculates the offset vectors to the candidate point from the surrounding vectors in the vector field
        Vector2D o1 = local.sub(0, 0);
        Vector2D o2 = local.sub(1, 0);
        Vector2D o3 = local.sub(0, 1);
        Vector2D o4 = local.sub(1, 1);

        // calculates the dot product between each gradient vector and offset vector
        double d1 = v1.dot(o1);
        double d2 = v2.dot(o2);
        double d3 = v3.dot(o3);
        double d4 = v4.dot(o4);

        // applying bi-linear interpolation to obtain noise value at the desired point
        double lerpX1 = lerp(d1, d2, smoothStep(local.x));
        double lerpX2 = lerp(d3, d4, smoothStep(local.x));
        double lerpY  = lerp(lerpX1, lerpX2, smoothStep(local.y));

        return lerpY;

        // returns the computed noise
    }
}
