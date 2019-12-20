package nl.arkenbout.geoffrey.angel.engine.component;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class TransformComponentTest {

    @Test
    void setParent_recursively_should_throwIllegalArgumentException() {
        var a = new TransformComponent();
        var b = new TransformComponent();
        var c = new TransformComponent();
        b.setParent(a);
        c.setParent(b);

        assertThrows(IllegalArgumentException.class, () -> a.setParent(c) );
    }
}