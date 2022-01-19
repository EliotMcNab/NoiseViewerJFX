package app.noiseviewerjfx.utilities.generation.errors;

import app.noiseviewerjfx.utilities.generation.Plane;

public class GenerationError extends RuntimeException {
    public GenerationError(String errorMessage) {
        super(errorMessage);
    }

    public GenerationError(Plane plane) {
        super(String.format(
                "could not read values from plane %s because it has not been generated yet", plane
        ));
    }
}
