package nl.arkenbout.geoffrey.angel.engine.util;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;

class MathUtilsTest {

    private static Stream<Arguments> remap_param() {
        return Stream.of(
                Arguments.of(10, 0, 10, 0, 100, 100),
                Arguments.of(-1, -1, 1, 0, 1, 0)
        );
    }

    @ParameterizedTest
    @MethodSource("remap_param")
    void remap(float v, float min, float max, float newMin, float newMax, float expected) {
        float value = MathUtils.remap(v, min, max, newMin, newMax);
        assertEquals(expected, value);
    }
}