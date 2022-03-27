package dev.lyze.sar.systems.map;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.maps.MapLayer;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import dev.lyze.sar.utils.OrthogonalTiledMapRendererBleeding;
import lombok.Getter;

public class Map {
    @Getter private final TiledMap map;
    @Getter private final OrthogonalTiledMapRendererBleeding renderer;

    @Getter private final MapLayer entitiesLayer, trackCollisionLayer;
    @Getter private final TiledMapTileLayer trackLayer, obstaclesLayer, torchesLayer;

    public Map(String path, SpriteBatch batch) {
        this.map = new TmxMapLoader().load(path);

        trackLayer = (TiledMapTileLayer) map.getLayers().get("Tracks");
        if (trackLayer == null)
            throw new IllegalArgumentException("Tracks Layer undefined");

        obstaclesLayer = (TiledMapTileLayer) map.getLayers().get("Obstacles");
        if (obstaclesLayer == null)
            throw new IllegalArgumentException("Obstacles Layer undefined");

        torchesLayer = (TiledMapTileLayer) map.getLayers().get("Torches");
        if (torchesLayer == null)
            throw new IllegalArgumentException("Torches Layer undefined");

        entitiesLayer = map.getLayers().get("Entities");
        if (entitiesLayer == null)
            throw new IllegalArgumentException("Entities Layer undefined");

        trackCollisionLayer = map.getLayers().get("TrackCollisions");
        if (trackCollisionLayer == null)
            throw new IllegalArgumentException("TrackCollisions Layer undefined");

        renderer = new OrthogonalTiledMapRendererBleeding(map, 1f / trackLayer.getTileWidth(), batch);
    }

    public void dispose() {
        map.dispose();
    }
}
