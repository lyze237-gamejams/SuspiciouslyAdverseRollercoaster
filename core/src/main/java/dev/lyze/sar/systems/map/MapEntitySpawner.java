package dev.lyze.sar.systems.map;

import com.artemis.ComponentMapper;
import com.artemis.annotations.All;
import com.artemis.annotations.Wire;
import com.artemis.systems.IteratingSystem;
import com.badlogic.gdx.utils.Array;
import dev.lyze.sar.components.EntitySpawnerComponent;
import dev.lyze.sar.systems.map.spawners.PlayerSpawner;
import dev.lyze.sar.systems.map.spawners.Spawner;
import dev.lyze.sar.utils.Constants;
import lombok.var;

@All(EntitySpawnerComponent.class)
public class MapEntitySpawner extends IteratingSystem {
    private final Array<Spawner> spawners = new Array<>();

    private ComponentMapper<EntitySpawnerComponent> entitySpawnerMapper;

    @Wire(name = "constants") private Constants constants;
    @Wire private Map map;

    public MapEntitySpawner() {
        spawners.add(new PlayerSpawner());
    }

    @Override
    protected void inserted(int entityId) {
        var entity = entitySpawnerMapper.get(entityId);
        var type = entity.getObject().getProperties().get("type");

        for (Spawner spawner : spawners) {
            if (spawner.getName().equals(type)) {
                spawner.spawn(constants, world, map, entity.getObject());
            }
        }
    }

    @Override
    protected void process(int entityId) {

    }
}
