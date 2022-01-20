package app.noiseviewerjfx.utilities.generation;

import app.noiseviewerjfx.utilities.Vector2D;
import app.noiseviewerjfx.utilities.generation.errors.GenerationError;
import app.noiseviewerjfx.utilities.generation.generationmodel.FieldGenerationModel;
import app.noiseviewerjfx.utilities.generation.generationmodel.GenerationModel;
import app.noiseviewerjfx.utilities.generation.transformations.FieldTransformation;

import java.util.Arrays;
import java.util.Random;

/**
 * A {@link Plane} composed of {@link Vector2D Vectors}, which can ge {@link Generated} using a {@link FieldGenerationModel}
 * and have {@link FieldTransformation field transformations} or
 * {@link app.noiseviewerjfx.utilities.generation.effects.FieldEffect field effects} applied to it
 */
public class VectorField implements Plane, Generated, Cloneable {

    // =====================================
    //               FIELDS
    // =====================================

    private Vector2D[] grid = new Vector2D[1];
    private final int WIDHT;
    private final int HEIGHT;
    private FieldGenerationModel generationModel = RANDOM_GENERATION;

    // =====================================
    //            CONSTRUCTORS
    // =====================================

    public VectorField(final int width, final int height) {
        this.WIDHT  = width;
        this.HEIGHT = height;
    }

    public VectorField(VectorField otherGrid) {
        this(otherGrid.grid, otherGrid.WIDHT, otherGrid.HEIGHT);
        generationModel = otherGrid.generationModel;
    }

    public VectorField(Vector2D[] gridArray, final int width, final int height) {
        this.WIDHT  = width;
        this.HEIGHT = height;
        this.grid   = Arrays.copyOf(gridArray, gridArray.length);
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

    /**
     * Wraps around the filed if the coordinates are out of bounds
     * @param x (int): x-coordinates
     * @param y (int): y-coordinates
     * @return (Vector2D): the coordinates of the tile in the field, wrapped around if necessary
     */
    private Vector2D tileCoords(int x, int y) {
        x = x >= 0 ? x % WIDHT  : WIDHT + (x % WIDHT);
        y = y >= 0 ? y % HEIGHT : HEIGHT + (y % HEIGHT);
        return new Vector2D(x, y);
    }

    @Override
    public Vector2D get(int x, int y) {
        Vector2D P = tileCoords(x, y);
        return grid[(int) P.getY() * WIDHT + (int) P.getX()];
    }

    @Override
    public Vector2D valAt(int i) {
        i %= getSize();
        return grid[i];
    }

    public Vector2D coordinatesFromPosition(int i) {
        return new Vector2D(i % WIDHT, i / WIDHT);
    }

    // =====================================
    //       TRANSFORMATIONS & EFFECTS
    // =====================================

    private void checkGeneration() {
        if (grid != null) return;
        throw new GenerationError(this);
    }

    public Grid applyTransformation(FieldTransformation effect) {
        checkGeneration();
        return effect.transform(this);
    }

    @Override
    protected VectorField clone() {
        return new VectorField(this);
    }

    public VectorField resize(int newWidth, int newHeight) {
        return new VectorField(grid, newWidth, newHeight);
    }

    // =====================================
    //              GENERATION
    // =====================================

    @Override
    public VectorField setGenerationModel(GenerationModel generationModel) {
        VectorField clone = clone();
        clone.generationModel = (FieldGenerationModel) generationModel;
        return clone;
    }

    @Override
    public FieldGenerationModel getGenerationModel() {
        return generationModel;
    }

    @Override
    public VectorField generate(long seed) {
        VectorField generated = generationModel.generate(this, seed);

        return new VectorField(
                generated.grid,
                generated.WIDHT,
                generated.HEIGHT
        );
    }

    public static FieldGenerationModel RANDOM_GENERATION = (other, seed) -> {
        Random random = new Random(seed);

        Vector2D[] randomField = new Vector2D[other.getSize()];
        for (int i = 0; i < other.getSize(); i++) {
            randomField[i] = new Vector2D(random.nextDouble() * 2 - 1, random.nextDouble() * 2 - 1);
        }

        return new VectorField(randomField, other.getWidth(), other.getHeight());
    };
}