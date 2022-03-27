package dev.lyze.sar.systems.player;

import com.artemis.ComponentMapper;
import com.artemis.annotations.All;
import com.artemis.annotations.Wire;
import com.artemis.systems.IteratingSystem;
import dev.lyze.sar.components.AnimatableSpriteComponent;
import dev.lyze.sar.components.player.FlashSpriteComponent;
import dev.lyze.sar.components.player.PlayerOrCartComponent;
import dev.lyze.sar.eventsystem.EventListener;
import dev.lyze.sar.eventsystem.EventManager;
import dev.lyze.sar.eventsystem.events.HitEvent;

@All({AnimatableSpriteComponent.class, PlayerOrCartComponent.class})
public class FlashPlayerSpriteOnHitSystem extends IteratingSystem {
    @Wire private EventManager eventManager;
    private ComponentMapper<AnimatableSpriteComponent> spriteMapper;

    private boolean startFlash;

    @Override
    protected void initialize() {
        eventManager.addListener(new EventListener<HitEvent>(HitEvent.class) {
            @Override
            protected void fire(HitEvent event) {
                startFlash = true;
            }
        });
    }

    @Override
    protected void process(int entityId) {
        if (startFlash)
            world.edit(entityId)
                .add(new FlashSpriteComponent(0.8f));
    }

    @Override
    protected void end() {
        startFlash = false;
    }
}
