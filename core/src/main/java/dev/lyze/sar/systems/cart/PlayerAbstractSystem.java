package dev.lyze.sar.systems.cart;

import com.artemis.ComponentMapper;
import com.artemis.systems.IteratingSystem;
import dev.lyze.sar.components.movement.*;
import dev.lyze.sar.components.cart.CartComponent;
import dev.lyze.sar.components.cart.CartConstants;
import dev.lyze.sar.systems.debug.GizmoSystem;
import lombok.var;

public abstract class PlayerAbstractSystem extends IteratingSystem {
    protected ComponentMapper<CartComponent> playerMapper;
    protected ComponentMapper<PositionComponent> positionMapper;
    protected ComponentMapper<RotationComponent> rotationMapper;
    protected ComponentMapper<VelocityComponent> velocityMapper;
    protected ComponentMapper<GravityComponent> gravityMapper;

    protected GizmoSystem gizmos;

    @Override
    protected void process(int entityId) {
        var player = playerMapper.get(entityId);
        var position = positionMapper.get(entityId);
        var rotation = rotationMapper.get(entityId);
        var velocity = velocityMapper.get(entityId);
        var gravity = gravityMapper.get(entityId);

        process(entityId, player, player.cartConstants, position, rotation, velocity, gravity);
    }

    protected abstract void process(int entityId, CartComponent player, CartConstants cartConstants, PositionComponent position, RotationComponent rotation, VelocityComponent velocity, GravityComponent gravity);
}
