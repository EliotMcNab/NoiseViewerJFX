package app.noiseviewerjfx.utilities.generation.generationmodel;

import app.noiseviewerjfx.utilities.Vector2D;
import app.noiseviewerjfx.utilities.generation.Grid;
import app.noiseviewerjfx.utilities.geometry.Rectangle;

public class GradientRectangleGeneration implements GridGenerationModel{

    final int width;
    final int height;
    final double strength;

    public GradientRectangleGeneration(final int width, final int height, double strength) {
        this.width = width;
        this.height = height;
        this.strength = strength;
    }

    @Override
    public Grid generate(Grid other, long seed) {


        // the size of the grid
        final int HEIGHT = other.getHeight();
        final int WIDTH  = other.getWidth();

        // the rectangle on which the grid is being generated
        Vector2D rectangleEdge = new Vector2D((WIDTH - width) / 2, (HEIGHT - height) / 2);
        Rectangle rectangle = new Rectangle(width, height, rectangleEdge);

        // longest distance to the rectangle
        final double longestDistanceTo = rectangle.closestDistanceTo(0, 0);

        double[] grid = new double[other.getSize()];

        for (int y = 0; y < HEIGHT; y++) {
            for (int x = 0; x < WIDTH; x++) {
                int i = y * WIDTH + x;
                grid[i] = rectangle.closestDistanceTo(x, y) / longestDistanceTo * strength;
                if (grid[i] > 1) grid[i] = 1;
            }
        }

        return new Grid(grid, WIDTH, HEIGHT, 0, 1, other.getImageModel(), other.getGenerationModel());

    }
}
