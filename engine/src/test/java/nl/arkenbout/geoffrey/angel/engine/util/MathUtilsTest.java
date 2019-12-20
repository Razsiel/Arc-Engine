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
                Arguments.of(-1, -1, 1, 0, 2, 0),
                Arguments.of(0, -1, 1, 0, 2, 1),
                Arguments.of(1, -1, 1, 0, 2, 2)
        );
    }

    @ParameterizedTest(name = "remapping {0} from range({1}, {2}) to range({3}, {4}) and expecting it to become {5}")
    @MethodSource("remap_param")
    void remap(float v, float min, float max, float newMin, float newMax, float expected) {
        float value = MathUtils.remap(v, min, max, newMin, newMax);
        assertEquals(expected, value);
    }
}