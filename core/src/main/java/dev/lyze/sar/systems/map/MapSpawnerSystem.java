package dev.lyze.sar.systems.map;

import com.artemis.BaseSystem;
import com.artemis.annotations.Wire;
import com.badlogic.gdx.maps.MapLayer;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.objects.PolygonMapObject;
import com.badlogic.gdx.maps.objects.PolylineMapObject;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.math.Polygon;
import com.badlogic.gdx.math.Polyline;
import dev.lyze.sar.components.ObstacleComponent;
import dev.lyze.sar.components.TrackComponent;
import dev.lyze.sar.components.EntitySpawnerComponent;
import lombok.var;
import java.util.Arrays;

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

    private void parseObstaclesLayer(TiledMapTileLayer obstacles) {
        for (int x = 0; x < obstacles.getWidth(); x++) {
            for (int y = 0; y < obstacles.getHeight(); y++) {
                var cell = obstacles.getCell(x, y);
                if (cell == null)
                    continue;

                var objects = cell.getTile().getObjects();
                if (objects.getCount() != 1)
                    throw new IllegalArgumentException("Spikes cell " + x + "/" + y + " is wrong count: " + objects.getCount() + " != " + 1);

                var polygonMapObject= ((PolygonMapObject) objects.get(0));
                var verts= Arrays.copyOf(polygonMapObject.getPolygon().getTransformedVertices(), polygonMapObject.getPolygon().getTransformedVertices().length);

                for (int j = 0; j < verts.length; j++) {
                    verts[j] /= map.getTrackLayer().getTileWidth();
                }

                var polygon = new Polygon(verts);
                polygon.setPosition(x, y);

                world.edit(world.create())
                        .add(new ObstacleComponent(polygon.getTransformedVertices()));
            }
        }
    }

    @Override
    protected void initialize() {
        var entitiesLayer = map.getEntitiesLayer();

        parseTrackCollisions(map.getTrackCollisionLayer());
        parseObstaclesLayer(map.getObstaclesLayer());
        parseEntities(entitiesLayer);
    }

    @Override
    protected void processSystem() {

    }
}
