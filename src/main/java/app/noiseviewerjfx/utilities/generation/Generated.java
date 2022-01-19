package app.noiseviewerjfx.utilities.generation;

public interface Generated {
    int getWidth();

    int getHeight();

    int getSize();

    void generate(long seed);

    void setGenerationModel(GenerationModel generationModel);

    GenerationModel getGenerationModel();
}
