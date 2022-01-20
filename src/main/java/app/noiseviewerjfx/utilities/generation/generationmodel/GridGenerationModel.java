package app.noiseviewerjfx.utilities.generation.generationmodel;

import app.noiseviewerjfx.utilities.generation.Grid;

/**
 * A GridGenerationModel specifies how the values contained inside a
 * {@link Grid} are generated
 */
public interface GridGenerationModel extends GenerationModel {
    Grid generate(Grid other, long seed);
}
