package nl.arkenbout.geoffrey.angel.ecs;

public class Entity {

    private Integer id;
    private GameContext context;

    Entity(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setContext(GameContext context) {
        this.context = context;
    }

    public void addComponent(Component component) {
        this.context.getComponentRegistry().addComponent(this.id, component);
    }
}
