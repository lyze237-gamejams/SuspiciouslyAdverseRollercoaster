package dev.lyze.sar.systems.sprites;

import com.artemis.ComponentMapper;
import com.artemis.annotations.All;
import com.artemis.systems.IteratingSystem;
import dev.lyze.sar.components.AnimatableSpriteComponent;
import lombok.var;

@All(AnimatableSpriteComponent.class)
public class AnimatableSpriteSystem extends IteratingSystem {
    private ComponentMapper<AnimatableSpriteComponent> animatableSpriteMapper;

    @Override
    protected void process(int entityId) {
        var sprite = animatableSpriteMapper.get(entityId);
        sprite.getSprite().tick(world.getDelta());
    }
}
