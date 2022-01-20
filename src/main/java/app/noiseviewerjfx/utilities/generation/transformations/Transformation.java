package app.noiseviewerjfx.utilities.generation.transformations;

import app.noiseviewerjfx.utilities.generation.Plane;

/**
 * A transformation from one type of {@link Plane plane} to another
 */
public interface Transformation {
    default Plane transform(Plane target) {
        return null;
    }
}
