package nl.arkenbout.geoffrey.angel.engine.core.input.keyboard;

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

    F1(290), F2(291), F3(292), F4(293), F5(294),
    F6(295), F7(296), F8(297), F9(298), F10(299),
    F11(300), F12(301), F13(302), F14(303), F15(304),
    F16(305), F17(306), F18(307), F19(308), F20(309),
    F21(310), F22(311), F23(312), F24(313), F25(314),

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
