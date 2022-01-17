package app.noiseviewerjfx.utilities.io.serialization;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * A Save is {@link Map} which associates {@link State States} to a string representing the variable they originated from.
 * This key is then used to retrieve the {@link State}
 */
public class Save implements Map<String, State> {

    private final HashMap<String, State> values = new HashMap<>();
    private final int VERSION;

    public Save(final int version) {
        this.VERSION = version;
    }

    public int version() {
        return VERSION;
    }

    @Override
    public int size() {
        return values.size();
    }

    @Override
    public boolean isEmpty() {
        return values.size() == 0;
    }

    @Override
    public boolean containsKey(Object key) {
        if (!(key instanceof String)) return false;
        return values.containsKey(key);
    }

    @Override
    public boolean containsValue(Object value) {
        return values.containsValue(value);
    }

    @Override
    public State get(Object key) {
        return values.get(key);
    }

    @Override
    public State put(String key, State value) {
        return values.put(key, value);
    }

    @Override
    public State remove(Object key) {
        return null;
    }

    @Override
    public void putAll(Map<? extends String, ? extends State> m) {
        values.putAll(m);
    }

    @Override
    @Deprecated
    public void clear() {

    }

    @Override
    public Set<String> keySet() {
        return values.keySet();
    }

    @Override
    @Deprecated
    public Collection<State> values() {
        return null;
    }

    @Override
    @Deprecated
    public Set<Entry<String, State>> entrySet() {
        return null;
    }

}
