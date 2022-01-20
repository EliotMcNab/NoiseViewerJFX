package app.noiseviewerjfx.utilities.generation.generationmodel;

import app.noiseviewerjfx.utilities.generation.VectorField;

/**
 * A {@link GenerationModel generation model} used to specify how the {@link app.noiseviewerjfx.utilities.Vector2D vectors}
 * inside of a {@link VectorField vector field} are generated
 */
public interface FieldGenerationModel extends GenerationModel {
    VectorField generate(VectorField other, long seed);
}
