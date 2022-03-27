package dev.lyze.sar.systems.sprites;

import com.artemis.ComponentMapper;
import com.artemis.annotations.All;
import com.artemis.annotations.Wire;
import com.artemis.systems.IteratingSystem;
import com.badlogic.gdx.graphics.Color;
import dev.lyze.sar.components.AnimatableSpriteComponent;
import dev.lyze.sar.components.player.FlashSpriteComponent;
import dev.lyze.sar.components.player.PlayerOrCartComponent;
import dev.lyze.sar.eventsystem.EventListener;
import dev.lyze.sar.eventsystem.EventManager;
import dev.lyze.sar.eventsystem.events.HitEvent;
import lombok.var;

@All({AnimatableSpriteComponent.class, FlashSpriteComponent.class})
public class FlashSpriteSystem extends IteratingSystem {
    @Wire private EventManager eventManager;
    private ComponentMapper<AnimatableSpriteComponent> spriteMapper;
    private ComponentMapper<FlashSpriteComponent> flashMapper;

    @Override
    protected void process(int entityId) {
        var sprite = spriteMapper.get(entityId).getSprite();
        var flash = flashMapper.get(entityId);

        flash.setDuration(flash.getDuration() - world.getDelta());

        sprite.setColor((((int) (flash.getDuration() * 10)) + 1) % 2 == 0 ? flash.getColor() : Color.WHITE);

        if (flash.getDuration() < 0) {
            sprite.setColor(Color.WHITE);
            world.edit(entityId)
                    .remove(FlashSpriteComponent.class);
        }
    }
}
