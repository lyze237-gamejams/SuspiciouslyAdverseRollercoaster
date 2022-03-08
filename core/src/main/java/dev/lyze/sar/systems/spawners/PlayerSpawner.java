package dev.lyze.sar.systems.spawners;

import com.artemis.World;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.math.Vector2;
import dev.lyze.sar.components.movement.*;
import dev.lyze.sar.components.player.PlayerComponent;
import dev.lyze.sar.components.player.PlayerFallStateComponent;
import dev.lyze.sar.components.player.TrackAccelerationDecelerationComponent;
import lombok.var;

public class PlayerSpawner extends Spawner {
    @Override
    public void spawn(World world, MapObject object) {
        var rectangleObject = ((RectangleMapObject) object);
        var rectangle = rectangleObject.getRectangle();

        world.edit(world.create())
                .add(new PlayerComponent())
                .add(new PositionComponent(new Vector2(rectangle.x, rectangle.y)))
                .add(new SizeComponent(128 * 2, 128 * 1.5f))
                .add(new GravityComponent())
                .add(new VelocityComponent())
                .add(new RotationComponent())
                .add(new TrackAccelerationDecelerationComponent())
                .add(new PlayerFallStateComponent());
    }

    @Override
    public String getName() {
        return "PlayerSpawner";
    }
}
