package dev.lyze.sar.systems.batto;

import com.artemis.ComponentMapper;
import com.artemis.annotations.All;
import com.artemis.annotations.Wire;
import com.artemis.systems.IteratingSystem;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.utils.viewport.ExtendViewport;
import dev.lyze.sar.components.AnimatableSpriteComponent;
import dev.lyze.sar.components.BattoComponent;
import dev.lyze.sar.components.DrawLineToPlayerComponent;
import dev.lyze.sar.components.batto.BattoAttackActualHitComponent;
import dev.lyze.sar.components.batto.BattoIdleComponent;
import dev.lyze.sar.components.movement.PositionComponent;
import dev.lyze.sar.utils.Constants;
import lombok.var;

@All({BattoComponent.class, PositionComponent.class, AnimatableSpriteComponent.class, BattoIdleComponent.class})
public class BattoIdleSystem extends IteratingSystem {
    @Wire private ExtendViewport viewport;
    @Wire(name = "constants") private Constants constants;

    private ComponentMapper<PositionComponent> positionMapper;
    private ComponentMapper<AnimatableSpriteComponent> spriteMapper;

    @Override
    protected void process(int entityId) {
        var position = positionMapper.get(entityId).getPosition();
        var sprite = spriteMapper.get(entityId).getSprite();

        sprite.setAnimation(constants.getBattoFlight());

        if (!viewport.getCamera().frustum.pointInFrustum(position.x, position.y, 0))
            world.delete(entityId);
    }
}
