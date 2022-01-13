package app.noiseviewerjfx.utilities.io.input;

import javafx.event.EventHandler;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;

import java.util.HashSet;
import java.util.Set;

public abstract class Keyboard implements EventHandler<KeyEvent> {

    private final Set<KeyCode> activeKeys   = new HashSet<>();
    private final Set<KeyCode> priorKeys    = new HashSet<>();

    @Override
    public void handle(KeyEvent event) {
        // if the key is pressed...
        if (KeyEvent.KEY_PRESSED.equals(event.getEventType())) {
            flushPriorKeys();
            activeKeys.add(event.getCode());
            onKeyPressed(event);
        }

        // if the key is released...
        if (KeyEvent.KEY_RELEASED.equals(event.getEventType())) {
            activeKeys.remove(event.getCode());
            priorKeys.add(event.getCode());
            onKeyReleased(event);
        }
    }

    public boolean isKeyPressed(KeyCode keyCode) {
        return activeKeys.contains(keyCode);
    }

    public boolean areAllKeysPressed(KeyCode... keyCodes) {
        for (KeyCode keyCode : keyCodes) {
            if (!isKeyPressed(keyCode)) return false;
        }

        return true;
    }

    public boolean isKeyReleased(KeyCode keyCode) {
        return priorKeys.contains(keyCode);
    }

    private void flushPriorKeys() {
        priorKeys.clear();
    }

    protected abstract void onKeyPressed(KeyEvent keyEvent);

    protected abstract void onKeyReleased(KeyEvent keyEvent);
}
