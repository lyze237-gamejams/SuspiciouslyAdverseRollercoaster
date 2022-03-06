package dev.lyze.sar.systems;

import com.artemis.ComponentMapper;
import com.artemis.annotations.All;
import com.artemis.annotations.Wire;
import com.artemis.systems.IteratingSystem;
import com.badlogic.gdx.maps.MapLayer;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.objects.PolygonMapObject;
import com.badlogic.gdx.maps.objects.PolylineMapObject;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.math.Polygon;
import com.badlogic.gdx.math.Polyline;
import dev.lyze.sar.Map;
import dev.lyze.sar.components.TrackComponent;
import dev.lyze.sar.components.EntitySpawnerComponent;
import dev.lyze.sar.components.MapComponent;
import dev.lyze.sar.components.MapSpawnerComponent;
import lombok.var;

@All({MapSpawnerComponent.class})
public class MapSpawnerSystem extends IteratingSystem {
    private ComponentMapper<MapSpawnerComponent> mapMapper;

    @Wire private Map map;

    @Override
    protected void inserted(int entityId) {
        var entitiesLayer = map.getEntitiesLayer();

        parseTrackCollisions(map.getTrackCollisionLayer());
        parseEntities(entitiesLayer);

        world.edit(entityId)
                .remove(MapSpawnerComponent.class)
                .add(new MapComponent(map));
    }

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
            float[] verts = lineCollisionObject.getPolyline().getTransformedVertices();

            world.edit(world.create())
                    .add(new TrackComponent(new Polyline(verts)));
        }
    }

    @Override
    protected void process(int entityId) {

    }
}
