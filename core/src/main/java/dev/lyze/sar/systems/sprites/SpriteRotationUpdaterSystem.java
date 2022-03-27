package dev.lyze.sar.systems.sprites;

import com.artemis.ComponentMapper;
import com.artemis.annotations.All;
import com.artemis.systems.IteratingSystem;
import com.badlogic.gdx.math.MathUtils;
import dev.lyze.sar.components.AnimatableSpriteComponent;
import dev.lyze.sar.components.movement.RotationComponent;
import lombok.var;

@All({AnimatableSpriteComponent.class, RotationComponent.class})
public class SpriteRotationUpdaterSystem extends IteratingSystem {
    private ComponentMapper<AnimatableSpriteComponent> spriteMapper;
    private ComponentMapper<RotationComponent> rotationMapper;

    @Override
    protected void process(int entityId) {
        var sprite = spriteMapper.get(entityId).getSprite();
        var rotation = rotationMapper.get(entityId).getRotation();

        sprite.setRotation(rotation * MathUtils.radiansToDegrees);
    }
}
