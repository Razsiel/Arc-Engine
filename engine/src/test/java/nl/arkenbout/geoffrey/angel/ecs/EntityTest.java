package nl.arkenbout.geoffrey.angel.ecs;

import nl.arkenbout.geoffrey.angel.ecs.test.AnotherTestComponent;
import nl.arkenbout.geoffrey.angel.ecs.test.TestComponent;
import nl.arkenbout.geoffrey.angel.ecs.test.YetAnotherTestComponent;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ArgumentsSource;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.params.provider.Arguments.arguments;

class EntityTest {

    @Test
    void givenComponentsThatShouldExist_whenHasComponents_thenReturnTrue() {
        var testComponent = new TestComponent();
        var anotherTestComponent = new AnotherTestComponent();
        var yetAnotherTestComponent = new YetAnotherTestComponent();
        var components = List.of(testComponent, anotherTestComponent, yetAnotherTestComponent);
        var entity = new Entity("1", components);

        List<Class<? extends Component>> matcher = List.of(TestComponent.class, AnotherTestComponent.class);
        var result = entity.hasComponents(matcher);

        assertTrue(result);
    }

    @Test
    void givenExistingEntity_whenAddComponent_thenHasComponentTrue() {
        var testComponent = new TestComponent();
        var entity = new Entity("1");

        entity.addComponent(testComponent);
        var hasComponent = entity.hasComponent(TestComponent.class);

        assertTrue(hasComponent);
    }

    @Test
    public void hasComponents_performanceTest() {
        
    }
}