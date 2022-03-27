package dev.lyze.sar.systems.player;

import com.artemis.Aspect;
import com.artemis.ComponentMapper;
import com.artemis.EntitySubscription;
import com.artemis.annotations.All;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.Vector2;
import dev.lyze.sar.components.TrackComponent;
import dev.lyze.sar.components.movement.*;
import dev.lyze.sar.components.player.PlayerCartComponent;
import dev.lyze.sar.components.player.PlayerConstants;
import dev.lyze.sar.components.player.PlayerCartFallStateComponent;
import dev.lyze.sar.components.player.PlayerCartFollowTrackComponent;
import lombok.var;

@All({PlayerCartComponent.class, PlayerCartFallStateComponent.class})
public class PlayerCartFallStateSystem extends PlayerAbstractSystem {
    private final Vector2 intersection = new Vector2();

    private ComponentMapper<TrackComponent> trackMapper;
    private EntitySubscription tracks;

    @Override
    protected void initialize() {
        tracks = world.getAspectSubscriptionManager().get(Aspect.all(TrackComponent.class));
    }

    @Override
    protected void process(int entityId, PlayerCartComponent player, PlayerConstants playerConstants, PositionComponent position, SizeComponent size, RotationComponent rotation, VelocityComponent velocity, GravityComponent gravity) {
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
                        .remove(PlayerCartFallStateComponent.class)
                        .add(new PlayerCartFollowTrackComponent(tracks.getEntities().get(i), j));

                return true;
            }
        }
        return false;
    }
}
