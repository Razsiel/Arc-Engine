package nl.arkenbout.geoffrey.arc.ecs;

import java.util.List;

public abstract class ComponentSystem {

    private ComponentRegistry componentRegistry;

    public abstract void update();

    void setComponentRegistry(ComponentRegistry componentRegistry) {
        this.componentRegistry = componentRegistry;
    }

    protected final <C extends Component> List<C> getComponents(Class<C> type) {
        return componentRegistry.getComponents(type);
    }

    protected final List<ComponentMatch> getComponents(ComponentMatcher matcher) {
        return componentRegistry.getComponents(matcher);
    }

    public void cleanup() {}
}
