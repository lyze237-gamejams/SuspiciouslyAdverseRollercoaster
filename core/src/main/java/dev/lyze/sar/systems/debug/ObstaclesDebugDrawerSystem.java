package dev.lyze.sar.systems.debug;

import com.artemis.ComponentMapper;
import com.artemis.annotations.All;
import com.artemis.annotations.Wire;
import com.artemis.systems.IteratingSystem;
import com.badlogic.gdx.graphics.Color;
import dev.lyze.sar.components.ObstacleComponent;
import dev.lyze.sar.utils.Constants;
import lombok.var;
import space.earlygrey.shapedrawer.JoinType;
import space.earlygrey.shapedrawer.ShapeDrawer;

@All({ObstacleComponent.class})
public class ObstaclesDebugDrawerSystem extends IteratingSystem {
    @Wire(name = "constants") private Constants constants;
    @Wire private ShapeDrawer drawer;

    private ComponentMapper<ObstacleComponent> obstaclesMapper;

    @Override
    protected void process(int entityId) {
        if (!constants.isDebug())
            return;

        drawer.setColor(Color.RED);

        var obstacle = obstaclesMapper.get(entityId);
        drawer.polygon(obstacle.getPolygon(), 0.01f);
    }
}
