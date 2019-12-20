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

        assertEquals(b.getParent(), a);
        assertEquals(c.getParent(), b);
        assertThrows(IllegalArgumentException.class, () -> a.setParent(c) );
    }

    @Test
    void setParent_toThis_should_throwIllegalArgumentException() {
        var a = new TransformComponent();
        assertThrows(IllegalArgumentException.class, () -> a.setParent(a) );
    }
}