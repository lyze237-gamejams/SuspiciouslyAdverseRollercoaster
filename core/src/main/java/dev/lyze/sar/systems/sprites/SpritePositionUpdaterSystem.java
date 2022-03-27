package dev.lyze.sar.systems.sprites;

import com.artemis.ComponentMapper;
import com.artemis.annotations.All;
import com.artemis.systems.IteratingSystem;
import dev.lyze.sar.components.AnimatableSpriteComponent;
import dev.lyze.sar.components.movement.PositionComponent;
import lombok.var;

@All({AnimatableSpriteComponent.class, PositionComponent.class})
public class SpritePositionUpdaterSystem extends IteratingSystem {
    private ComponentMapper<AnimatableSpriteComponent> spriteMapper;
    private ComponentMapper<PositionComponent> positionMapper;

    @Override
    protected void process(int entityId) {
        var sprite = spriteMapper.get(entityId).getSprite();
        var position = positionMapper.get(entityId).getPosition();

        sprite.setOriginBasedPosition(position.x, position.y);
    }
}
