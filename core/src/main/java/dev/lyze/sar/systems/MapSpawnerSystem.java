package dev.lyze.sar.systems;

import com.artemis.BaseSystem;
import com.artemis.ComponentMapper;
import com.artemis.annotations.All;
import com.artemis.annotations.Wire;
import com.badlogic.gdx.maps.MapLayer;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.objects.PolylineMapObject;
import com.badlogic.gdx.math.Polyline;
import dev.lyze.sar.Map;
import dev.lyze.sar.components.TrackComponent;
import dev.lyze.sar.components.EntitySpawnerComponent;
import lombok.var;

public class MapSpawnerSystem extends BaseSystem {
    @Wire private Map map;

    private void parseEntities(MapLayer entitiesLayer) {
        for (MapObject object : entitiesLayer.getObjects()) {
            world.edit(world.create())
                    .add(new EntitySpawnerComponent(object));
        }
    }

    private void parseTrackCollisions(MapLayer layer) {
        for (int i = 0; i < layer.getObjects().getCount(); i++) {
            MapObject mapObject = layer.getObjects().get(i);

            var lineCollisionObject = (PolylineMapObject) mapObject;
            var verts = lineCollisionObject.getPolyline().getTransformedVertices();

            for (int j = 0; j < verts.length; j++)
                verts[j] /= map.getTrackLayer().getTileWidth();

            world.edit(world.create())
                    .add(new TrackComponent(new Polyline(verts)));
        }
    }

    @Override
    protected void initialize() {
        var entitiesLayer = map.getEntitiesLayer();

        parseTrackCollisions(map.getTrackCollisionLayer());
        parseEntities(entitiesLayer);
    }

    @Override
    protected void processSystem() {

    }
}
