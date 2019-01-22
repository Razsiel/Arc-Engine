package nl.arkenbout.geoffrey.arc.ecs;

public class Entity {

    private Integer id;
    private GameContext context;

    public Entity(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setContext(GameContext context) {
        this.context = context;
    }

    public void addComponent(Component component) {
        this.context.addComponent(this.id, component);
    }
}
