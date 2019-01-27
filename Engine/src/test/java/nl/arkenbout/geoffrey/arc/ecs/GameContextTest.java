package nl.arkenbout.geoffrey.arc.ecs;

import nl.arkenbout.geoffrey.arc.ecs.test.*;
import org.junit.jupiter.api.*;

import static org.hamcrest.MatcherAssert.*;
import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.*;

public class GameContextTest {

    private GameContext context;
    private ComponentSystemRegistry systemRegistry;
    private ComponentRegistry componentRegistry;

    @BeforeEach
    void init() {
        context = GameContext.getInstance();
        systemRegistry = context.getComponentSystemRegistery();
        componentRegistry = context.getComponentRegistry();
    }

    //---------------------------------------------------------
    //  registerSystem Tests
    //---------------------------------------------------------

    @Test
    void registerSystem_toEmptySet_returnsSystemAdded() {
        var systemToAdd = new TestComponentSystem();

        var added = systemRegistry.registerSystem(systemToAdd);

        assertNotNull(added);
        assertTrue(systemRegistry.getComponentSystems().contains(systemToAdd));
    }

    @Test
    void registerSystem_alreadyAdded_returnsAlreadyExistingSystem() {
        var systemToAdd = new TestComponentSystem();
        var addedBefore = systemRegistry.registerSystem(systemToAdd);
        assertNotNull(addedBefore);

        var system = systemRegistry.registerSystem(systemToAdd);
        assertEquals(addedBefore, system);
    }

    @Test
    void registerSystemGeneric_toEmptySet_returnsTrue() {
        var systemToAdd = TestComponentSystem.class;

        TestComponentSystem added = null;
        try {
            added = systemRegistry.registerSystem(systemToAdd);
        } catch (Exception e) {
            fail("failed");
        }

        assertNotNull(added);
        assertEquals(1, systemRegistry.getComponentSystems().size());
    }

    @Test
    void registerSystemGeneric_withPrivateConstructor_throwsIllegalAccessException() {
        var systemToAdd = PrivateConstructorTestComponentSystem.class;

        assertThrows(IllegalAccessException.class, () -> systemRegistry.registerSystem(systemToAdd));
    }

    @Test
    void registerSystemGeneric_withoutDefaultConstructor_throwsNoSuchMethodException() {
        var systemToAdd = NoDefaultConstructorTestComponentSystem.class;

        assertThrows(NoSuchMethodException.class, () -> systemRegistry.registerSystem(systemToAdd));
    }


    //---------------------------------------------------------
    //  addComponent Tests
    //---------------------------------------------------------

    @Test
    void addComponent_toEmptySet_returnsTrue() {
        var entityId = 0;
        var componentToAdd = new TestComponent();

        var added = componentRegistry.addComponent(entityId, componentToAdd);

        assertNotNull(added);
        assertTrue(componentRegistry.getComponents(entityId).contains(componentToAdd));
    }

    @Test
    void getComponents_returnsListOfComponentsOfType() {
        componentRegistry.addComponent(0, new TestComponent());
        componentRegistry.addComponent(0, new AnotherTestComponent());
        componentRegistry.addComponent(1, new AnotherTestComponent());
        componentRegistry.addComponent(1, new TestComponent());
        componentRegistry.addComponent(6, new TestComponent());

        var components = componentRegistry.getComponents(TestComponent.class);

        assertEquals(3, components.size());
    }

    @Test
    void getComponents_withSingleComponentClassInComponentMatcher_returnsComponentOfType() {
        componentRegistry.addComponent(0, new TestComponent());
        componentRegistry.addComponent(0, new AnotherTestComponent());
        componentRegistry.addComponent(0, new YetAnotherTestComponent());

        var matcher = new ComponentMatcher(TestComponent.class);

        var matches = componentRegistry.getComponents(matcher);

        assertThat(matches, hasSize(1));
        for (var match : matches) {
            var component = match.getComponent(TestComponent.class);
            assertNotNull(component);
        }
    }

    @Test
    void getComponents_withMultipleComponentClassInComponentMatcher_returnsComponentOfType() {
        componentRegistry.addComponent(0, new TestComponent());
        componentRegistry.addComponent(0, new AnotherTestComponent());
        componentRegistry.addComponent(0, new YetAnotherTestComponent());

        var matcher = new ComponentMatcher(TestComponent.class, AnotherTestComponent.class);

        var matches = componentRegistry.getComponents(matcher);

        assertThat(matches, hasSize(1));
        var match = matches.get(0);
        assertNotNull(match);
        assertThat(match.getMatchedComponents(), hasSize(2));
        assertNotNull(match.getComponent(TestComponent.class));
        assertNotNull(match.getComponent(AnotherTestComponent.class));
    }
}
