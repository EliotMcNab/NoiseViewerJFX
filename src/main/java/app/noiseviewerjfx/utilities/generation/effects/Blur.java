package app.noiseviewerjfx.utilities.generation.effects;

import app.noiseviewerjfx.utilities.generation.Grid;

public class Blur implements GridEffect {

    private final int STRENGTH;

    public Blur(final int strength) {
        this.STRENGTH = strength;
    }

    @Override
    public Grid applyTo(Grid target) {
        double[] grid = new double[target.getSize()];

        // dimensions of the grid
        final int HEIGHT = target.getHeight();
        final int WIDTH  = target.getWidth();

        for (int i = 0; i < STRENGTH; i++) {
            for (int y = 0; y < HEIGHT; y++) {
                for (int x = 0; x < WIDTH; x++) {
                    grid[y * HEIGHT + x] = (
                            target.get(x - 1, y - 1) +
                            target.get(x, y - 1) +
                            target.get(x + 1, y - 1) +
                            target.get(x - 1, y) +
                            target.get(x, y) +
                            target.get(x + 1, y) +
                            target.get(x - 1, y + 1) +
                            target.get(x, y + 1) +
                            target.get(x + 1, y + 1)
                    ) / 9;
                }
            }
            target = new Grid(grid, WIDTH, HEIGHT, target.getMin(), target.getMax());
        }

        return new Grid(grid, WIDTH, HEIGHT, target.getMin(), target.getMax());
    }
}
