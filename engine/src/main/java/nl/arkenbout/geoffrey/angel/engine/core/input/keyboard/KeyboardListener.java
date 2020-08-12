package nl.arkenbout.geoffrey.angel.engine.core.input.keyboard;

import java.util.List;
import java.util.Set;

public interface KeyboardListener {
    void onKeyDown(Key key, List<KeyModifier> modifiers);

    void onKeyUp(Key key, List<KeyModifier> modifiers);

    void update(Set<Key> keys, List<KeyModifier> modifiers);
}
