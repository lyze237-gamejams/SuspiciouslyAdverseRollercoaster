package dev.lyze.sar.systems.wait;

import com.artemis.ComponentMapper;
import com.artemis.annotations.All;
import com.artemis.systems.IteratingSystem;
import dev.lyze.sar.components.AnimatableSpriteComponent;
import dev.lyze.sar.components.wait.WaitForAnimationFinishComponent;
import lombok.var;

@All({WaitForAnimationFinishComponent.class, AnimatableSpriteComponent.class})
public class WaitForAnimationFinishSystem extends IteratingSystem {
    private ComponentMapper<WaitForAnimationFinishComponent> waitForAnimationFinishMapper;
    private ComponentMapper<AnimatableSpriteComponent> spriteMapper;

    @Override
    protected void process(int entityId) {
        var nextState = waitForAnimationFinishMapper.get(entityId);
        var sprite = spriteMapper.get(entityId).getSprite();

        if (sprite.isAnimationFinished())
            world.edit(entityId)
                .remove(WaitForAnimationFinishComponent.class)
                .add(nextState.getNextComponent());
    }
}
