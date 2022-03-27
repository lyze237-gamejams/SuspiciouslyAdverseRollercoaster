package dev.lyze.sar.systems.spawners;

import com.artemis.World;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import dev.lyze.sar.Map;
import dev.lyze.sar.components.movement.*;
import dev.lyze.sar.components.player.PlayerCartComponent;
import dev.lyze.sar.components.player.PlayerCartFallStateComponent;
import lombok.var;

public class PlayerSpawner extends Spawner {
    @Override
    public void spawn(World world, Map map, MapObject object) {
        var rectangleObject = ((RectangleMapObject) object);
        var rectangle = rectangleObject.getRectangle();

        world.edit(world.create())
                .add(new PlayerCartComponent())
                .add(new PositionComponent(rectangle.x / map.getTrackLayer().getTileWidth(), rectangle.y / map.getTrackLayer().getTileHeight()))
                .add(new SizeComponent(2, 1.5f))
                .add(new GravityComponent())
                .add(new VelocityComponent())
                .add(new RotationComponent())
                .add(new PlayerCartFallStateComponent());
    }

    @Override
    public String getName() {
        return "PlayerSpawner";
    }
}
