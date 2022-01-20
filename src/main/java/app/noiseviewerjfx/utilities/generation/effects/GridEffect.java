package app.noiseviewerjfx.utilities.generation.effects;

import app.noiseviewerjfx.utilities.generation.Grid;

/**
 * A type of {@link Effect effect} which is applied to {@link Grid grids}
 */
public interface GridEffect extends Effect {
    Grid applyTo(Grid target);
}
