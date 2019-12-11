package nl.arkenbout.geoffrey.angel.engine.core.input;

import java.util.List;

public interface KeyboardListener {
    void onKeyDown(Key key, List<KeyModifier> modifiers);
    void onKeyUp(Key key, List<KeyModifier> modifiers);
    void onKeys(List<Key> keys, List<KeyModifier> modifiers);
}
