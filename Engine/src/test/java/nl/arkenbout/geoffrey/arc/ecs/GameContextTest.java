package nl.arkenbout.geoffrey.arc.ecs;

import nl.arkenbout.geoffrey.arc.ecs.test.*;
import org.junit.jupiter.api.*;

import static org.hamcrest.MatcherAssert.*;
import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.*;

public class GameContextTest {

    private GameContext context;

    @BeforeEach
    void init() {
        context = GameContext.getInstance();
    }

    //---------------------------------------------------------
    //  registerSystem Tests
    //---------------------------------------------------------

    @Test
    void registerSystem_toEmptySet_returnsSystemAdded() {
        var systemToAdd = new TestComponentSystem();

        var added = context.registerSystem(systemToAdd);

        assertNotNull(added);
        assertTrue(context.getComponentSystems().contains(systemToAdd));
    }

    @Test
    void registerSystem_alreadyAdded_returnsAlreadyExistingSystem() {
        var systemToAdd = new TestComponentSystem();
        var addedBefore = context.registerSystem(systemToAdd);
        assertNotNull(addedBefore);

        var system = context.registerSystem(systemToAdd);
        assertEquals(addedBefore, system);
    }

    @Test
    void registerSystemGeneric_toEmptySet_returnsTrue() {
        var systemToAdd = TestComponentSystem.class;

        TestComponentSystem added = null;
        try {
            added = context.registerSystem(systemToAdd);
        } catch (Exception e) {
            fail("failed");
        }

        assertNotNull(added);
        assertEquals(1, context.getComponentSystems().size());
    }

    @Test
    void registerSystemGeneric_withPrivateConstructor_throwsIllegalAccessException() {
        var systemToAdd = PrivateConstructorTestComponentSystem.class;

        assertThrows(IllegalAccessException.class, () -> context.registerSystem(systemToAdd));
    }

    @Test
    void registerSystemGeneric_withoutDefaultConstructor_throwsNoSuchMethodException() {
        var systemToAdd = NoDefaultConstructorTestComponentSystem.class;

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

        assertNotNull(added);
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

    @Test
    void getComponents_withSingleComponentClassInComponentMatcher_returnsComponentOfType() {
        context.addComponent(0, new TestComponent());
        context.addComponent(0, new AnotherTestComponent());
        context.addComponent(0, new YetAnotherTestComponent());

        var matcher = new ComponentMatcher(TestComponent.class);

        var matches = context.getComponents(matcher);

        assertThat(matches, hasSize(1));
        for (var match : matches) {
            var component = match.getComponent(TestComponent.class);
            assertNotNull(component);
        }
    }

    @Test
    void getComponents_withMultipleComponentClassInComponentMatcher_returnsComponentOfType() {
        context.addComponent(0, new TestComponent());
        context.addComponent(0, new AnotherTestComponent());
        context.addComponent(0, new YetAnotherTestComponent());

        var matcher = new ComponentMatcher(TestComponent.class, AnotherTestComponent.class);

        var matches = context.getComponents(matcher);

        assertThat(matches, hasSize(1));
        var match = matches.get(0);
        assertNotNull(match);
        assertThat(match.getMatchedComponents(), hasSize(2));
        assertNotNull(match.getComponent(TestComponent.class));
        assertNotNull(match.getComponent(AnotherTestComponent.class));
    }
}
