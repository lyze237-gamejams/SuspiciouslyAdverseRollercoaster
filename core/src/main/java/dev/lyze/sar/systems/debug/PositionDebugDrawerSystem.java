package dev.lyze.sar.systems.debug;

import com.artemis.ComponentMapper;
import com.artemis.annotations.All;
import com.artemis.annotations.Wire;
import com.artemis.systems.IteratingSystem;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Polygon;
import dev.lyze.sar.components.movement.PositionComponent;
import dev.lyze.sar.components.movement.RotationComponent;
import dev.lyze.sar.utils.Constants;
import lombok.var;
import space.earlygrey.shapedrawer.JoinType;
import space.earlygrey.shapedrawer.ShapeDrawer;

@All({PositionComponent.class})
public class PositionDebugDrawerSystem extends IteratingSystem {
    @Wire private ShapeDrawer drawer;
    @Wire(name = "constants") private Constants constants;

    private ComponentMapper<PositionComponent> positionMapper;

    @Override
    protected void process(int entityId) {
        if (!constants.isDebug())
            return;

        var position = positionMapper.get(entityId).getPosition();
        drawer.filledCircle(position.x, position.y, 0.1f, Color.TEAL);
    }
}
