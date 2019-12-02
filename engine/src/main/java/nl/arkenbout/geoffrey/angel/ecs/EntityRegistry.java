package nl.arkenbout.geoffrey.angel.ecs;

import org.hashids.Hashids;

import java.nio.ByteBuffer;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;
import java.util.zip.CRC32;

public class EntityRegistry {

    private Map<String, Entity> entities = new HashMap<>();
    private final Hashids hashids;

    public EntityRegistry() {
        hashids = new Hashids(Long.toString(System.currentTimeMillis(), Character.MAX_RADIX), 8);
    }

    public void addEntity(Entity entity) {
        entities.put(entity.getId(), entity);
    }

    public String getNextEntityId() {
        return hashids.encode(System.currentTimeMillis());
    }
}
