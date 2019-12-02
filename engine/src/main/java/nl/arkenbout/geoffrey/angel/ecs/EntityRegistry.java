package nl.arkenbout.geoffrey.angel.ecs;

import java.util.HashMap;
import java.util.Map;
import java.util.zip.CRC32;

public class EntityRegistry {

    private Map<Integer, Entity> entities = new HashMap<>();
    private int nextEntityId;

    public void addEntity(Entity entity) {
        entities.put(entity.getId(), entity);
    }

    public Integer getNextEntityId() {
        new CRC32().getValue();
        return nextEntityId++;
    }
}
