package nl.arkenbout.geoffrey.arc.ecs;

import java.util.List;

public abstract class System<T extends Component> {

    private GameContext context;

    public abstract void update();

    void setContext(GameContext context) {
        this.context = context;
    }

    protected final List<T> getComponents(Class<T> type) {
        return context.getComponents(type);
    }

    public void cleanup() {}
}
