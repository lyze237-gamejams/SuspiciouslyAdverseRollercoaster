package dev.lyze.sar.systems.map.spawners;

import com.artemis.World;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import dev.lyze.sar.components.AnimatableSpriteComponent;
import dev.lyze.sar.components.BattoComponent;
import dev.lyze.sar.components.movement.PositionComponent;
import dev.lyze.sar.systems.map.Map;
import dev.lyze.sar.systems.sprites.SpriteOrder;
import dev.lyze.sar.utils.Constants;
import lombok.var;

public class BattoSpawner extends Spawner {
    @Override
    public void spawn(Constants constants, World world, Map map, MapObject object) {
        var rectangleObject = ((RectangleMapObject) object);
        var rectangle = rectangleObject.getRectangle();

        world.edit(world.create())
                .add(new BattoComponent())
                .add(new PositionComponent(rectangle.x / map.getTrackLayer().getTileWidth(), rectangle.y / map.getTrackLayer().getTileHeight()))
                .add(new AnimatableSpriteComponent(constants.getBattoFlight().getKeyFrame(0), SpriteOrder.INFRONT_PLAYER));
    }

    @Override
    public String getName() {
        return "Batto";
    }
}
