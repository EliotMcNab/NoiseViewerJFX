package app.noiseviewerjfx.utilities.generation.generationmodel;
import app.noiseviewerjfx.utilities.generation.Grid;

import static app.noiseviewerjfx.utilities.ComplementaryMath.distanceToCircle;

public class GradientCircleGeneration implements GridGenerationModel{

    private final int RADIUS;
    private final double STRENGTH;

    public GradientCircleGeneration(final int radius, double strength) {
        this.RADIUS = radius;
        this.STRENGTH = strength;
    }

    @Override
    public Grid generate(Grid other, long seed) {
        double[] grid = new double[other.getSize()];

        // the size of the grid
        final int WIDTH  = other.getWidth();
        final int HEIGHT = other.getHeight();

        // the position of the circle, so it is at the center of the grid
        final int CIRCLE_X = WIDTH / 2;
        final int CIRCLE_Y = HEIGHT / 2;
        double maxDistanceToCircle = distanceToCircle(WIDTH, HEIGHT, CIRCLE_X, CIRCLE_Y, RADIUS);

        for (int y = 0; y < HEIGHT; y++) {
            for (int x = 0; x < WIDTH; x++) {

                // calculates the distance  of the current coordinates to the edge circle
                double distanceToCircleEdge = distanceToCircle(x, y, CIRCLE_X, CIRCLE_Y, RADIUS);
                if (distanceToCircleEdge < 0) distanceToCircleEdge = 0;

                // saves the ratio between the point's distance to the circle
                // and the maximum possible distance to the circle to obtain gradient
                int i = y * WIDTH + x;
                grid[i] = distanceToCircleEdge / maxDistanceToCircle * STRENGTH;
                if (grid[i] > 1) grid[i] = 1;
            }
        }

        return new Grid(grid, WIDTH, HEIGHT, 0, 1, other.getImageModel(), other.getGenerationModel());

    }
}
