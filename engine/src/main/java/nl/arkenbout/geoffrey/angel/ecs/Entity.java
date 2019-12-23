package nl.arkenbout.geoffrey.angel.ecs;

public class Entity {

    private String id;
    private Context context;

    Entity(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }

    public void setContext(Context context) {
        this.context = context;
    }

    public void addComponent(Component component) {
        this.context.getComponentRegistry()
                .addComponent(this.id, component);
    }
}
