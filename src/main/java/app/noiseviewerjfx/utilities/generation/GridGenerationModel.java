package app.noiseviewerjfx.utilities.generation;

public interface GridGenerationModel extends GenerationModel {
    Grid generate(Grid other, long seed);
}
