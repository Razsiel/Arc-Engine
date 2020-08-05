package nl.arkenbout.geoffrey.angel.ecs.context;

import nl.arkenbout.geoffrey.angel.ecs.BaseContext;
import nl.arkenbout.geoffrey.angel.ecs.system.ComponentSystem;

import java.util.Set;

public class GlobalContext extends BaseContext {
    private static GlobalContext instance;

    private Context activeContext;

    private GlobalContext() {
        super("GLOBAL");
    }

    public static GlobalContext getInstance() {
        if (instance == null) {
            instance = new GlobalContext();
        }
        return instance;
    }

    public Context getActiveContext() {
        return this.activeContext;
    }

    public void setActiveContext(Context sceneContext) {
        if (activeContext != null) {
            activeContext.unload();
        }
        sceneContext.load();
        this.activeContext = sceneContext;
    }

    public void cleanup() {
        instance = null;
        super.cleanup();
    }

    @Override
    public void update(Set<ComponentSystem> ignored) {
        this.componentSystemRegistery.getComponentSystems()
                .forEach(componentSystem -> componentSystem.update(this.entityRegistry.getEntities()));
    }
}
