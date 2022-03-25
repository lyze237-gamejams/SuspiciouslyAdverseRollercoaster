package dev.lyze.sar.systems;

import com.artemis.ComponentMapper;
import com.artemis.annotations.All;
import com.artemis.annotations.Wire;
import com.artemis.systems.IteratingSystem;
import com.badlogic.gdx.utils.Array;
import dev.lyze.sar.Map;
import dev.lyze.sar.components.EntitySpawnerComponent;
import dev.lyze.sar.systems.spawners.PlayerSpawner;
import dev.lyze.sar.systems.spawners.Spawner;
import lombok.var;

@All(EntitySpawnerComponent.class)
public class MapEntitySpawner extends IteratingSystem {
    private ComponentMapper<EntitySpawnerComponent> entitySpawnerMapper;
    @Wire private Map map;

    private final Array<Spawner> spawners = new Array<>();

    public MapEntitySpawner() {
        spawners.add(new PlayerSpawner());
    }

    @Override
    protected void inserted(int entityId) {
        var entity = entitySpawnerMapper.get(entityId);
        var type = entity.getObject().getProperties().get("type");

        for (Spawner spawner : spawners) {
            if (spawner.getName().equals(type)) {
                spawner.spawn(world, map, entity.getObject());
            }
        }
    }

    @Override
    protected void process(int entityId) {

    }
}
