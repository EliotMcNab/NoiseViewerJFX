package app.noiseviewerjfx.utilities.generation.generationmodel;

import app.noiseviewerjfx.utilities.Vector2D;
import app.noiseviewerjfx.utilities.generation.Grid;

import static app.noiseviewerjfx.utilities.ComplementaryMath.isInRectangle;

public class RectangleGeneration implements GridGenerationModel{

    private final int w;
    private final int h;

    public RectangleGeneration(final int width, final int height) {
        this.w = width;
        this.h = height;
    }

    @Override
    public Grid generate(Grid other, long seed) {

        // the size of the grid
        final int WIDTH = other.getWidth();
        final int HEIGHT = other.getHeight();

        final Vector2D d = new Vector2D(WIDTH - w, HEIGHT - h).div(2);

        double[] grid = new double[other.getSize()];
        for (int y = 0; y < HEIGHT; y++) {
            for (int x = 0; x < WIDTH; x++) {
                grid[y * WIDTH + x] = isInRectangle(x, y, (int) d.x, (int) d.y, w, h) ? 0 : 1;
            }
        }

        return new Grid(grid, WIDTH, HEIGHT, 0, 1, other.getImageModel(), other.getGenerationModel());
    }
}
