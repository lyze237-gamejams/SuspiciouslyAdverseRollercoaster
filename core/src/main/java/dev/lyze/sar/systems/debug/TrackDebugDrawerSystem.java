package dev.lyze.sar.systems.debug;

import com.artemis.ComponentMapper;
import com.artemis.annotations.All;
import com.artemis.annotations.Wire;
import com.artemis.systems.IteratingSystem;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.utils.viewport.ExtendViewport;
import dev.lyze.sar.components.TrackComponent;
import dev.lyze.sar.utils.Constants;
import lombok.var;
import space.earlygrey.shapedrawer.JoinType;
import space.earlygrey.shapedrawer.ShapeDrawer;

@All({TrackComponent.class})
public class TrackDebugDrawerSystem extends IteratingSystem {
    @Wire(name = "constants") private Constants constants;
    @Wire private ShapeDrawer drawer;

    private ComponentMapper<TrackComponent> collisionMapper;

    @Override
    protected void process(int entityId) {
        if (!constants.isDebug())
            return;

        drawer.setColor(Color.WHITE);

        var track = collisionMapper.get(entityId);
        drawer.path(track.getLine().getTransformedVertices(), 0.05f, JoinType.POINTY, true);
    }
}
