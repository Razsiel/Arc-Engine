package nl.arkenbout.geoffrey.angel.ecs.system;

import nl.arkenbout.geoffrey.angel.ecs.ComponentRegistry;
import nl.arkenbout.geoffrey.angel.ecs.match.ComponentMatch;
import nl.arkenbout.geoffrey.angel.ecs.match.ComponentMatcher;

import java.util.List;

public abstract class ComponentSystem {
    protected final ComponentMatcher componentMatcher;
    protected ComponentRegistry componentRegistry;

    public ComponentSystem() {
        this.componentMatcher = new ComponentMatcher();
    }

    protected ComponentSystem(ComponentMatcher componentMatcher) {
        this.componentMatcher = componentMatcher;
    }

    protected abstract void doEachComponent(ComponentMatch match);

    void setComponentRegistry(ComponentRegistry componentRegistry) {
        this.componentRegistry = componentRegistry;
    }

    public void update() {
        List<ComponentMatch> components = getComponents(componentMatcher);
        for (ComponentMatch match : components) {
            doEachComponent(match);
        }
    }

    protected final List<ComponentMatch> getComponents(ComponentMatcher matcher) {
        return componentRegistry.getComponents(matcher);
    }

    public void cleanup() {
    }
}

