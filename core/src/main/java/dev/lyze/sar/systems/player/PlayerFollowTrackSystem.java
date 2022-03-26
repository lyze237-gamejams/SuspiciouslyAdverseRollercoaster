package dev.lyze.sar.systems.player;

import com.artemis.ComponentMapper;
import com.artemis.annotations.All;
import com.artemis.systems.IteratingSystem;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import dev.lyze.sar.components.TrackComponent;
import dev.lyze.sar.components.movement.*;
import dev.lyze.sar.components.player.PlayerComponent;
import dev.lyze.sar.components.player.PlayerConstants;
import dev.lyze.sar.components.player.PlayerFallStateComponent;
import dev.lyze.sar.components.player.PlayerFollowTrackComponent;
import dev.lyze.sar.systems.debug.GizmoSystem;
import lombok.var;

@All({PlayerComponent.class, PlayerFollowTrackComponent.class})
public class PlayerFollowTrackSystem extends PlayerAbstractSystem {
    private final Vector2 intersection = new Vector2();
    private final Vector2 direction = new Vector2();

    private ComponentMapper<TrackComponent> trackMapper;
    private ComponentMapper<PlayerFollowTrackComponent> playerFollowTrackMapper;

    @Override
    protected void process(int entityId, PlayerComponent player, PlayerConstants playerConstants, PositionComponent position, SizeComponent size, RotationComponent rotation, VelocityComponent velocity, GravityComponent gravity) {
        var follow = playerFollowTrackMapper.get(entityId);
        var trackToFollow = trackMapper.get(follow.getTrackId());

        if (Gdx.input.isKeyJustPressed(Input.Keys.SPACE)) {
            velocity.getVelocity().set(playerConstants.jumpVelocity);
            position.getPosition().mulAdd(velocity.getVelocity(), world.getDelta());

            world.edit(entityId)
                    .remove(PlayerFollowTrackComponent.class)
                    .add(new PlayerFallStateComponent());

            return;
        }

        var verts = trackToFollow.getLine().getTransformedVertices();

        var startPosX = position.getPosition().x;
        var startPosY = position.getPosition().y;
        var targetPosX = verts[follow.getSection() + 2];
        var targetPosY = verts[follow.getSection() + 3];

        calculateDirection(position.getPosition(), targetPosX, targetPosY);

        velocity.clamp(playerConstants.maxVelocity);
        var speed = velocity.getVelocity().len();
        direction.scl(speed);

        velocity.getVelocity().set(direction);
        velocity.clamp(playerConstants.maxVelocity);

        position.getPosition().mulAdd(velocity.getVelocity(), world.getDelta());

        var angle = MathUtils.atan2(direction.y, direction.x);
        var angleDeg = angle * MathUtils.radiansToDegrees;
        rotation.setRotation(angle);

        if (angle < 0) {
            var multiplier = MathUtils.map(-90, 0, 0.2f, 0, angleDeg);
            var scale = playerConstants.trackAcceleration * multiplier;
            var length = velocity.getVelocity().len();
            velocity.getVelocity().nor().scl(length + scale * world.getDelta());
        }
        if (angle > 0) {
            var multiplier = MathUtils.map(0, 90, 0, 0.2f, angleDeg);
            var scale = playerConstants.trackDeceleration * multiplier;
            var length = velocity.getVelocity().len();
            velocity.getVelocity().nor().scl(length + scale * world.getDelta());

            if (velocity.getVelocity().len() < playerConstants.trackMinSpeed) {
                velocity.getVelocity().nor().scl(playerConstants.trackMinSpeed);
            }
        }

        var distance = Vector2.dst(startPosX, startPosY, targetPosX, targetPosY);
        if (Vector2.dst(startPosX, startPosY, position.getPosition().x, position.getPosition().y) >= distance) {
            position.getPosition().set(targetPosX, targetPosY);

            follow.setSection(follow.getSection() + 2);

            if (follow.getSection() >= verts.length - 2) {
                System.out.println("Done!~");

                position.getPosition().add(1, 0);

                world.edit(entityId)
                    .remove(PlayerFollowTrackComponent.class)
                    .add(new PlayerFallStateComponent());
            }
        }
    }

    private void calculateDirection(Vector2 position, float targetPosX, float targetPosY) {
        direction.set(targetPosX, targetPosY);
        direction.sub(position);
        direction.nor();

        gizmos.setLineWidth(0.01f);
        gizmos.addDirection(position.x, position.y, direction.x, direction.y, 0.5f);
    }
}
