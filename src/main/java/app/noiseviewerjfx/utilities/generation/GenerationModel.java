package app.noiseviewerjfx.utilities.generation;

public interface GenerationModel {
    default Object generate(Generated other, long seed) {
        return null;
    }
}
