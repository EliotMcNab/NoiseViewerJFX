package app.noiseviewerjfx.utilities.generation.effects;

import app.noiseviewerjfx.utilities.generation.Plane;

public interface Effect {
    default Plane applyTo(Plane target) {
        return null;
    }
}
