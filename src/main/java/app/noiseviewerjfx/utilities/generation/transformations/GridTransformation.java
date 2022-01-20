package app.noiseviewerjfx.utilities.generation.transformations;

import app.noiseviewerjfx.utilities.generation.Grid;
import app.noiseviewerjfx.utilities.generation.VectorField;

/**
 * A type of {@link Transformation trasnformation} which can be applied to a {@link Grid grid}
 */
public interface GridTransformation extends Transformation {
    VectorField transform(Grid target);
}
