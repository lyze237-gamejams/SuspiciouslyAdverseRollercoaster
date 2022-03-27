package dev.lyze.sar.systems.spawners;

import com.artemis.World;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.math.Vector2;
import dev.lyze.sar.Map;
import dev.lyze.sar.components.CopyPositionFromEntityComponent;
import dev.lyze.sar.components.CopyRotationFromEntityComponent;
import dev.lyze.sar.components.SpriteComponent;
import dev.lyze.sar.components.movement.*;
import dev.lyze.sar.components.player.PlayerCartComponent;
import dev.lyze.sar.components.player.PlayerCartFallStateComponent;
import dev.lyze.sar.systems.sprites.SpriteOrder;
import dev.lyze.sar.utils.Constants;
import lombok.var;

public class PlayerSpawner extends Spawner {
    @Override
    public void spawn(Constants constants, World world, Map map, MapObject object) {
        var rectangleObject = ((RectangleMapObject) object);
        var rectangle = rectangleObject.getRectangle();

        var playerId = world.create();
        world.edit(playerId)
                .add(new PlayerCartComponent())
                .add(new PositionComponent(rectangle.x / map.getTrackLayer().getTileWidth(), rectangle.y / map.getTrackLayer().getTileHeight()))
                .add(new SizeComponent(2, 1.5f))
                .add(new GravityComponent())
                .add(new VelocityComponent())
                .add(new RotationComponent())
                .add(new PlayerCartFallStateComponent());

        world.edit(world.create())
                .add(new PositionComponent(0, 0))
                .add(new SizeComponent(2, 1.5f))
                .add(new RotationComponent())
                .add(new CopyPositionFromEntityComponent(playerId))
                .add(new CopyRotationFromEntityComponent(playerId))
                .add(new SpriteComponent(constants.getPlayerIdle().getKeyFrame(0), SpriteOrder.PLAYER));

        world.edit(world.create())
                .add(new PositionComponent(0, 0))
                .add(new SizeComponent(2, 1.5f))
                .add(new RotationComponent())
                .add(new CopyPositionFromEntityComponent(playerId))
                .add(new CopyRotationFromEntityComponent(playerId))
                .add(new SpriteComponent(constants.getMinecartBack(), SpriteOrder.BEHIND_PLAYER));

        world.edit(world.create())
                .add(new PositionComponent(0, 0))
                .add(new SizeComponent(2, 1.5f))
                .add(new RotationComponent())
                .add(new CopyPositionFromEntityComponent(playerId))
                .add(new CopyRotationFromEntityComponent(playerId))
                .add(new SpriteComponent(constants.getMinecartFront(), SpriteOrder.INFRONT_PLAYER));
    }

    @Override
    public String getName() {
        return "PlayerSpawner";
    }
}
