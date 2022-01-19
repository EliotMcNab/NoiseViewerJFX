package app.noiseviewerjfx.utilities.generation.transformations;

import app.noiseviewerjfx.utilities.generation.Grid;
import app.noiseviewerjfx.utilities.generation.VectorField;

public interface GridTransform extends Transform {
    VectorField transform(Grid target);
}
