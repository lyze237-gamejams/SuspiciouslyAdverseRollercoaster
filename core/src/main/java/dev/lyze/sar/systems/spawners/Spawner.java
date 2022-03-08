package dev.lyze.sar.systems.spawners;

import com.artemis.World;
import com.badlogic.gdx.maps.MapObject;
import dev.lyze.sar.Map;

public abstract class Spawner {
    public abstract void spawn(World world, Map map, MapObject object);

    public abstract String getName();
}
