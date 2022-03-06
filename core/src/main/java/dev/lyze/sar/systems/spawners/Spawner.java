package dev.lyze.sar.systems.spawners;

import com.artemis.World;
import com.badlogic.gdx.maps.MapObject;

public abstract class Spawner {
    public abstract void spawn(World world, MapObject object);

    public abstract String getName();
}
