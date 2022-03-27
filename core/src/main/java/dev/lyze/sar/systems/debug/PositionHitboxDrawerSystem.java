package dev.lyze.sar.systems.debug;

import com.artemis.ComponentMapper;
import com.artemis.annotations.All;
import com.artemis.annotations.Wire;
import com.artemis.systems.IteratingSystem;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Polygon;
import dev.lyze.sar.components.HitboxComponent;
import dev.lyze.sar.components.movement.PositionComponent;
import dev.lyze.sar.components.movement.RotationComponent;
import dev.lyze.sar.utils.Constants;
import dev.lyze.sar.utils.RectangleUtils;
import lombok.var;
import space.earlygrey.shapedrawer.JoinType;
import space.earlygrey.shapedrawer.ShapeDrawer;

@All({PositionComponent.class, RotationComponent.class, HitboxComponent.class})
public class PositionHitboxDrawerSystem extends IteratingSystem {
    private final Polygon polygon = new Polygon(new float[8]);

    @Wire private ShapeDrawer drawer;
    @Wire(name = "constants") private Constants constants;

    private ComponentMapper<PositionComponent> positionMapper;
    private ComponentMapper<RotationComponent> rotationMapper;
    private ComponentMapper<HitboxComponent> hitboxMapper;

    @Override
    protected void process(int entityId) {
        if (!constants.isDebug())
            return;

        drawer.setColor(Color.TEAL);

        var position = positionMapper.get(entityId).getPosition();
        var hitbox = hitboxMapper.get(entityId);
        var rotation = rotationMapper.get(entityId).getRotation();

        RectangleUtils.toPolygon(position.x, position.y, hitbox.getWidth(), hitbox.getHeight(), rotation, polygon);

        drawer.polygon(polygon.getTransformedVertices(), 0.1f, JoinType.SMOOTH);

        drawer.setColor(Color.GREEN);
        drawer.filledCircle(position.x, position.y, 0.1f);
    }
}
