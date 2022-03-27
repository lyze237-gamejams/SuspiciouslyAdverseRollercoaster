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

        var position = positionMapper.get(entityId);
        var hitbox = hitboxMapper.get(entityId);
        var rotation = rotationMapper.get(entityId);

        var verts = polygon.getVertices();
        var x = -hitbox.getWidth() / 2f;
        var y = 0;

        verts[0] = x;
        verts[1] = y;

        verts[2] = x + hitbox.getWidth();
        verts[3] = y;

        verts[4] = x + hitbox.getWidth();
        verts[5] = y + hitbox.getHeight();

        verts[6] = x;
        verts[7] = y + hitbox.getHeight();

        polygon.setVertices(verts);
        polygon.setPosition(position.getPosition().x, position.getPosition().y);
        polygon.setRotation(rotation.getRotation() * MathUtils.radiansToDegrees);
        drawer.polygon(polygon.getTransformedVertices(), 0.1f, JoinType.SMOOTH);

        drawer.setColor(Color.GREEN);
        drawer.filledCircle(position.getPosition().x, position.getPosition().y, 0.1f);
    }
}
