package app.noiseviewerjfx.utilities.generation.effects;

import app.noiseviewerjfx.utilities.generation.Plane;
import app.noiseviewerjfx.utilities.generation.VectorField;

/**
 * A type of {@link Effect} which is applied to {@link VectorField vector fields}
 */
public interface FieldEffect extends Effect {
    VectorField applyTo(Plane target);
}
