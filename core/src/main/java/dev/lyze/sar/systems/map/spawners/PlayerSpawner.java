package dev.lyze.sar.systems.map.spawners;

import com.aliasifkhan.hackLights.HackLight;
import com.aliasifkhan.hackLights.HackLightEngine;
import com.artemis.World;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import dev.lyze.sar.AnimatableSprite;
import dev.lyze.sar.components.*;
import dev.lyze.sar.components.player.PlayerOrCartComponent;
import dev.lyze.sar.systems.map.Map;
import dev.lyze.sar.components.movement.*;
import dev.lyze.sar.components.cart.CartComponent;
import dev.lyze.sar.components.cart.CartFallStateComponent;
import dev.lyze.sar.components.player.PlayerComponent;
import dev.lyze.sar.components.player.PlayerIdleComponent;
import dev.lyze.sar.systems.sprites.SpriteOrder;
import dev.lyze.sar.utils.Constants;
import lombok.var;

public class PlayerSpawner extends Spawner {
    @Override
    public void spawn(Constants constants, World world, Map map, MapObject object) {
        var rectangleObject = ((RectangleMapObject) object);
        var rectangle = rectangleObject.getRectangle();

        var cartId = world.create();
        // minecart
        world.edit(cartId)
                .add(new CartComponent())
                .add(new PlayerOrCartComponent())
                .add(new PositionComponent(rectangle.x / map.getTrackLayer().getTileWidth(), rectangle.y / map.getTrackLayer().getTileHeight()))
                .add(new GravityComponent())
                .add(new VelocityComponent())
                .add(new RotationComponent())
                .add(new CartFallStateComponent());

        // player
        world.edit(world.create())
                .add(new PlayerComponent())
                .add(new PlayerOrCartComponent())
                .add(new PositionComponent(0, 0))
                .add(new RotationComponent())
                .add(new PlayerIdleComponent())
                .add(new HitboxComponent(0.7f, 2.5f))
                .add(new CopyPositionFromEntityComponent(cartId))
                .add(new CopyRotationFromEntityComponent(cartId))
                .add(new AnimatableSpriteComponent(constants.getPlayerIdle().getKeyFrame(0), SpriteOrder.PLAYER));

        world.edit(world.create())
                .add(new PositionComponent(0, 0))
                .add(new RotationComponent())
                .add(new CopyPositionFromEntityComponent(cartId))
                .add(new CopyRotationFromEntityComponent(cartId))
                .add(new AnimatableSpriteComponent(constants.getMinecartBack(), SpriteOrder.BEHIND_PLAYER));

        world.edit(world.create())
                .add(new PositionComponent(0, 0))
                .add(new RotationComponent())
                .add(new CopyPositionFromEntityComponent(cartId))
                .add(new CopyRotationFromEntityComponent(cartId))
                .add(new AnimatableSpriteComponent(constants.getMinecartFront(), SpriteOrder.INFRONT_PLAYER));

        var properties = object.getProperties();
        var lightColor = properties.get("lightColor", Color.class);
        var lightScale = properties.get("lightScale", float.class);
        var lightRegion = properties.get("lightRegion", String.class);

        var light = new HackLight(constants.getLights().findRegion(lightRegion), lightColor.r, lightColor.g, lightColor.b, lightColor.a, lightScale);
        world.getInjector().getRegistered(HackLightEngine.class).addLight(light);
        world.edit(world.create())
                .add(new HackLightComponent(light))
                .add(new PositionComponent(0, 0))
                .add(new CopyPositionFromEntityComponent(cartId));
    }

    @Override
    public String getName() {
        return "Player";
    }
}
