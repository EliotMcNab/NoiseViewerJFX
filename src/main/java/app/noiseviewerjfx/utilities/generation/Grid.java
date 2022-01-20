package app.noiseviewerjfx.utilities.generation;

import app.noiseviewerjfx.utilities.Vector2D;
import app.noiseviewerjfx.utilities.generation.ImageModel.ImageModel;
import app.noiseviewerjfx.utilities.generation.effects.GridEffect;
import app.noiseviewerjfx.utilities.generation.errors.GenerationError;
import app.noiseviewerjfx.utilities.generation.generationmodel.GenerationModel;
import app.noiseviewerjfx.utilities.generation.generationmodel.GridGenerationModel;
import app.noiseviewerjfx.utilities.generation.transformations.GridTransformation;
import app.noiseviewerjfx.utilities.processing.ColorProcessing;
import javafx.scene.image.Image;
import javafx.scene.image.PixelWriter;
import javafx.scene.image.WritableImage;

import java.util.Arrays;
import java.util.Random;

/**
 * A {@link Plane plane} composed of double values, which can ge {@link Generated generated} using a
 * {@link GridGenerationModel grid generation model} and have {@link GridTransformation grid transformations}
 * or {@link GridEffect grid effects} applied to it
 */
public class Grid implements Plane, Generated, Cloneable {

    private double[] grid = new double[1];
    private final int WIDHT;
    private final int HEIGHT;

    private double MIN;
    private double MAX;

    private GridGenerationModel generationModel = RANDOM_GENERATION;
    private ImageModel imageModel = GRAYSCALE;

    // =====================================
    //             CONSTRUCTORS
    // =====================================

    public Grid(final int width, final int height) {
        this.WIDHT  = width;
        this.HEIGHT = height;
    }

    public Grid(Grid otherGrid) {
        this(otherGrid.grid, otherGrid.WIDHT, otherGrid.HEIGHT, otherGrid.MIN, otherGrid.MAX);
        imageModel = otherGrid.imageModel;
        generationModel = otherGrid.generationModel;
    }

    private Grid(double[] gridArray, final int width, final int height, final double min, final double max) {
        this.WIDHT  = width;
        this.HEIGHT = height;
        this.MIN    = min;
        this.MAX    = max;
        this.grid   = Arrays.copyOf(gridArray, gridArray.length);
    }

    public Grid(double[] gridArray, final int width, final int height, final double min, final double max, ImageModel imageModel, GridGenerationModel generationModel) {
        this.WIDHT  = width;
        this.HEIGHT = height;
        this.MIN    = min;
        this.MAX    = max;
        this.grid   = Arrays.copyOf(gridArray, gridArray.length);
        this.imageModel = imageModel;
        this.generationModel = generationModel;
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

    public Grid applyEffect(GridEffect effect) {
        checkGeneration();
        return effect.applyTo(this);
    }

    public Grid applyAllEffects(GridEffect... effects) {
        Grid copy = this;

        for (GridEffect effect : effects) {
            copy = copy.applyEffect(effect);
        }

        return copy;
    }

    public Grid add(Grid other) {
        return add(other, 1);
    }

    public Grid add(Grid other, double strength) {
        double[] grid = new double[getSize()];

        double min = MAX + other.MAX * strength;
        double max = MIN + other.MIN * strength;

        for (int y = 0; y < HEIGHT; y++) {
            for (int x = 0; x < WIDHT; x++) {
                int i = y * WIDHT + x;
                grid[i] = get(x, y) + other.get(x, y) * strength;
                if (grid[i] < min) min = grid[i];
                if (grid[i] > max) max = grid[i];
            }
        }

        return new Grid(grid, WIDHT, HEIGHT, min, max);
    }

    @Override
    protected Grid clone() {
        return new Grid(this);
    }

    // =====================================
    //             GENERATION
    // =====================================

    @Override
    public Grid setGenerationModel(GenerationModel generationModel) {
        Grid clone = clone();
        clone.generationModel = (GridGenerationModel) generationModel;
        return clone;
    }

    @Override
    public GridGenerationModel getGenerationModel() {
        return generationModel;
    }

    @Override
    public Grid generate(long seed) {
        Grid generated = generationModel.generate(this, seed);

        return new Grid(
                generated.grid,
                generated.WIDHT,
                generated.HEIGHT,
                generated.MIN,
                generated.MAX,
                imageModel,
                generationModel
        );
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

    // =====================================
    //          IMAGE CONVERSION
    // =====================================

    private void checkGeneration() {
        if (grid != null) return;
        throw new GenerationError(this);
    }

    public Image toGrayscaleImage() {
        checkGeneration();
        return imageModel.toImage(this);
    }

    public Grid setImageModel(ImageModel newModel) {
        Grid clone = clone();
        clone.imageModel = newModel;
        return clone;
    }

    public ImageModel getImageModel() {
        return imageModel;
    }

    public static ImageModel GRAYSCALE = plane -> {

        WritableImage grayscale = new WritableImage(plane.getWidth(), plane.getHeight());
        PixelWriter pixelWriter = grayscale.getPixelWriter();

        for (int y = 0; y < plane.getHeight(); y++) {
            for (int x = 0; x < plane.getWidth(); x++) {
                final int pixelValue = (int) ((plane.get(x, y) + Math.abs(plane.getMin())) / (plane.getMax() + Math.abs(plane.getMin())) * 255);
                pixelWriter.setArgb(x, y, ColorProcessing.grayScaleToArgb(pixelValue));
            }
        }

        return grayscale;
    };

    public static ImageModel OPACITY = plane -> {

        WritableImage maskImage = new WritableImage(plane.getWidth(), plane.getHeight());
        PixelWriter maskPixelWriter = maskImage.getPixelWriter();

        for (int y = 0; y < plane.getHeight(); y++) {
            for (int x = 0; x < plane.getWidth(); x++) {
                maskPixelWriter.setArgb(x, y, ColorProcessing.argb((int) (plane.get(x, y) * 255), 0, 0, 0));
            }
        }

        return maskImage;

    };

}
