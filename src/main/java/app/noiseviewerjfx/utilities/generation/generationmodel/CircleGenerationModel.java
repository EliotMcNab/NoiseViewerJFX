package app.noiseviewerjfx.utilities.generation.generationmodel;

import app.noiseviewerjfx.utilities.generation.Grid;

import static app.noiseviewerjfx.utilities.ComplementaryMath.isInCircle;

public class CircleGenerationModel implements GridGenerationModel{

    private final int RADIUS;

    public CircleGenerationModel(final int radius) {
        this.RADIUS = radius;
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

        for (int y = 0; y < HEIGHT; y++) {
            for (int x = 0; x < WIDTH; x++) {
                grid[y * WIDTH + x] = isInCircle(x, y, CIRCLE_X, CIRCLE_Y, RADIUS) ? 1 : 0;
            }
        }

        return new Grid(grid, WIDTH, HEIGHT, 0, 1, other.getImageModel(), other.getGenerationModel());
    }
}
