package dev.lyze.sar.systems.cart;

import com.artemis.ComponentMapper;
import com.artemis.annotations.All;
import com.artemis.annotations.Wire;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import dev.lyze.sar.components.TrackComponent;
import dev.lyze.sar.components.movement.*;
import dev.lyze.sar.components.cart.CartComponent;
import dev.lyze.sar.components.cart.CartConstants;
import dev.lyze.sar.components.cart.CartFallStateComponent;
import dev.lyze.sar.components.cart.CartFollowTrackComponent;
import dev.lyze.sar.utils.Constants;
import lombok.var;

@All({CartComponent.class, CartFollowTrackComponent.class})
public class PlayerCartFollowTrackSystem extends PlayerAbstractSystem {
    private final Vector2 direction = new Vector2();

    @Wire(name = "constants") private Constants constants;

    private ComponentMapper<TrackComponent> trackMapper;
    private ComponentMapper<CartFollowTrackComponent> playerFollowTrackMapper;

    private float currentSpeed = 10f;

    @Override
    protected void process(int entityId, CartComponent player, CartConstants cartConstants, PositionComponent position, RotationComponent rotation, VelocityComponent velocity, GravityComponent gravity) {
        var follow = playerFollowTrackMapper.get(entityId);
        var trackToFollow = trackMapper.get(follow.getTrackId());

        if (Gdx.input.isKeyPressed(Input.Keys.SPACE)) {
            velocity.getVelocity().set(velocity.getVelocity().x + cartConstants.jumpVelocity.x, cartConstants.jumpVelocity.y);
            velocity.clamp();
            position.getPosition().mulAdd(velocity.getVelocity(), world.getDelta());

            constants.getJumpCartSound().play(0.6f);

            world.edit(entityId)
                    .remove(CartFollowTrackComponent.class)
                    .add(new CartFallStateComponent());

            return;
        }

        var verts = trackToFollow.getLine().getTransformedVertices();

        var startPosX = position.getPosition().x;
        var startPosY = position.getPosition().y;
        var targetPosX = verts[follow.getSection() + 2];
        var targetPosY = verts[follow.getSection() + 3];

        calculateDirection(position.getPosition(), targetPosX, targetPosY);

        direction.scl(currentSpeed);

        velocity.getVelocity().set(direction);
        velocity.clamp();

        position.getPosition().mulAdd(velocity.getVelocity(), world.getDelta());

        var angle = MathUtils.atan2(direction.y, direction.x);
        var angleDeg = angle * MathUtils.radiansToDegrees;
        rotation.setRotation(angle);

        if (angle < 0) {
            var multiplier = MathUtils.map(-90, 0, 0.2f, 0, angleDeg);
            var scale = cartConstants.trackAcceleration * multiplier;
            currentSpeed += scale * world.getDelta();
            velocity.getVelocity().nor().scl(currentSpeed);
        }
        if (angle > 0) {
            var multiplier = MathUtils.map(0, 90, 0, 0.2f, angleDeg);
            var scale = cartConstants.trackDeceleration * multiplier;
            currentSpeed += scale * world.getDelta();
            currentSpeed = Math.max(currentSpeed, cartConstants.trackMinSpeed);
            velocity.getVelocity().nor().scl(currentSpeed);
        }

        var distance = Vector2.dst(startPosX, startPosY, targetPosX, targetPosY);
        if (Vector2.dst(startPosX, startPosY, position.getPosition().x, position.getPosition().y) >= distance) {
            position.getPosition().set(targetPosX, targetPosY);

            follow.setSection(follow.getSection() + 2);

            if (follow.getSection() >= verts.length - 2) {
                position.getPosition().add(1, 0);

                world.edit(entityId)
                    .remove(CartFollowTrackComponent.class)
                    .add(new CartFallStateComponent());
            }
        }
    }

    private void calculateDirection(Vector2 position, float targetPosX, float targetPosY) {
        direction.set(targetPosX, targetPosY);
        direction.sub(position);
        direction.nor();

        gizmos.setColor(Color.TEAL);
        gizmos.setLineWidth(0.05f);
        gizmos.addLine(position.x, position.y, targetPosX, targetPosY);

        gizmos.setColor(Color.ORANGE);
        gizmos.setLineWidth(0.1f);
        gizmos.addDirection(position.x, position.y, direction.x, direction.y, 0.5f);
    }
}
