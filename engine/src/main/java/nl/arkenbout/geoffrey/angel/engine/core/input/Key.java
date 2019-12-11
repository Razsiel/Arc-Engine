package nl.arkenbout.geoffrey.angel.engine.core.input;

import java.util.HashMap;
import java.util.Map;

public enum Key {
    KEY_0(48), KEY_1(49), KEY_2(50), KEY_3(51), KEY_4(52),
    KEY_5(53), KEY_6(54), KEY_7(55), KEY_8(56), KEY_9(57),

    A(65), B(66), C(67), D(68), E(69), F(70),
    G(71), H(72), I(73), J(74), K(75), L(76),
    M(77), N(78), O(79), P(80), Q(81), R(82),
    S(83), T(84), U(85), V(86), W(87), X(88),
    Y(89), Z(90),

    UP(265),
    DOWN(264),
    LEFT(263),
    RIGHT(262),

    SPACE(32),
    LEFT_SHIFT(340),
    RIGHT_SHIFT(344),

    ESCAPE(256),
    TAB(258),

    KEY_UNKNOWN(-1);

    private static Map<Integer, Key> keyMap = new HashMap<>();
    static {
        for (Key key : values()) {
            keyMap.put(key.value, key);
        }
    }

    private final int value;

    Key(int value) {
        this.value = value;
    }

    static Key fromGlfwKeyCode(int glfwKeyCode) {
        if (!keyMap.containsKey(glfwKeyCode)) {
            return KEY_UNKNOWN;
        }
        return keyMap.get(glfwKeyCode);
    }
}
