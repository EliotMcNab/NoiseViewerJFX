package app.noiseviewerjfx.utilities.generation;

import app.noiseviewerjfx.utilities.generation.generationmodel.FieldGenerationModel;
import app.noiseviewerjfx.utilities.generation.generationmodel.GenerationModel;
import app.noiseviewerjfx.utilities.generation.generationmodel.GridGenerationModel;

/**
 * Maks classes which can be generated using a {@link GenerationModel generatiion model}
 * <br>
 * <i>see {@link FieldGenerationModel} and {@link GridGenerationModel} for more specific examples</i>
 */
public interface Generated {
    int getWidth();

    int getHeight();

    int getSize();

    Generated generate(long seed);

    Generated setGenerationModel(GenerationModel generationModel);

    GenerationModel getGenerationModel();
}
