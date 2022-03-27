package dev.lyze.sar.systems.spawners;

import com.artemis.World;
import com.badlogic.gdx.maps.MapObject;
import dev.lyze.sar.Map;
import dev.lyze.sar.utils.Constants;

public abstract class Spawner {
    public abstract void spawn(Constants constants, World world, Map map, MapObject object);

    public abstract String getName();
}
