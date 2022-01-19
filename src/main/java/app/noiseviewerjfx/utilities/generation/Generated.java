package app.noiseviewerjfx.utilities.generation;

public interface Generated {
    int getWidth();

    int getHeight();

    int getSize();

    Generated generate(long seed);

    void setGenerationModel(GenerationModel generationModel);

    GenerationModel getGenerationModel();
}
