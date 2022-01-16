package app.noiseviewerjfx.utilities.io.serialization;

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
