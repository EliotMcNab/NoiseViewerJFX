package app.noiseviewerjfx.utilities.generation.effects;

import app.noiseviewerjfx.utilities.generation.Grid;

public class Upscale implements GridEffect{

    private final int SCALE;

    public Upscale(final int scale) {
        this.SCALE = scale;
    }

    @Override
    public Grid applyTo(Grid target) {

        // the size of the grid
        final int HEIGHT = target.getHeight();
        final int WIDTH  = target.getWidth();

        // the size of the grid once it has been scaled
        final int wS = WIDTH * SCALE;
        final int hS = HEIGHT * SCALE;

        // the scaled grid
        double[] grid = new double[wS * hS];

        // gets the values in the grid
        for (int y = 0; y < HEIGHT; y++) {
            for (int x = 0; x < WIDTH; x++) {
                // scales the grid
                for (int dy = 0; dy < SCALE; dy++) {
                    for (int dx = 0; dx < SCALE; dx++) {
                        // converts the coordinates in the original grid to coordinates in the scaled grid
                        int yS = y * SCALE + dy;
                        int xS = x * SCALE + dx;
                        // converts the coordinates in the scaled grid to coordinates in the 1D grid array
                        int i = yS * wS + xS;
                        grid[i] = target.get(x, y);
                    }
                }
            }
        }

        return new Grid(grid, wS, hS, target.getMin(), target.getMax());
    }
}
