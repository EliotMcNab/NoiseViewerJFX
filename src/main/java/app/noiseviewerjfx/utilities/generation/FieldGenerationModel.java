package app.noiseviewerjfx.utilities.generation;

public interface FieldGenerationModel extends GenerationModel {
    VectorField generate(VectorField other, long seed);
}
