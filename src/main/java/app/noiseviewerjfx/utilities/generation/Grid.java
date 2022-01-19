package app.noiseviewerjfx.utilities.generation;

import app.noiseviewerjfx.utilities.ComplementaryMath;
import app.noiseviewerjfx.utilities.Vector2D;
import app.noiseviewerjfx.utilities.generation.effects.GridEffect;
import app.noiseviewerjfx.utilities.generation.errors.GenerationError;
import app.noiseviewerjfx.utilities.processing.ColorProcessing;
import javafx.scene.image.Image;
import javafx.scene.image.PixelWriter;
import javafx.scene.image.WritableImage;

import java.util.Arrays;
import java.util.Random;

public class Grid implements Plane, Generated, Cloneable {

    private double[] grid;
    private final int WIDHT;
    private final int HEIGHT;

    private double MIN;
    private double MAX;

    private double max_Value;
    private double min_Value;

    private GridGenerationModel generationModel = RANDOM_GENERATION;

    // =====================================
    //             CONSTRUCTORS
    // =====================================

    public Grid(final int width, final int height) {
        this.WIDHT  = width;
        this.HEIGHT = height;
    }

    public Grid(Grid otherGrid) {
        this(otherGrid.grid, otherGrid.WIDHT, otherGrid.HEIGHT, otherGrid.MIN, otherGrid.MAX);
    }

    public Grid(double[] gridArray, final int width, final int height, final double min, final double max) {
        this.WIDHT  = width;
        this.HEIGHT = height;
        this.MIN    = min;
        this.MAX    = max;
        this.grid   = Arrays.copyOf(gridArray, WIDHT * HEIGHT);

        this.max_Value = MIN;
        this.min_Value = MAX;

        normalise();
    }

    // =====================================
    //              ACCESSORS
    // =====================================

    @Override
    public int getWidth() {
        return WIDHT;
    }

    @Override
    public int getHeight() {
        return HEIGHT;
    }

    @Override
    public int getSize() {
        return WIDHT * HEIGHT;
    }

    public double getMin() {
        return MIN;
    }

    public double getMax() {
        return MAX;
    }

    private Vector2D tileCoords(int x, int y) {
        x = x >= 0 ? x % WIDHT  : WIDHT + (x % WIDHT);
        y = y >= 0 ? y % HEIGHT : HEIGHT + (y % HEIGHT);
        return new Vector2D(x, y);
    }

    @Override
    public Double get(int x, int y) {
        Vector2D P = tileCoords(x, y);
        return grid[(int) P.getY() * WIDHT + (int) P.getX()];
    }

    @Override
    public Double valAt(int i) {
        return grid[i];
    }

    public Vector2D coordinatesFromPosition(int i) {
        return new Vector2D(i % WIDHT, i / WIDHT);
    }

    // =====================================
    //       TRANSFORMATIONS & EFFECTS
    // =====================================

    private void normalise() {
        for (int i = 0; i < grid.length; i++) {
            grid[i] = ComplementaryMath.mapToDouble(grid[i], min_Value, max_Value, MIN, MAX);
        }
    }

    public Grid applyEffect(GridEffect effect) {
        checkGeneration();
        return effect.applyTo(this);
    }

    // =====================================
    //               IMAGE
    // =====================================

    private void checkGeneration() {
        if (grid != null) return;
        throw new GenerationError(this);
    }

    public Image toGrayscaleImage() {

        checkGeneration();

        WritableImage grayscale = new WritableImage(WIDHT, HEIGHT);
        PixelWriter pixelWriter = grayscale.getPixelWriter();

        for (int y = 0; y < HEIGHT; y++) {
            for (int x = 0; x < WIDHT; x++) {
                final int pixelValue = (int) ((get(x, y) + Math.abs(MIN)) / (MAX + Math.abs(MIN)) * 255);
                pixelWriter.setArgb(x, y, ColorProcessing.grayScaleToArgb(pixelValue));
            }
        }

        return grayscale;
    }

    @Override
    protected Grid clone() {
        return new Grid(
                Arrays.copyOf(grid, WIDHT * HEIGHT),
                WIDHT,
                HEIGHT,
                MIN,
                MAX
        );
    }

    // =====================================
    //             GENERATION
    // =====================================

    @Override
    public void setGenerationModel(GenerationModel generationModel) {
        this.generationModel = (GridGenerationModel) generationModel;
    }

    @Override
    public GridGenerationModel getGenerationModel() {
        return generationModel;
    }

    @Override
    public void generate(long seed) {
        Grid generated = generationModel.generate(this, seed);

        this.grid = generated.grid;
        this.min_Value = generated.min_Value;
        this.max_Value = generated.max_Value;
        this.MIN = generated.MIN;
        this.MAX = generated.MAX;
    }

    public static GridGenerationModel RANDOM_GENERATION = (other, seed) -> {
        Random random = new Random(seed);

        double[] grid = new double[other.getSize()];
        double min = 1;
        double max = -1;
        for (int i = 0; i < other.getSize(); i++) {
            grid[i] = random.nextDouble() * 2 - 1;
            if (grid[i] > max) {
                max = grid[i];
            }
            if (grid[i] < min) {
                min = grid[i];
            }
        }

        return new Grid(grid, other.getWidth(), other.getHeight(), min, max);
    };

    public static GridGenerationModel GRADIENT_DOWN = (other, seed) -> {
        double[] grid = new double[other.getSize()];
        for (int i = 0; i < other.getHeight(); i++) {
            Arrays.fill(grid, i * other.getWidth(), (i + 1) * other.getWidth(), i);
        }
        double min = grid[0];
        double max = grid[grid.length - 1];

        return new Grid(grid, other.getWidth(), other.getHeight(), min, max);
    };

    public static GridGenerationModel GRADIENT_UP = (other, seed) -> {
        double[] grid = new double[other.getSize()];
        for (int i = 0; i < other.getHeight(); i++) {
            Arrays.fill(grid, i * other.getWidth(), (i + 1) * other.getWidth(), other.getHeight() - i);
        }
        double max = grid[0];
        double min = grid[grid.length - 1];

        return new Grid(grid, other.getWidth(), other.getHeight(), min, max);
    };

    public static GridGenerationModel GRADIENT_RIGHT = (other, seed) -> {
        double[] grid = new double[other.getSize()];
        for (int i = 0; i < grid.length; i++) {
            grid[i] = i % other.getWidth();
        }

        double min = grid[0];
        double max = grid[other.getWidth() - 1];

        return new Grid(grid, other.getWidth(), other.getHeight(), min, max);
    };

    public static GridGenerationModel GRADIENT_LEFT = (other, seed) -> {
        double[] grid = new double[other.getSize()];
        for (int i = 0; i < grid.length; i++) {
            grid[i] = other.getWidth() - i % other.getWidth();
        }

        double min = grid[other.getWidth() - 1];
        double max = grid[0];

        return new Grid(grid, other.getWidth(), other.getHeight(), min, max);
    };

    public static GridGenerationModel FULL = (other, seed) -> {
        double[] grid = new double[other.getSize()];
        for (int i = 0; i < grid.length; i++) {
            grid[i] = 1;
        }

        double min = 0;
        double max = 1;

        return new Grid(grid, other.getWidth(), other.getHeight(), min, max);
    };

    public static GridGenerationModel EMPTY = (other, seed) -> {
        double[] grid = new double[other.getSize()];
        for (int i = 0; i < other.getSize(); i++) {
            grid[i] = 0;
        }

        double min = 0;
        double max = 1;

        return new Grid(grid, other.getWidth(), other.getHeight(), min, max);
    };

}
