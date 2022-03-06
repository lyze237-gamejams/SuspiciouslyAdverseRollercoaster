package dev.lyze.sar;

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
    @Getter private final TiledMapTileLayer trackLayer;

    public Map(String path) {
        this.map = new TmxMapLoader().load(path);
        renderer = new OrthogonalTiledMapRendererBleeding(map, 1f);

        trackLayer = (TiledMapTileLayer) map.getLayers().get("Tracks");
        if (trackLayer == null)
            throw new IllegalArgumentException("Tracks Layer undefined");

        entitiesLayer = map.getLayers().get("Entities");
        if (entitiesLayer == null)
            throw new IllegalArgumentException("Entities Layer undefined");

        trackCollisionLayer = map.getLayers().get("TrackCollisions");
        if (trackCollisionLayer == null)
            throw new IllegalArgumentException("TrackCollisions Layer undefined");
    }
}
