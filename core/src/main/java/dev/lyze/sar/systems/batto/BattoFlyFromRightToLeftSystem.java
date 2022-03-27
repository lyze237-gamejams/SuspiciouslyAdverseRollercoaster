package dev.lyze.sar.systems.batto;

import com.artemis.ComponentMapper;
import com.artemis.annotations.All;
import com.artemis.annotations.Wire;
import com.artemis.systems.IteratingSystem;
import com.badlogic.gdx.utils.viewport.ExtendViewport;
import dev.lyze.sar.components.AnimatableSpriteComponent;
import dev.lyze.sar.components.BattoComponent;
import dev.lyze.sar.components.batto.BattoFlyFromRightToLeftComponent;
import dev.lyze.sar.components.movement.PositionComponent;
import dev.lyze.sar.utils.Constants;
import lombok.var;

@All({BattoComponent.class, PositionComponent.class, AnimatableSpriteComponent.class, BattoFlyFromRightToLeftComponent.class})
public class BattoFlyFromRightToLeftSystem extends IteratingSystem {
    @Wire private ExtendViewport viewport;
    @Wire(name = "constants") private Constants constants;

    private ComponentMapper<PositionComponent> positionMapper;
    private ComponentMapper<AnimatableSpriteComponent> spriteMapper;
    private ComponentMapper<BattoFlyFromRightToLeftComponent> battoMapper;

    @Override
    protected void process(int entityId) {
        var position = positionMapper.get(entityId).getPosition();
        var sprite = spriteMapper.get(entityId).getSprite();
        var batto = battoMapper.get(entityId);

        System.out.println(position);

        position.x -= batto.getSpeed() * world.getDelta();

        sprite.setAnimation(constants.getBattoFlight());

        if (position.x < 0)
            world.delete(entityId);
    }
}
