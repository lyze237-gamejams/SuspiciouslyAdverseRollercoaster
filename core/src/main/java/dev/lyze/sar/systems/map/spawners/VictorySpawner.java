package dev.lyze.sar.systems.map.spawners;

import com.artemis.World;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.math.Rectangle;
import dev.lyze.sar.systems.map.Map;
import dev.lyze.sar.utils.Constants;
import lombok.var;

public class VictorySpawner extends Spawner {
    @Override
    public void spawn(Constants constants, World world, Map map, MapObject object) {
        var tile = map.getTrackLayer().getTileWidth();

        var rectangleMapObject = (RectangleMapObject) object;
        var bigRectangle = rectangleMapObject.getRectangle();

        var rectangle = new Rectangle(bigRectangle.x / tile, bigRectangle.y / tile, bigRectangle.width / tile, bigRectangle.height / tile);

        map.getVictoryRectangle().set(rectangle);
    }

    @Override
    public String getName() {
        return "Victory";
    }
}
