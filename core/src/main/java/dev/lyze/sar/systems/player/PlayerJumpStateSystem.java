package dev.lyze.sar.systems.player;

import com.artemis.ComponentMapper;
import com.artemis.annotations.All;
import com.artemis.annotations.Wire;
import com.artemis.systems.IteratingSystem;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import dev.lyze.sar.components.AnimatableSpriteComponent;
import dev.lyze.sar.components.HitboxComponent;
import dev.lyze.sar.components.player.PlayerDuckStateComponent;
import dev.lyze.sar.components.wait.WaitForAnimationFinishComponent;
import dev.lyze.sar.components.player.PlayerComponent;
import dev.lyze.sar.components.player.PlayerIdleComponent;
import dev.lyze.sar.components.player.PlayerJumpStateComponent;
import dev.lyze.sar.utils.Constants;
import lombok.var;

@All({PlayerComponent.class, PlayerJumpStateComponent.class, AnimatableSpriteComponent.class})
public class PlayerJumpStateSystem extends IteratingSystem {
    @Wire(name = "constants") private Constants constants;
    private ComponentMapper<PlayerJumpStateComponent> jumpStateMapper;
    private ComponentMapper<AnimatableSpriteComponent> spriteMapper;
    private ComponentMapper<HitboxComponent> hitboxMapper;

    @Override
    protected void inserted(int entityId) {
        var jumpState = jumpStateMapper.get(entityId);
        jumpState.setTime(0);
    }

    @Override
    protected void process(int entityId) {
        var jumpState = jumpStateMapper.get(entityId);
        var sprite = spriteMapper.get(entityId).getSprite();
        var hitbox = hitboxMapper.get(entityId);

        jumpState.setTime(jumpState.getTime() + world.getDelta());
        sprite.setAnimation(constants.getPlayerAir());
        hitbox.setHeight(1.5f);

        if (jumpState.isFinished()) {
            if (Gdx.input.isKeyPressed(Input.Keys.S)) {
                world.edit(entityId)
                        .remove(PlayerJumpStateComponent.class)
                        .add(new PlayerDuckStateComponent());

                return;
            }

            sprite.setAnimation(constants.getPlayerLanding());

            world.edit(entityId)
                    .remove(PlayerJumpStateComponent.class)
                    .add(new WaitForAnimationFinishComponent(new PlayerIdleComponent()));
        }
    }
}
