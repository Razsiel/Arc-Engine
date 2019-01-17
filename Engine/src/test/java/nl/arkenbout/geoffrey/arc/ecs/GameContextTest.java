package nl.arkenbout.geoffrey.arc.ecs;

import nl.arkenbout.geoffrey.arc.ecs.test.*;
import org.junit.jupiter.api.*;
import org.mockito.Mock;

import java.util.Objects;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class GameContextTest {

    private GameContext context;

    @BeforeEach
    void init() {
        context = new GameContext();
    }

    //---------------------------------------------------------
    //  registerSystem Tests
    //---------------------------------------------------------

    @Test
    void registerSystem_toEmptySet_returnsSystemAdded() {
        var systemToAdd = new TestSystem();

        var added = context.registerSystem(systemToAdd);

        assertNotNull(added);
        assertTrue(context.getSystems().contains(systemToAdd));
    }

    @Test
    void registerSystem_alreadyAdded_returnsAlreadyExistingSystem() {
        var systemToAdd = new TestSystem();
        var addedBefore = context.registerSystem(systemToAdd);
        assertNotNull(addedBefore);

        var system = context.registerSystem(systemToAdd);
        assertEquals(addedBefore, system);
    }

    @Test
    void registerSystemGeneric_toEmptySet_returnsTrue() {
        var systemToAdd = TestSystem.class;

        TestSystem added = null;
        try {
            added = context.registerSystem(systemToAdd);
        } catch (Exception e) {
            fail("failed");
        }

        assertNotNull(added);
        assertEquals(1, context.getSystems().size());
    }

    @Test
    void registerSystemGeneric_withPrivateConstructor_throwsIllegalAccessException() {
        var systemToAdd = PrivateConstructorTestSystem.class;

        assertThrows(IllegalAccessException.class, () -> context.registerSystem(systemToAdd));
    }

    @Test
    void registerSystemGeneric_withoutDefaultConstructor_throwsNoSuchMethodException() {
        var systemToAdd = NoDefaultConstructorTestSystem.class;

        assertThrows(NoSuchMethodException.class, () -> context.registerSystem(systemToAdd));
    }


    //---------------------------------------------------------
    //  addComponent Tests
    //---------------------------------------------------------

    @Test
    void addComponent_toEmptySet_returnsTrue() {
        var entityId = 0;
        var componentToAdd = new TestComponent();

        var added = context.addComponent(entityId, componentToAdd);

        assertTrue(added);
        assertTrue(context.getComponents(entityId).contains(componentToAdd));
    }

    @Test
    void getComponents_returnsListOfComponentsOfType() {
        context.addComponent(0, new TestComponent());
        context.addComponent(0, new AnotherTestComponent());
        context.addComponent(1, new AnotherTestComponent());
        context.addComponent(1, new TestComponent());
        context.addComponent(6, new TestComponent());

        var components = context.getComponents(TestComponent.class);

        assertEquals(3, components.size());
    }
}
