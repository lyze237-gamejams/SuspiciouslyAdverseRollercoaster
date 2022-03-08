package dev.lyze.sar.systems.debug;

import com.artemis.ComponentMapper;
import com.artemis.annotations.All;
import com.artemis.annotations.Wire;
import com.artemis.systems.IteratingSystem;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.utils.viewport.ExtendViewport;
import dev.lyze.sar.components.TrackComponent;
import lombok.var;
import space.earlygrey.shapedrawer.JoinType;
import space.earlygrey.shapedrawer.ShapeDrawer;

@All({TrackComponent.class})
public class TrackDebugDrawerSystem extends IteratingSystem {
    @Wire private ExtendViewport viewport;
    @Wire private ShapeDrawer drawer;

    private ComponentMapper<TrackComponent> collisionMapper;

    @Override
    protected void begin() {
        drawer.getBatch().setProjectionMatrix(viewport.getCamera().combined);
        drawer.getBatch().begin();
    }

    @Override
    protected void process(int entityId) {
        drawer.setColor(Color.WHITE);

        var track = collisionMapper.get(entityId);
        drawer.path(track.getLine().getTransformedVertices(), 0.05f, JoinType.POINTY, true);
    }

    @Override
    protected void end() {
        drawer.getBatch().end();
    }
}
