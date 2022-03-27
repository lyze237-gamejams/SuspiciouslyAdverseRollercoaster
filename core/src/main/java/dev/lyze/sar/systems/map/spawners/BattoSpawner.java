package dev.lyze.sar.systems.map.spawners;

import com.aliasifkhan.hackLights.HackLight;
import com.aliasifkhan.hackLights.HackLightEngine;
import com.artemis.World;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.math.Polygon;
import dev.lyze.sar.components.*;
import dev.lyze.sar.components.batto.BattoWaitTillPlayerIsInScreenStateComponent;
import dev.lyze.sar.components.movement.PositionComponent;
import dev.lyze.sar.systems.map.Map;
import dev.lyze.sar.systems.sprites.SpriteOrder;
import dev.lyze.sar.utils.Constants;
import dev.lyze.sar.utils.RectangleUtils;
import lombok.var;

public class BattoSpawner extends Spawner {
    @Override
    public void spawn(Constants constants, World world, Map map, MapObject object) {
        var rectangleObject = ((RectangleMapObject) object);
        var rectangle = rectangleObject.getRectangle();

        var batId = world.create();
        world.edit(batId)
                .add(new BattoComponent())
                .add(new ObstacleComponent(RectangleUtils.toPolygon(0, 1f, 0.4f, 0.4f, 0, new Polygon(new float[8])).getTransformedVertices()))
                .add(new BattoWaitTillPlayerIsInScreenStateComponent())
                .add(new PositionComponent(rectangle.x / map.getTrackLayer().getTileWidth(), rectangle.y / map.getTrackLayer().getTileHeight()))
                .add(new AnimatableSpriteComponent(constants.getBattoFlight().getKeyFrame(0), SpriteOrder.INFRONT_PLAYER));

        var properties = object.getProperties();
        var lightColor = properties.get("lightColor", Color.class);
        var lightScale = properties.get("lightScale", float.class);
        var lightRegion = properties.get("lightRegion", String.class);

        var light = new HackLight(constants.getLights().findRegion(lightRegion), lightColor.r, lightColor.g, lightColor.b, lightColor.a, lightScale);
        world.getInjector().getRegistered(HackLightEngine.class).addLight(light);
        world.edit(world.create())
                .add(new HackLightComponent(light))
                .add(new PositionComponent(0, 0))
                .add(new CopyPositionFromEntityComponent(batId));
    }

    @Override
    public String getName() {
        return "Batto";
    }
}
