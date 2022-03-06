package dev.lyze.sar.systems;

import com.artemis.Aspect;
import com.artemis.ComponentMapper;
import com.artemis.EntitySubscription;
import com.artemis.annotations.All;
import com.artemis.annotations.Wire;
import com.artemis.systems.IteratingSystem;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.Vector2;
import dev.lyze.sar.components.TrackComponent;
import dev.lyze.sar.components.GravityComponent;
import dev.lyze.sar.components.PositionComponent;
import dev.lyze.sar.components.SizeComponent;
import dev.lyze.sar.gizmos.GizmoSystem;
import dev.lyze.sar.utils.IntersectionExtensions;
import lombok.var;

@All({PositionComponent.class, SizeComponent.class, GravityComponent.class})
public class GravitySystem extends IteratingSystem {
    private ComponentMapper<PositionComponent> positionMapper;
    private ComponentMapper<SizeComponent> sizeMapper;
    private ComponentMapper<GravityComponent> gravityMapper;

    private ComponentMapper<TrackComponent> trackMapper;
    private EntitySubscription trackSubscription;

    private final Vector2 intersection = new Vector2();

    private GizmoSystem gizmos;

    @Override
    protected void initialize() {
        trackSubscription = world.getAspectSubscriptionManager().get(Aspect.all(TrackComponent.class));
    }

    @Override
    protected void process(int entityId) {
        var position = positionMapper.get(entityId);
        var size = sizeMapper.get(entityId);
        var gravity = gravityMapper.get(entityId);

        var targetPosY = position.getY() - gravity.getGravity() * world.getDelta();

        var centerX = position.getX() + size.getWidth() / 2f;

        for (int i = 0; i < trackSubscription.getEntities().size(); i++) {
            var track = trackMapper.get(trackSubscription.getEntities().get(i));
            gizmos.setLineWidth(8);
            gizmos.setColor(Color.RED);
            gizmos.addLine(centerX, position.getY() + 12, centerX, targetPosY);
            if (IntersectionExtensions.lineIntersectsLine(track.getStartX(), track.getStartY(), track.getEndX(), track.getEndY(),centerX, position.getY() + 12, centerX, targetPosY, 0.001f, intersection) != null) {
                gizmos.setColor(Color.ORANGE);
                gizmos.setLineWidth(4);
                gizmos.addCircle(intersection.x, intersection.y, 6);
                targetPosY = intersection.y;
                break;
            }
        }

        position.setY(targetPosY);
    }
}
