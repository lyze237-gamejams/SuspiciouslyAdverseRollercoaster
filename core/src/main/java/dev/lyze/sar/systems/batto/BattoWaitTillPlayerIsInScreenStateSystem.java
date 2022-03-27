package dev.lyze.sar.systems.batto;

import com.artemis.ComponentMapper;
import com.artemis.annotations.All;
import com.artemis.annotations.Wire;
import com.artemis.systems.IteratingSystem;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.utils.viewport.ExtendViewport;
import dev.lyze.sar.components.AnimatableSpriteComponent;
import dev.lyze.sar.components.BattoComponent;
import dev.lyze.sar.components.DrawLineToPlayerComponent;
import dev.lyze.sar.components.batto.BattoAttackAnimationComponent;
import dev.lyze.sar.components.batto.BattoFlyFromRightToLeftComponent;
import dev.lyze.sar.components.batto.BattoWaitTillPlayerIsInScreenStateComponent;
import dev.lyze.sar.components.movement.PositionComponent;
import dev.lyze.sar.components.wait.WaitForAnimationFinishComponent;
import dev.lyze.sar.components.wait.WaitTimeComponent;
import dev.lyze.sar.utils.Constants;
import lombok.var;
@All({BattoComponent.class, PositionComponent.class, AnimatableSpriteComponent.class, BattoWaitTillPlayerIsInScreenStateComponent.class})
public class BattoWaitTillPlayerIsInScreenStateSystem extends IteratingSystem {
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
            return;

        world.edit(entityId)
                .remove(BattoWaitTillPlayerIsInScreenStateComponent.class)
                //.add(new DrawLineToPlayerComponent(timeToShoot))
                .add(new BattoFlyFromRightToLeftComponent());
    }
}
