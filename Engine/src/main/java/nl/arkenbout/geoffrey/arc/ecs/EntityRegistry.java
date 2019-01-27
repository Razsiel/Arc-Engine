package nl.arkenbout.geoffrey.arc.ecs;

import java.util.HashMap;
import java.util.Map;

public class EntityRegistry {

    private Map<Integer, Entity> entities = new HashMap<>();
    private int nextEntityId;

    public void addEntity(Entity entity) {
        entities.put(entity.getId(), entity);
    }

    public Integer getNextEntityId() {
        return nextEntityId++;
    }
}
