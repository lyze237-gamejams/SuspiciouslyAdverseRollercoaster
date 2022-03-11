package dev.lyze.sar.systems.player;

import com.artemis.Aspect;
import com.artemis.ComponentMapper;
import com.artemis.EntitySubscription;
import com.artemis.annotations.All;
import com.artemis.systems.IteratingSystem;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.Vector2;
import dev.lyze.sar.components.TrackComponent;
import dev.lyze.sar.components.movement.GravityComponent;
import dev.lyze.sar.components.movement.PositionComponent;
import dev.lyze.sar.components.movement.VelocityComponent;
import dev.lyze.sar.components.player.PlayerComponent;
import dev.lyze.sar.components.player.PlayerFallStateComponent;
import dev.lyze.sar.components.player.PlayerFollowTrackComponent;
import dev.lyze.sar.systems.debug.GizmoSystem;
import lombok.var;

@All({PlayerComponent.class, PlayerFallStateComponent.class, GravityComponent.class, PositionComponent.class, VelocityComponent.class})
public class PlayerFallStateSystem extends IteratingSystem {
    private final Vector2 intersection = new Vector2();

    private EntitySubscription tracks;
    private ComponentMapper<PlayerComponent> playerMapper;
    private ComponentMapper<TrackComponent> trackMapper;
    private ComponentMapper<PositionComponent> positionMapper;
    private ComponentMapper<GravityComponent> gravityMapper;
    private ComponentMapper<VelocityComponent> velocityMapper;

    private GizmoSystem gizmos;

    @Override
    protected void initialize() {
        tracks = world.getAspectSubscriptionManager().get(Aspect.all(TrackComponent.class));
    }

    @Override
    protected void process(int entityId) {
        var playerConstants = playerMapper.get(entityId).playerConstants;
        var position = positionMapper.get(entityId);
        var velocity = velocityMapper.get(entityId);
        var gravity = gravityMapper.get(entityId);

        velocity.getVelocity().add(0, gravity.getGravity() * world.getDelta());
        if (velocity.getVelocity().x > playerConstants.slowDownXToGravity) {
            velocity.getVelocity().x -= playerConstants.slowDownXGravitySpeed * world.getDelta();

            if (velocity.getVelocity().x < playerConstants.slowDownXToGravity)
                velocity.getVelocity().x = playerConstants.slowDownXToGravity;
        }
        velocity.clamp(playerConstants.maxVelocity);

        var targetXPosition = position.getPosition().x + velocity.getVelocity().x * world.getDelta();
        var targetYPosition = position.getPosition().y + velocity.getVelocity().y * world.getDelta();

        if (targetYPosition < position.getPosition().y && touchTrack(entityId, position, targetYPosition))
            return;

        position.getPosition().set(targetXPosition, targetYPosition);
    }

    private boolean touchTrack(int entityId, PositionComponent position, float targetYPosition) {
        for (int i = 0; i < tracks.getEntities().size(); i++) {
            var track = trackMapper.get(tracks.getEntities().get(i));

            gizmos.setLineWidth(0.1f);
            gizmos.setColor(Color.RED);
            var snapWhenCarIsDirectlyUnderTrack = 0.2f;
            gizmos.addLine(position.getPosition().x, position.getPosition().y + snapWhenCarIsDirectlyUnderTrack, position.getPosition().x, targetYPosition);

            var verts = track.getLine().getTransformedVertices();

            for (int j = 0; j < verts.length - 2; j += 2) {
                if (!Intersector.intersectSegments(verts[j], verts[j + 1], verts[j + 2], verts[j + 3], position.getPosition().x, position.getPosition().y + snapWhenCarIsDirectlyUnderTrack, position.getPosition().x, targetYPosition, intersection))
                    continue;

                position.getPosition().set(intersection.x, intersection.y);

                gizmos.setColor(Color.ORANGE);
                gizmos.addCircle(intersection.x, intersection.y, 0.1f);

                world.edit(entityId)
                        .remove(PlayerFallStateComponent.class)
                        .add(new PlayerFollowTrackComponent(tracks.getEntities().get(i), j));

                return true;
            }
        }
        return false;
    }
}
