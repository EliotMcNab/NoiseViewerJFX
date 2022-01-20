package app.noiseviewerjfx.utilities.generation.transformations;

import app.noiseviewerjfx.utilities.generation.Grid;
import app.noiseviewerjfx.utilities.generation.VectorField;

/**
 * A type of {@link Transformation transformation} which can be applied to {@link VectorField vector fields}
 */
public interface FieldTransformation extends Transformation {
    Grid transform(VectorField target);
}
