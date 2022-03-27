package dev.lyze.sar.systems.player;

import com.artemis.ComponentMapper;
import com.artemis.annotations.All;
import com.artemis.annotations.Wire;
import com.artemis.systems.IteratingSystem;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import dev.lyze.sar.components.AnimatableSpriteComponent;
import dev.lyze.sar.components.WaitForAnimationFinishComponent;
import dev.lyze.sar.components.player.PlayerComponent;
import dev.lyze.sar.components.player.PlayerDuckStateComponent;
import dev.lyze.sar.components.player.PlayerIdleComponent;
import dev.lyze.sar.utils.Constants;
import lombok.var;

@All({PlayerComponent.class, PlayerIdleComponent.class, AnimatableSpriteComponent.class})
public class PlayerIdleStateSystem extends IteratingSystem {
    @Wire(name = "constants") private Constants constants;
    private ComponentMapper<AnimatableSpriteComponent> spriteMapper;

    @Override
    protected void process(int entityId) {
        var sprite = spriteMapper.get(entityId).getSprite();
        sprite.setAnimation(constants.getPlayerIdle());

        if (Gdx.input.isKeyPressed(Input.Keys.S)) {
            sprite.setAnimation(constants.getPlayerCrouchIn());

            world.edit(entityId)
                    .remove(PlayerIdleComponent.class)
                    .add(new WaitForAnimationFinishComponent(new PlayerDuckStateComponent()));
        }
    }
}
