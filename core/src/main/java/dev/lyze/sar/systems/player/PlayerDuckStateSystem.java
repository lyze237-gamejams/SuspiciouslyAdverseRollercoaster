package dev.lyze.sar.systems.player;

import com.artemis.ComponentMapper;
import com.artemis.annotations.All;
import com.artemis.annotations.Wire;
import com.artemis.systems.IteratingSystem;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import dev.lyze.sar.components.AnimatableSpriteComponent;
import dev.lyze.sar.components.HitboxComponent;
import dev.lyze.sar.components.WaitForAnimationFinishComponent;
import dev.lyze.sar.components.player.PlayerComponent;
import dev.lyze.sar.components.player.PlayerDuckStateComponent;
import dev.lyze.sar.components.player.PlayerIdleComponent;
import dev.lyze.sar.utils.Constants;
import lombok.var;

@All({PlayerComponent.class, PlayerDuckStateComponent.class, AnimatableSpriteComponent.class, HitboxComponent.class})
public class PlayerDuckStateSystem extends IteratingSystem {
    @Wire(name = "constants") private Constants constants;
    private ComponentMapper<PlayerDuckStateComponent> duckStateMapper;
    private ComponentMapper<AnimatableSpriteComponent> spriteMapper;
    private ComponentMapper<HitboxComponent> hitboxMapper;

    @Override
    protected void process(int entityId) {
        var duckState = duckStateMapper.get(entityId);
        var sprite = spriteMapper.get(entityId).getSprite();
        var hitbox = hitboxMapper.get(entityId);

        hitbox.setHeight(duckState.getHitboxHeight());
        sprite.setAnimation(constants.getPlayerCrouchIn());

        if (!Gdx.input.isKeyPressed(Input.Keys.S)) {
            sprite.setAnimation(constants.getPlayerCrouchOut());

            world.edit(entityId)
                    .remove(PlayerDuckStateComponent.class)
                    .add(new WaitForAnimationFinishComponent(new PlayerIdleComponent()));
        }
    }
}
