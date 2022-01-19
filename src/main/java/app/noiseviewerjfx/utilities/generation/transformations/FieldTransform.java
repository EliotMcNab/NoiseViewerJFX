package app.noiseviewerjfx.utilities.generation.transformations;

import app.noiseviewerjfx.utilities.generation.Grid;
import app.noiseviewerjfx.utilities.generation.VectorField;

public interface FieldTransform extends Transform {
    Grid transform(VectorField target);
}
