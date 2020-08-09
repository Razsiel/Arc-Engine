package nl.arkenbout.geoffrey.angel.engine.core.input.keyboard;

import java.util.*;
import java.util.stream.Collectors;

public enum KeyModifier {
    SHIFT(1),
    CONTROL(2),
    ALT(4),
    SUPER(8),
    CAPS_LOCK(16),
    NUM_LOCK(32),

    NONE(0);

    private static Map<Integer, List<KeyModifier>> keyModifierMap = new HashMap<>();

    static {
        keyModifierMap.put(0, Collections.singletonList(NONE));
        for (int i = 1; i <= 32; i++) {
            List<KeyModifier> keyModifiersForI = fromBitMask(i);
            keyModifierMap.put(i, keyModifiersForI);
        }
    }

    private final int value;

    KeyModifier(int value) {
        this.value = value;
    }

    private static List<KeyModifier> fromBitMask(int bitMask) {
        if (bitMask == NUM_LOCK.value)
            return Collections.singletonList(NUM_LOCK);
        else if (bitMask == CAPS_LOCK.value)
            return Collections.singletonList(CAPS_LOCK);
        else if (bitMask == SUPER.value)
            return Collections.singletonList(SUPER);
        else if (bitMask == ALT.value)
            return Collections.singletonList(ALT);
        else if (bitMask == CONTROL.value)
            return Collections.singletonList(CONTROL);
        else if (bitMask == SHIFT.value)
            return Collections.singletonList(SHIFT);
        else
            return Arrays.stream(values())
                    .filter(value -> value.value < bitMask)
                    .collect(Collectors.toList());
    }

    public static List<KeyModifier> fromGlfwModifierCode(int glfwModifierCode) {
        if (!keyModifierMap.containsKey(glfwModifierCode)) {
            throw new IllegalArgumentException("Modifier code \'" + glfwModifierCode + "\' does not map to any modifier combinations");
        }
        return keyModifierMap.get(glfwModifierCode);
    }
}
