package nl.arkenbout.geoffrey.angel.ecs;

import nl.arkenbout.geoffrey.angel.ecs.context.GlobalContext;
import nl.arkenbout.geoffrey.angel.ecs.test.PrivateConstructorTestComponentSystem;
import nl.arkenbout.geoffrey.angel.ecs.test.TestComponentSystem;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class GlobalContextTest {

    private GlobalContext context;

    @BeforeEach
    void init() {
        context = GlobalContext.getInstance();
    }

    @AfterEach
    void after() {
        context.cleanup();
        context = null;
     }

    //---------------------------------------------------------
    //  registerSystem Tests
    //---------------------------------------------------------

    @Test
    void registerSystem_toEmptySet_returnsSystemAdded() {
        var systemToAdd = new TestComponentSystem();

        var added = context.registerSystem(systemToAdd);

        assertNotNull(added);
        assertTrue(context.getComponentSystemRegistery().getComponentSystems().contains(systemToAdd));
    }

    @Test
    void registerSystem_alreadyAdded_returnsAlreadyExistingSystem() {
        var systemBefore = new TestComponentSystem();
        var addedBefore = context.registerSystem(systemBefore);
        assertNotNull(addedBefore);

        var newInstanceOfAlreadyExistingSystem = new TestComponentSystem();
        var system = context.registerSystem(newInstanceOfAlreadyExistingSystem);
        assertEquals(addedBefore, system);
    }

    @Test
    void registerSystemGeneric_toEmptySet_returnsTrue() {
        var systemToAdd = TestComponentSystem.class;

        assertDoesNotThrow(() -> {
            var added = context.registerSystem(systemToAdd);
            assertNotNull(added);
            assertEquals(1, context.getComponentSystemRegistery().getComponentSystems().size());
        });
    }

    @Test
    void registerSystemGeneric_withPrivateConstructor_throwsIllegalAccessException() {
        var systemToAdd = PrivateConstructorTestComponentSystem.class;

        assertThrows(IllegalArgumentException.class, () -> context.registerSystem(systemToAdd));
    }
}
