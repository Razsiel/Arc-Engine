package nl.arkenbout.geoffrey.arc.ecs;

import java.util.List;

public abstract class ComponentSystem<C extends Component> {

    private GameContext context;

    public abstract void update();

    void setContext(GameContext context) {
        this.context = context;
    }

    protected final List<C> getComponents(Class<C> type) {
        return context.getComponents(type);
    }

    protected final List<ComponentMatch> getComponents(ComponentMatcher matcher) {
        return context.getComponents(matcher);
    }

    public void cleanup() {}
}
