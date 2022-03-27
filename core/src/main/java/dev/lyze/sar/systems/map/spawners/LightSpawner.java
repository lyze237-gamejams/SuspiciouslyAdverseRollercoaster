package dev.lyze.sar.systems.map.spawners;

import com.aliasifkhan.hackLights.HackLight;
import com.aliasifkhan.hackLights.HackLightEngine;
import com.artemis.World;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.MapProperties;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import dev.lyze.sar.components.*;
import dev.lyze.sar.components.cart.CartComponent;
import dev.lyze.sar.components.cart.CartFallStateComponent;
import dev.lyze.sar.components.movement.GravityComponent;
import dev.lyze.sar.components.movement.PositionComponent;
import dev.lyze.sar.components.movement.RotationComponent;
import dev.lyze.sar.components.movement.VelocityComponent;
import dev.lyze.sar.components.player.PlayerComponent;
import dev.lyze.sar.components.player.PlayerIdleComponent;
import dev.lyze.sar.components.player.PlayerOrCartComponent;
import dev.lyze.sar.systems.map.Map;
import dev.lyze.sar.systems.sprites.SpriteOrder;
import dev.lyze.sar.utils.Constants;
import lombok.var;

public class LightSpawner extends Spawner {
    @Override
    public void spawn(Constants constants, World world, Map map, MapObject object) {
        var engine = world.getInjector().getRegistered(HackLightEngine.class);

        var rectangleObject = ((RectangleMapObject) object);
        var rectangle = rectangleObject.getRectangle();
        var properties = object.getProperties();

        var color = properties.get("color", Color.class);
        var region = properties.get("region", String.class);
        var scale = properties.get("scale", float.class);

        var light = new HackLight(constants.getLights().findRegion(region), color.r, color.g, color.b, color.a, scale);
        light.setOriginBasedPosition(rectangle.x / map.getTrackLayer().getTileWidth(), rectangle.y / map.getTrackLayer().getTileHeight());
        engine.addLight(light);
    }

    @Override
    public String getName() {
        return "Light";
    }
}
