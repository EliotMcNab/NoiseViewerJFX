package app.noiseviewerjfx.utilities.io.serialization;

/**
 * A state is characterised by a value-version pair
 * States can be generated from {@link app.noiseviewerjfx.utilities.controller.valueControllers.Evolving Evolving} classes
 * and represent previous values contained within these classes
 * When generated, States are generally stored within a {@link Save}
 * States can be restored by classes which implement the {@link Loadable} interface
 */
public class State {

    private final int VERSION;
    private final Object VALUE;

    public State(final int version, final Object value) {
        this.VERSION = version;
        this.VALUE = value;
    }

    public Object getVALUE() {
        return VALUE;
    }
}
