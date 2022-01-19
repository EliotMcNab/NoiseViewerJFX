package app.noiseviewerjfx.utilities.generation.transformations;

import app.noiseviewerjfx.utilities.generation.Plane;

public interface Transform {
    default Plane transform(Plane target) {
        return null;
    }
}
