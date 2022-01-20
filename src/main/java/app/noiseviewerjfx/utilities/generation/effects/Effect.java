package app.noiseviewerjfx.utilities.generation.effects;

import app.noiseviewerjfx.utilities.generation.Plane;

/**
 * Effects are applied to {@link Plane Planes} to generate new planes based on the values they contain
 */
public interface Effect {
    default Plane applyTo(Plane target) {
        return null;
    }
}
