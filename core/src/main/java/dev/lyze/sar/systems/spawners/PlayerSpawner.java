package dev.lyze.sar.systems.spawners;

import com.artemis.World;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import dev.lyze.sar.components.*;
import lombok.var;

public class PlayerSpawner extends Spawner {
    @Override
    public void spawn(World world, MapObject object) {
        var rectangleObject = ((RectangleMapObject) object);
        var rectangle = rectangleObject.getRectangle();

        world.edit(world.create())
                .add(new PlayerComponent())
                .add(new PositionComponent(rectangle.x, rectangle.y))
                .add(new SizeComponent(128 * 2, 128 * 1.5f))
                .add(new GravityComponent())
                .add(new AccelerationComponent());
    }

    @Override
    public String getName() {
        return "PlayerSpawner";
    }
}
