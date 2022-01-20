package app.noiseviewerjfx.utilities.generation.generationmodel;

import app.noiseviewerjfx.utilities.generation.Generated;

/**
 * Specifies how a class is {@link Generated generated}
 */
public interface GenerationModel {
    default Object generate(Generated other, long seed) {
        return null;
    }
}
