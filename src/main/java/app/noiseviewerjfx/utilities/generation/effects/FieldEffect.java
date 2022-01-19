package app.noiseviewerjfx.utilities.generation.effects;

import app.noiseviewerjfx.utilities.generation.Plane;
import app.noiseviewerjfx.utilities.generation.VectorField;

public interface FieldEffect extends Effect {
    VectorField applyTo(Plane target);
}
