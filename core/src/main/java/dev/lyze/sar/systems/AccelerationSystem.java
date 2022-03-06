package dev.lyze.sar.systems;

import com.artemis.Aspect;
import com.artemis.ComponentMapper;
import com.artemis.EntitySubscription;
import com.artemis.annotations.All;
import com.artemis.systems.IteratingSystem;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.Vector2;
import dev.lyze.sar.components.*;
import dev.lyze.sar.gizmos.GizmoSystem;
import dev.lyze.sar.utils.IntersectionExtensions;
import lombok.var;

@All({PositionComponent.class, SizeComponent.class, AccelerationComponent.class})
public class AccelerationSystem extends IteratingSystem {
    private ComponentMapper<PositionComponent> positionMapper;
    private ComponentMapper<SizeComponent> sizeMapper;
    private ComponentMapper<AccelerationComponent> accelerationComponent;

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
        var acceleration = accelerationComponent.get(entityId);

        var center = position.getX() + size.getWidth() / 2f;
        var targetPosX = center + acceleration.getAcceleration() * world.getDelta();

        for (int i = 0; i < trackSubscription.getEntities().size(); i++) {
            var track = trackMapper.get(trackSubscription.getEntities().get(i));
            gizmos.setLineWidth(1);
            gizmos.setColor(Color.BLUE);
            gizmos.addLine(center + 1, position.getY(), targetPosX, position.getY());
            if (IntersectionExtensions.lineIntersectsLine(track.getStartX(), track.getStartY(), track.getEndX(), track.getEndY(), center + 1, position.getY(), targetPosX, position.getY(), 0.001f, intersection) != null) {
                gizmos.setColor(Color.YELLOW);
                gizmos.setLineWidth(1);
                gizmos.addCircle(intersection.x, intersection.y, 6);
                targetPosX = intersection.x;
                break;
            }
        }

        position.setX(targetPosX - size.getWidth() / 2f);
    }
}
