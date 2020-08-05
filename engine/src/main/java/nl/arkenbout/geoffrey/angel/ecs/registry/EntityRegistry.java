package nl.arkenbout.geoffrey.angel.ecs.registry;

import nl.arkenbout.geoffrey.angel.ecs.Entity;
import org.hashids.Hashids;

import java.util.*;

public class EntityRegistry {

    private Map<String, Entity> entities = new HashMap<>();
    private static final Hashids HASHIDS = new Hashids(Long.toString(System.currentTimeMillis(), Character.MAX_RADIX), 8);

    public EntityRegistry() {
    }

    public Entity addEntity(Entity entity) {
        var put = entities.put(entity.getId(), entity);
        return entity;
    }

    public Collection<Entity> getEntities() {
        return Collections.unmodifiableCollection(this.entities.values());
    }

    public String getNextEntityId() {
        String nextId;
        do {
            nextId = HASHIDS.encode(System.nanoTime());
        } while (entities.containsKey(nextId));
        return nextId;
    }
}
